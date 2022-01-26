export interface ICourseCategory {
  id?: number;
  title?: string;
  logo?: string;
  isParent?: boolean;
  parentId?: number;
  description?: string | null;
}

export const defaultValue: Readonly<ICourseCategory> = {
  isParent: false,
};
