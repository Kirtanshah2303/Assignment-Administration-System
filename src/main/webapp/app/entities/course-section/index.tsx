import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CourseSection from './course-section';
import CourseSectionDetail from './course-section-detail';
import CourseSectionUpdate from './course-section-update';
import CourseSectionDeleteDialog from './course-section-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CourseSectionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CourseSectionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CourseSectionDetail} />
      <ErrorBoundaryRoute path={match.url} component={CourseSection} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CourseSectionDeleteDialog} />
  </>
);

export default Routes;
