import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CourseAssignment from './course-assignment';
import CourseAssignmentDetail from './course-assignment-detail';
import CourseAssignmentUpdate from './course-assignment-update';
import CourseAssignmentDeleteDialog from './course-assignment-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CourseAssignmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CourseAssignmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CourseAssignmentDetail} />
      <ErrorBoundaryRoute path={match.url} component={CourseAssignment} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CourseAssignmentDeleteDialog} />
  </>
);

export default Routes;
