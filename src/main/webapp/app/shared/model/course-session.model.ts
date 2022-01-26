import dayjs from 'dayjs';
import { ICourseSection } from 'app/shared/model/course-section.model';
import { ICourseReviewStatus } from 'app/shared/model/course-review-status.model';

export interface ICourseSession {
  id?: number;
  sessionTitle?: string;
  sessionDescription?: string | null;
  sessionVideo?: string;
  sessionDuration?: string;
  sessionOrder?: number;
  sessionResource?: string | null;
  sessionQuiz?: string | null;
  isPreview?: boolean;
  isDraft?: boolean;
  isApproved?: boolean;
  isPublished?: boolean;
  courseSection?: ICourseSection | null;
  courseReviewStatuses?: ICourseReviewStatus[] | null;
}

export const defaultValue: Readonly<ICourseSession> = {
  isPreview: false,
  isDraft: false,
  isApproved: false,
  isPublished: false,
};
