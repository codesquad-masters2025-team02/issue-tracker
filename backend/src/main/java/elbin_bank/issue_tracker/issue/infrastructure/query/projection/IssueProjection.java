package elbin_bank.issue_tracker.issue.infrastructure.query.projection;

import elbin_bank.issue_tracker.label.application.query.dto.LabelDto;

import java.time.LocalDateTime;
import java.util.List;

public record IssueProjection(
        Long id,
        String author,
        String title,
        List<LabelDto> labels,
        boolean isClosed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<String> assigneesProfileImages,
        String milestone
) {
}
