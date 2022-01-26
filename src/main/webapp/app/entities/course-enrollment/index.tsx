import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CourseEnrollment from './course-enrollment';
import CourseEnrollmentDetail from './course-enrollment-detail';
import CourseEnrollmentUpdate from './course-enrollment-update';
import CourseEnrollmentDeleteDialog from './course-enrollment-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CourseEnrollmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CourseEnrollmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CourseEnrollmentDetail} />
      <ErrorBoundaryRoute path={match.url} component={CourseEnrollment} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CourseEnrollmentDeleteDialog} />
  </>
);

export default Routes;
