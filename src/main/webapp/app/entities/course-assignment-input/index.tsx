import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CourseAssignmentInput from './course-assignment-input';
import CourseAssignmentInputDetail from './course-assignment-input-detail';
import CourseAssignmentInputUpdate from './course-assignment-input-update';
import CourseAssignmentInputDeleteDialog from './course-assignment-input-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CourseAssignmentInputUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CourseAssignmentInputUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CourseAssignmentInputDetail} />
      <ErrorBoundaryRoute path={match.url} component={CourseAssignmentInput} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CourseAssignmentInputDeleteDialog} />
  </>
);

export default Routes;
