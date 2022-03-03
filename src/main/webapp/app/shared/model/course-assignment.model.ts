import { ICourseSession } from 'app/shared/model/course-session.model';

export interface ICourseAssignment {
  id?: number;
  assignmentTitle?: string;
  assignmentDescription?: string | null;
  assignmentOrder?: number;
  assignmentResource?: string | null;
  isPreview?: boolean;
  isDraft?: boolean;
  isApproved?: boolean;
  isPublished?: boolean;
  courseSession?: ICourseSession | null;
}

export const defaultValue: Readonly<ICourseAssignment> = {
  isPreview: false,
  isDraft: false,
  isApproved: false,
  isPublished: false,
};
