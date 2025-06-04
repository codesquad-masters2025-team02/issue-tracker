package elbin_bank.issue_tracker.milestone.application.query.dto;

public record MilestoneDto(
        long id,
        String title,
        String expiredAt,
        long totalIssueCount,
        long closedIssueCount
) {
}
