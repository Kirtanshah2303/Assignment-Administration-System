import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './course-assignment-progress.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseAssignmentProgressDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const courseAssignmentProgressEntity = useAppSelector(state => state.courseAssignmentProgress.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="courseAssignmentProgressDetailsHeading">
          <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentProgress.detail.title">
            CourseAssignmentProgress
          </Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentProgress.id">Id</Translate>
            </span>
          </dt>
          <dd>{courseAssignmentProgressEntity.id}</dd>
          <dt>
            <span id="completed">
              <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentProgress.completed">Completed</Translate>
            </span>
          </dt>
          <dd>{courseAssignmentProgressEntity.completed ? 'true' : 'false'}</dd>
          <dt>
            <span id="completedDate">
              <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentProgress.completedDate">Completed Date</Translate>
            </span>
          </dt>
          <dd>
            {courseAssignmentProgressEntity.completedDate ? (
              <TextFormat value={courseAssignmentProgressEntity.completedDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentProgress.user">User</Translate>
          </dt>
          <dd>{courseAssignmentProgressEntity.user ? courseAssignmentProgressEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentProgress.courseAssignment">
              Course Assignment
            </Translate>
          </dt>
          <dd>{courseAssignmentProgressEntity.courseAssignment ? courseAssignmentProgressEntity.courseAssignment.assignmentTitle : ''}</dd>
        </dl>
        <Button tag={Link} to="/course-assignment-progress" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/course-assignment-progress/${courseAssignmentProgressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CourseAssignmentProgressDetail;
