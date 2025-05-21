package elbin_bank.issue_tracker.user.domain;

import elbin_bank.issue_tracker.comment.application.query.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface UserRepository {

    Map<Long, String> findNickNamesByIds(List<Long> ids);





}
