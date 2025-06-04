package elbin_bank.issue_tracker.milestone.infrastructure.query.projection;

public record MilestoneProjection(
        long id,
        String title,
        String description,
        String expiredAt,
        long totalIssueCount,
        long closedIssueCount
) {
}
