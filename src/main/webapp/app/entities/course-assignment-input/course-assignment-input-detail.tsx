import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './course-assignment-input.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseAssignmentInputDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const courseAssignmentInputEntity = useAppSelector(state => state.courseAssignmentInput.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="courseAssignmentInputDetailsHeading">
          <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentInput.detail.title">CourseAssignmentInput</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentInput.id">Id</Translate>
            </span>
          </dt>
          <dd>{courseAssignmentInputEntity.id}</dd>
          <dt>
            <span id="input">
              <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentInput.input">Input</Translate>
            </span>
          </dt>
          <dd>{courseAssignmentInputEntity.input}</dd>
          <dt>
            <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentInput.courseAssignment">Course Assignment</Translate>
          </dt>
          <dd>{courseAssignmentInputEntity.courseAssignment ? courseAssignmentInputEntity.courseAssignment.id : ''}</dd>
          <dt>
            <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentInput.user">User</Translate>
          </dt>
          <dd>{courseAssignmentInputEntity.user ? courseAssignmentInputEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/course-assignment-input" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/course-assignment-input/${courseAssignmentInputEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CourseAssignmentInputDetail;
