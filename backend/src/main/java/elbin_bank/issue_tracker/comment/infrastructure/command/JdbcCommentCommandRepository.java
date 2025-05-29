package elbin_bank.issue_tracker.comment.infrastructure.command;

import elbin_bank.issue_tracker.comment.domain.CommentCommandRepository;
import elbin_bank.issue_tracker.comment.presentation.command.dto.request.CommentCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcCommentCommandRepository implements CommentCommandRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public void save(CommentCreateRequestDto commentCreateRequestDto, long id) {
        Long mockUserId = 1L; // todo: 로그인 구현 후 변경 예정

        String sql = """
                    INSERT INTO comment
                      (issue_id, user_id, contents)
                    VALUES
                      (:issueId, :userId, :contents)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("issueId", id)
                .addValue("userId", mockUserId)
                .addValue("contents", commentCreateRequestDto.content());

        jdbc.update(sql, params);
    }

}
