package elbin_bank.issue_tracker.issue.infrastructure.query;

import elbin_bank.issue_tracker.issue.application.query.repository.IssueQueryRepository;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueCountProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.strategy.FilterStrategyContext;
import elbin_bank.issue_tracker.label.application.query.dto.LabelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcIssueQueryRepository implements IssueQueryRepository {

    private final NamedParameterJdbcTemplate jdbc;
    private final FilterStrategyContext filterCtx;

    @Override
    public List<IssueProjection> findIssues(String rawQuery) {
        String baseSql = """
                    SELECT i.id,
                           u.nickname AS author,
                           i.title,
                           m.title AS milestone,
                           i.is_closed,
                           i.created_at,
                           i.updated_at
                      FROM issue i
                 LEFT JOIN `user`    u ON i.author_id    = u.id
                 LEFT JOIN milestone m ON i.milestone_id = m.id
                """;

        var sqlAndParams = filterCtx.buildSql(rawQuery);
        String finalSql = baseSql + sqlAndParams.sql();

        return jdbc.query(finalSql, sqlAndParams.params(), (rs, rowNum) -> {
            long id = rs.getLong("id");
            var labels = fetchLabels(id);
            var assignees = fetchAssignees(id);
            return new IssueProjection(
                    id,
                    rs.getString("author"),
                    rs.getString("title"),
                    labels,
                    rs.getBoolean("is_closed"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at") != null
                            ? rs.getTimestamp("updated_at").toLocalDateTime()
                            : null,
                    assignees,
                    rs.getString("milestone")
            );
        });
    }

    private List<LabelDto> fetchLabels(Long issueId) {
        String sql = """
                    SELECT l.id, l.name, l.description, l.color
                      FROM label l
                     JOIN issue_label il ON il.label_id = l.id
                     WHERE il.issue_id = :iid
                """;

        return jdbc.query(sql, Map.of("iid", issueId),
                (rs, rn) -> new LabelDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("color"),
                        rs.getString("description")
                )
        );
    }

    private List<String> fetchAssignees(Long issueId) {
        String sql = """
                    SELECT u.profile_image_url
                      FROM `user` u
                     JOIN assignee a ON a.user_id = u.id
                     WHERE a.issue_id = :iid
                """;

        return jdbc.queryForList(sql, Map.of("iid", issueId), String.class);
    }

    @Override
    public IssueCountProjection countIssueOpenAndClosed() {
        return jdbc.queryForObject(
                "SELECT open_count, closed_count FROM issue_status_count WHERE id=1",
                Map.of(), (rs, rn) -> new IssueCountProjection(rs.getLong(1), rs.getLong(2))
        );
    }

}
