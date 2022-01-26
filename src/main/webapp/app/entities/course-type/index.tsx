import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CourseType from './course-type';
import CourseTypeDetail from './course-type-detail';
import CourseTypeUpdate from './course-type-update';
import CourseTypeDeleteDialog from './course-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CourseTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CourseTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CourseTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={CourseType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CourseTypeDeleteDialog} />
  </>
);

export default Routes;
