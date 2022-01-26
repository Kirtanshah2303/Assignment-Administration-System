import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Course from './course';
import CourseCategory from './course-category';
import CourseEnrollment from './course-enrollment';
import CourseLevel from './course-level';
import CourseSessionProgress from './course-session-progress';
import CourseAssignmentProgress from './course-assignment-progress';
import CourseReviewStatus from './course-review-status';
import CourseSection from './course-section';
import CourseSession from './course-session';
import CourseAssignment from './course-assignment';
import CourseAssignmentInput from './course-assignment-input';
import CourseAssignmentOutput from './course-assignment-output';
import CourseType from './course-type';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}course`} component={Course} />
      <ErrorBoundaryRoute path={`${match.url}course-category`} component={CourseCategory} />
      <ErrorBoundaryRoute path={`${match.url}course-enrollment`} component={CourseEnrollment} />
      <ErrorBoundaryRoute path={`${match.url}course-level`} component={CourseLevel} />
      <ErrorBoundaryRoute path={`${match.url}course-session-progress`} component={CourseSessionProgress} />
      <ErrorBoundaryRoute path={`${match.url}course-assignment-progress`} component={CourseAssignmentProgress} />
      <ErrorBoundaryRoute path={`${match.url}course-review-status`} component={CourseReviewStatus} />
      <ErrorBoundaryRoute path={`${match.url}course-section`} component={CourseSection} />
      <ErrorBoundaryRoute path={`${match.url}course-session`} component={CourseSession} />
      <ErrorBoundaryRoute path={`${match.url}course-assignment`} component={CourseAssignment} />
      <ErrorBoundaryRoute path={`${match.url}course-assignment-input`} component={CourseAssignmentInput} />
      <ErrorBoundaryRoute path={`${match.url}course-assignment-output`} component={CourseAssignmentOutput} />
      <ErrorBoundaryRoute path={`${match.url}course-type`} component={CourseType} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
