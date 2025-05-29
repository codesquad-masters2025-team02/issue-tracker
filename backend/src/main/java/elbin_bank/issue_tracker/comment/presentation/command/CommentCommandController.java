package elbin_bank.issue_tracker.comment.presentation.command;

import elbin_bank.issue_tracker.comment.application.query.CommentsQueryRepository;
import elbin_bank.issue_tracker.comment.domain.CommentCommandRepository;
import elbin_bank.issue_tracker.comment.presentation.command.dto.request.CommentCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/issues")
@RequiredArgsConstructor
public class CommentCommandController {

    private final CommentCommandRepository commentCommandRepository;

    @PostMapping(s)
    public ResponseEntity<Void> createComment(@RequestBody CommentCreateRequestDto commentCreateRequestDto, @PathVariable Long id, String s){
        commentCommandRepository.save(commentCreateRequestDto, id);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
