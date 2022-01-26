import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CourseLevel from './course-level';
import CourseLevelDetail from './course-level-detail';
import CourseLevelUpdate from './course-level-update';
import CourseLevelDeleteDialog from './course-level-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CourseLevelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CourseLevelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CourseLevelDetail} />
      <ErrorBoundaryRoute path={match.url} component={CourseLevel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CourseLevelDeleteDialog} />
  </>
);

export default Routes;
