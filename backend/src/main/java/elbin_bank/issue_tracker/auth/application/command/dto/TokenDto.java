package elbin_bank.issue_tracker.auth.application.command.dto;

public record TokenDto(
        String accessToken,
        String tokenType
) {
}
