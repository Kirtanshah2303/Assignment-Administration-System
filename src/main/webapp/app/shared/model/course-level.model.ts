export interface ICourseLevel {
  id?: number;
  title?: string | null;
  description?: string | null;
}

export const defaultValue: Readonly<ICourseLevel> = {};
