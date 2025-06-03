import DescriptionBox from '@/features/issue/components/detail/DescriptionBox';
import { type CommentAuthor } from '@/features/issue/types/issue';

interface IssueDescriptionProps {
  content: string | null;
  author: CommentAuthor;
  createdAt: string;
}

export default function IssueDescription(props: IssueDescriptionProps) {
  const handleSubmit = (description: string) => {
    // TODO 이슈 description 편집 로직
  };

  return <DescriptionBox {...props} onSubmit={handleSubmit} />;
}
