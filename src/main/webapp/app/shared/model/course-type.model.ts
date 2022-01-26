export interface ICourseType {
  id?: number;
  title?: string;
  description?: string | null;
}

export const defaultValue: Readonly<ICourseType> = {};
