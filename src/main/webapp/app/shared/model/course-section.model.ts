import { ICourse } from 'app/shared/model/course.model';

export interface ICourseSection {
  id?: number;
  sectionTitle?: string;
  sectionDescription?: string | null;
  sectionOrder?: number;
  isDraft?: boolean;
  isApproved?: boolean;
  course?: ICourse | null;
}

export const defaultValue: Readonly<ICourseSection> = {
  isDraft: false,
  isApproved: false,
};
