import styled from '@emotion/styled';
import IssueMeta from './IssueMeta';
import IssueHeaderActions from './IssueHeaderActions';

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

//TODO 편집 기능, 이슈 열림 토글 기능 추가시 prop도 추가
export default function IssueHeader({
  isClosed,
  issueNumber,
  title,
  author,
  createdAt,
  commentCount,
  onToggleIssueState,
}: IssueHeaderProps) {
  return (
    <HeaderWrapper>
      <LeftSection>
        <Title>
          {title} <IssueNumber>#{issueNumber}</IssueNumber>
        </Title>

        <IssueMeta
          isClosed={isClosed}
          createdAt={createdAt}
          authorName={author.nickname}
          commentCount={commentCount}
        />
      </LeftSection>
      <RightSection>
        <IssueHeaderActions
          isClosed={isClosed}
          onToggleIssueState={onToggleIssueState}
        />
      </RightSection>
    </HeaderWrapper>
  );
}

const HeaderWrapper = styled.div`
  display: flex;
  justify-content: space-between;
`;

const LeftSection = styled.div`
  display: flex;
  flex-direction: column;
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
