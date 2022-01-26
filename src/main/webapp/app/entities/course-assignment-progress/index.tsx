import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CourseAssignmentProgress from './course-assignment-progress';
import CourseAssignmentProgressDetail from './course-assignment-progress-detail';
import CourseAssignmentProgressUpdate from './course-assignment-progress-update';
import CourseAssignmentProgressDeleteDialog from './course-assignment-progress-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CourseAssignmentProgressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CourseAssignmentProgressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CourseAssignmentProgressDetail} />
      <ErrorBoundaryRoute path={match.url} component={CourseAssignmentProgress} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CourseAssignmentProgressDeleteDialog} />
  </>
);

export default Routes;
