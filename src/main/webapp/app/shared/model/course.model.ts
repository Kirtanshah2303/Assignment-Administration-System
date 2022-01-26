import dayjs from 'dayjs';
import { ICourseLevel } from 'app/shared/model/course-level.model';
import { ICourseCategory } from 'app/shared/model/course-category.model';
import { ICourseType } from 'app/shared/model/course-type.model';
import { IUser } from 'app/shared/model/user.model';

export interface ICourse {
  id?: number;
  courseTitle?: string;
  courseDescription?: string;
  courseObjectives?: string | null;
  courseSubTitle?: string;
  coursePreviewURL?: string | null;
  courseLength?: number | null;
  courseLogo?: string;
  courseCreatedOn?: string;
  courseUpdatedOn?: string;
  courseRootDir?: string | null;
  amount?: number | null;
  isDraft?: boolean;
  isApproved?: boolean;
  isPublished?: boolean;
  courseApprovalDate?: string | null;
  courseLevel?: ICourseLevel | null;
  courseCategory?: ICourseCategory | null;
  courseType?: ICourseType | null;
  user?: IUser | null;
  reviewer?: IUser | null;
}

export const defaultValue: Readonly<ICourse> = {
  isDraft: false,
  isApproved: false,
  isPublished: false,
};
