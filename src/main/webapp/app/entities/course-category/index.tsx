import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CourseCategory from './course-category';
import CourseCategoryDetail from './course-category-detail';
import CourseCategoryUpdate from './course-category-update';
import CourseCategoryDeleteDialog from './course-category-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CourseCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CourseCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CourseCategoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={CourseCategory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CourseCategoryDeleteDialog} />
  </>
);

export default Routes;
