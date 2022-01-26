import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CourseAssignmentOutput from './course-assignment-output';
import CourseAssignmentOutputDetail from './course-assignment-output-detail';
import CourseAssignmentOutputUpdate from './course-assignment-output-update';
import CourseAssignmentOutputDeleteDialog from './course-assignment-output-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CourseAssignmentOutputUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CourseAssignmentOutputUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CourseAssignmentOutputDetail} />
      <ErrorBoundaryRoute path={match.url} component={CourseAssignmentOutput} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CourseAssignmentOutputDeleteDialog} />
  </>
);

export default Routes;
