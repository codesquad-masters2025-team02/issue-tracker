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
      <TopRow>
        {isEditing ? (
          <TextInput
            label="제목"
            value={editedTitle}
            onChange={e => setEditedTitle(e.target.value)}
            placeholder="이슈의 제목을 입력해주세요"
          />
        ) : (
          <Title>
            <TitleText>{title}</TitleText>
            <IssueNumber>#{issueNumber}</IssueNumber>
          </Title>
        )}

        <IssueHeaderActions
          isEditing={isEditing}
          onEditStart={() => setIsEditing(true)}
          onEditCancel={handleCancelEdit}
          onEditSubmit={handleSubmitEdit}
          isClosed={isClosed}
          onToggleIssueState={onToggleIssueState}
          isSubmitDisabled={isSubmitDisabled}
        />
      </TopRow>

      <IssueMeta
        isClosed={isClosed}
        createdAt={createdAt}
        authorName={author.nickname}
        commentCount={commentCount}
      />
    </HeaderWrapper>
  );
}

const HeaderWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

const TopRow = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 16px;
  min-height: 48px;
`;

const Title = styled.h1`
  display: inline;
  flex-wrap: wrap;
  gap: 8px;

  color: ${({ theme }) => theme.neutral.text.strong};
  ${({ theme }) => theme.typography.displayBold32};
`;

const TitleText = styled.span`
  flex: 1;
  word-break: break-word;
  overflow-wrap: anywhere;
  margin-right: 8px;
`;

const IssueNumber = styled.span`
  align-self: flex-start;
  color: ${({ theme }) => theme.neutral.text.weak};
  white-space: nowrap;
`;
