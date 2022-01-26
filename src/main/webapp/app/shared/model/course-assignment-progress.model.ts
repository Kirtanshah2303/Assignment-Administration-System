import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ICourseAssignment } from 'app/shared/model/course-assignment.model';

export interface ICourseAssignmentProgress {
  id?: number;
  completed?: boolean;
  completedDate?: string | null;
  user?: IUser | null;
  courseAssignment?: ICourseAssignment | null;
}

export const defaultValue: Readonly<ICourseAssignmentProgress> = {
  completed: false,
};
