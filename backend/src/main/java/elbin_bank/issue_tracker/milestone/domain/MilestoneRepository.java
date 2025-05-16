package elbin_bank.issue_tracker.milestone.domain;

import java.util.List;

public interface MilestoneRepository {

    List<Milestone> findMilestonesByMilestoneIds(List<Long> milestoneIds);

}
