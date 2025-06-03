import { useState } from 'react';
import styled from '@emotion/styled';
import usePatchIssueTitle from '@/features/issue/hooks/usePatchIssueTitle';
import IssueMeta from './IssueMeta';
import IssueHeaderActions from './IssueHeaderActions';
import TextInput from '@/shared/components/TextInput';

interface IssueHeaderProps {
  isClosed: boolean;
  issueNumber: number;
  title: string;
  author: {
    id: number;
    nickname: string;
    profileImage: string;
  };
  createdAt: string;
  commentCount: number;
  onToggleIssueState: () => void;
}

export default function IssueHeader({
  isClosed,
  issueNumber,
  title,
  author,
  createdAt,
  commentCount,
  onToggleIssueState,
}: IssueHeaderProps) {
  const { mutate: patchTitle } = usePatchIssueTitle(issueNumber);
  const [isEditing, setIsEditing] = useState(false);
  const [editedTitle, setEditedTitle] = useState(title);

  const isSubmitDisabled = editedTitle.trim() === title.trim();

  const handleSubmitEdit = () => {
    patchTitle(editedTitle);
    setIsEditing(false);
  };

  const handleCancelEdit = () => {
    setEditedTitle(title);
    setIsEditing(false);
  };

  return (
    <HeaderWrapper>
      <LeftSection>
        {isEditing ? (
          <TextInput
            label="제목"
            value={editedTitle}
            onChange={e => setEditedTitle(e.target.value)}
            placeholder="이슈의 제목을 입력해주세요"
          />
        ) : (
          <Title>
            {title} <IssueNumber>#{issueNumber}</IssueNumber>
          </Title>
        )}

        <IssueMeta
          isClosed={isClosed}
          createdAt={createdAt}
          authorName={author.nickname}
          commentCount={commentCount}
        />
      </LeftSection>
      <RightSection>
        <IssueHeaderActions
          isEditing={isEditing}
          onEditStart={() => setIsEditing(true)}
          onEditCancel={handleCancelEdit}
          onEditSubmit={handleSubmitEdit}
          isClosed={isClosed}
          onToggleIssueState={onToggleIssueState}
          isSubmitDisabled={isSubmitDisabled}
        />
      </RightSection>
    </HeaderWrapper>
  );
}

const HeaderWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  gap: 16px;
`;

const LeftSection = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1;
  gap: 16px;
`;

const RightSection = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
  height: 48px;
`;

const Title = styled.h1`
  display: flex;
  align-items: flex-start;
  gap: 8px;

  color: ${({ theme }) => theme.neutral.text.strong};
  ${({ theme }) => theme.typography.displayBold32};
`;

const IssueNumber = styled.span`
  color: ${({ theme }) => theme.neutral.text.weak};
`;
