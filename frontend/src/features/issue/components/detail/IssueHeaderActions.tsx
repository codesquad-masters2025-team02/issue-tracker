import Button from '@/shared/components/Button';
import EditIcon from '@/assets/icons/edit.svg?react';
import ClosedIcon from '@/assets/icons/archive.svg?react';

interface Props {
  isClosed: boolean;
  onToggleIssueState: () => void;
}

export default function IssueHeaderActions({
  isClosed,
  onToggleIssueState,
}: Props) {
  return (
    <>
      <Button
        variant="outline"
        size="small"
        icon={<EditIcon />}
        onClick={() => {
          // TODO 편집로직
        }}
      >
        제목 편집
      </Button>
      <Button
        variant="outline"
        size="small"
        icon={<ClosedIcon />}
        onClick={onToggleIssueState}
      >
        {isClosed ? '이슈 열기' : '이슈 닫기'}
      </Button>
    </>
  );
}
