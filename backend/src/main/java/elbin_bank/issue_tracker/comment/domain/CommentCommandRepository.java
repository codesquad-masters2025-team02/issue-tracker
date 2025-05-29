package elbin_bank.issue_tracker.comment.domain;

import elbin_bank.issue_tracker.comment.presentation.command.dto.request.CommentCreateRequestDto;

public interface CommentCommandRepository {

    void save(CommentCreateRequestDto commentCreateRequestDto, long issueId);

}
