package elbin_bank.issue_tracker.milestone.infrastructure;

import elbin_bank.issue_tracker.milestone.domain.Milestone;
import elbin_bank.issue_tracker.milestone.domain.MilestoneRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

@Repository
public class JdbcMilestoneRepository implements MilestoneRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcMilestoneRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Milestone> findMilestonesByMilestoneIds(List<Long> milestoneIds) {
        if(milestoneIds == null || milestoneIds.isEmpty()) {
            return List.of();
        }

        String sql = """
        SELECT *
        FROM milestone
        WHERE id IN (:milestoneIds)
        """;

        var params = new MapSqlParameterSource("milestoneIds", milestoneIds);

        return jdbc.query(sql, params, milestoneRowMapper());

    }

    private RowMapper<Milestone> milestoneRowMapper() {
        return (rs, rowNum) -> {
            Milestone milestone = new Milestone();

            // Milestone 고유 필드
            milestone.setId(rs.getLong("id"));
            milestone.setTitle(rs.getString("title"));
            milestone.setDescription(rs.getString("description"));
            milestone.setClosed(rs.getBoolean("is_closed"));

            Date expiredDate = rs.getDate("expired_at");
            if (expiredDate != null) {
                milestone.setExpiredAt(expiredDate.toLocalDate());
            }

            //Not null
            Timestamp createdAt = rs.getTimestamp("created_at");
            milestone.setCreatedAt(createdAt.toLocalDateTime());


            // BaseEntity 공통 필드
            Timestamp updatedAt = rs.getTimestamp("updated_at");
            if (updatedAt != null) {
                milestone.setUpdatedAt(updatedAt.toLocalDateTime());
            }

            Timestamp deletedAt = rs.getTimestamp("deleted_at");
            if (deletedAt != null) {
                milestone.setDeletedAt(deletedAt.toLocalDateTime());
            }

            return milestone;
        };
    }



}

