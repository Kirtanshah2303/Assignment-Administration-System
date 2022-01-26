import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import course from 'app/entities/course/course.reducer';
// prettier-ignore
import courseCategory from 'app/entities/course-category/course-category.reducer';
// prettier-ignore
import courseEnrollment from 'app/entities/course-enrollment/course-enrollment.reducer';
// prettier-ignore
import courseLevel from 'app/entities/course-level/course-level.reducer';
// prettier-ignore
import courseSessionProgress from 'app/entities/course-session-progress/course-session-progress.reducer';
// prettier-ignore
import courseAssignmentProgress from 'app/entities/course-assignment-progress/course-assignment-progress.reducer';
// prettier-ignore
import courseReviewStatus from 'app/entities/course-review-status/course-review-status.reducer';
// prettier-ignore
import courseSection from 'app/entities/course-section/course-section.reducer';
// prettier-ignore
import courseSession from 'app/entities/course-session/course-session.reducer';
// prettier-ignore
import courseAssignment from 'app/entities/course-assignment/course-assignment.reducer';
// prettier-ignore
import courseAssignmentInput from 'app/entities/course-assignment-input/course-assignment-input.reducer';
// prettier-ignore
import courseAssignmentOutput from 'app/entities/course-assignment-output/course-assignment-output.reducer';
// prettier-ignore
import courseType from 'app/entities/course-type/course-type.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  course,
  courseCategory,
  courseEnrollment,
  courseLevel,
  courseSessionProgress,
  courseAssignmentProgress,
  courseReviewStatus,
  courseSection,
  courseSession,
  courseAssignment,
  courseAssignmentInput,
  courseAssignmentOutput,
  courseType,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
