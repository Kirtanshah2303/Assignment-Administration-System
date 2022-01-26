import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CourseSession from './course-session';
import CourseSessionDetail from './course-session-detail';
import CourseSessionUpdate from './course-session-update';
import CourseSessionDeleteDialog from './course-session-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CourseSessionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CourseSessionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CourseSessionDetail} />
      <ErrorBoundaryRoute path={match.url} component={CourseSession} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CourseSessionDeleteDialog} />
  </>
);

export default Routes;
