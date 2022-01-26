import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CourseSessionProgress from './course-session-progress';
import CourseSessionProgressDetail from './course-session-progress-detail';
import CourseSessionProgressUpdate from './course-session-progress-update';
import CourseSessionProgressDeleteDialog from './course-session-progress-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CourseSessionProgressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CourseSessionProgressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CourseSessionProgressDetail} />
      <ErrorBoundaryRoute path={match.url} component={CourseSessionProgress} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CourseSessionProgressDeleteDialog} />
  </>
);

export default Routes;
