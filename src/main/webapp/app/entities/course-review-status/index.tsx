import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CourseReviewStatus from './course-review-status';
import CourseReviewStatusDetail from './course-review-status-detail';
import CourseReviewStatusUpdate from './course-review-status-update';
import CourseReviewStatusDeleteDialog from './course-review-status-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CourseReviewStatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CourseReviewStatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CourseReviewStatusDetail} />
      <ErrorBoundaryRoute path={match.url} component={CourseReviewStatus} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CourseReviewStatusDeleteDialog} />
  </>
);

export default Routes;
