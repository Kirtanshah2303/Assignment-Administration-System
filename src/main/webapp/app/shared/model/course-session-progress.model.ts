import { IUser } from 'app/shared/model/user.model';
import { ICourseSession } from 'app/shared/model/course-session.model';

export interface ICourseSessionProgress {
  id?: number;
  watchSeconds?: number;
  user?: IUser | null;
  courseSession?: ICourseSession | null;
}

export const defaultValue: Readonly<ICourseSessionProgress> = {};
