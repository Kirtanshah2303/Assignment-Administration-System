import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './course-assignment-output.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseAssignmentOutputDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const courseAssignmentOutputEntity = useAppSelector(state => state.courseAssignmentOutput.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="courseAssignmentOutputDetailsHeading">
          <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentOutput.detail.title">CourseAssignmentOutput</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentOutput.id">Id</Translate>
            </span>
          </dt>
          <dd>{courseAssignmentOutputEntity.id}</dd>
          <dt>
            <span id="output">
              <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentOutput.output">Output</Translate>
            </span>
          </dt>
          <dd>{courseAssignmentOutputEntity.output}</dd>
          <dt>
            <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentOutput.courseAssignment">Course Assignment</Translate>
          </dt>
          <dd>{courseAssignmentOutputEntity.courseAssignment ? courseAssignmentOutputEntity.courseAssignment.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/course-assignment-output" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/course-assignment-output/${courseAssignmentOutputEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CourseAssignmentOutputDetail;
