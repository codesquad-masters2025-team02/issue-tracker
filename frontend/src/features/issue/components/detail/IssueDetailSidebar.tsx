import useIssueLabels from '@/features/issue/hooks/useIssueLabels';
import useIssueAssignees from '@/features/issue/hooks/useIssueAssignees';
import useIssueMilestone from '@/features/issue/hooks/useIssueMilestone';

import IssueSidebar from '@/shared/components/sidebar';

interface Props {
  issueId: number;
}

export default function IssueDetailSidebar({ issueId }: Props) {
  const { issueLabels } = useIssueLabels(issueId);
  const { issueAssignees } = useIssueAssignees(issueId);
  const { issueMilestone } = useIssueMilestone(issueId);

  const selectedAssigneeIds = issueAssignees.map(assignee => assignee.id);
  const selectedLabelIds = issueLabels.map(label => label.id);
  const selectedMilestoneId = issueMilestone?.id ?? null;

  // TODO 로딩, 에러 처리 분기

  return (
    <IssueSidebar
      selectedAssigneeIds={selectedAssigneeIds}
      onToggleAssignee={() => {}}
      selectedLabelIds={selectedLabelIds}
      onToggleLabel={() => {}}
      selectedMilestoneId={selectedMilestoneId}
      onSelectMilestone={() => {}}
    />
  );
}
