package elbin_bank.issue_tracker.user.domain;

import java.util.List;
import java.util.Optional;

public interface UserCommandRepository {

    void saveAssigneesToIssue(long issueId, List<Long> assignees);

    User save(User user);

    Optional<User> findByUuid(String uuid);

    Optional<User> findByLogin(String login);

    Optional<User> findByOAuthId(long oauthId);

}
