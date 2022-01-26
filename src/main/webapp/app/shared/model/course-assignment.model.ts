import dayjs from 'dayjs';
import { ICourseSection } from 'app/shared/model/course-section.model';
import { ICourseAssignmentInput } from 'app/shared/model/course-assignment-input.model';
import { ICourseAssignmentOutput } from 'app/shared/model/course-assignment-output.model';

export interface ICourseAssignment {
  id?: number;
  assignmentTitle?: string;
  assignmentDescription?: string | null;
  sessionVideo?: string;
  sessionDuration?: string;
  assignmentOrder?: number;
  assignmentResource?: string | null;
  isPreview?: boolean;
  isDraft?: boolean;
  isApproved?: boolean;
  isPublished?: boolean;
  courseSection?: ICourseSection | null;
  courseAssignmentInputs?: ICourseAssignmentInput[] | null;
  courseAssignmentOutputs?: ICourseAssignmentOutput[] | null;
}

export const defaultValue: Readonly<ICourseAssignment> = {
  isPreview: false,
  isDraft: false,
  isApproved: false,
  isPublished: false,
};
