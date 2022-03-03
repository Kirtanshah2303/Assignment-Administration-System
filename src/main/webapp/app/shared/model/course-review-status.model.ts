import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ICourseSession } from 'app/shared/model/course-session.model';

export interface ICourseReviewStatus {
  id?: number;
  status?: boolean;
  statusUpdatedOn?: string | null;
  feedback?: string | null;
  user?: IUser | null;
  courseSession?: ICourseSession | null;
}

export const defaultValue: Readonly<ICourseReviewStatus> = {
  status: false,
};
