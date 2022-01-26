import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './course-enrollment.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseEnrollmentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const courseEnrollmentEntity = useAppSelector(state => state.courseEnrollment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="courseEnrollmentDetailsHeading">
          <Translate contentKey="assignmentAdministrationSystemApp.courseEnrollment.detail.title">CourseEnrollment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="assignmentAdministrationSystemApp.courseEnrollment.id">Id</Translate>
            </span>
          </dt>
          <dd>{courseEnrollmentEntity.id}</dd>
          <dt>
            <span id="enrollementDate">
              <Translate contentKey="assignmentAdministrationSystemApp.courseEnrollment.enrollementDate">Enrollement Date</Translate>
            </span>
          </dt>
          <dd>
            {courseEnrollmentEntity.enrollementDate ? (
              <TextFormat value={courseEnrollmentEntity.enrollementDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastAccessedDate">
              <Translate contentKey="assignmentAdministrationSystemApp.courseEnrollment.lastAccessedDate">Last Accessed Date</Translate>
            </span>
          </dt>
          <dd>
            {courseEnrollmentEntity.lastAccessedDate ? (
              <TextFormat value={courseEnrollmentEntity.lastAccessedDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="assignmentAdministrationSystemApp.courseEnrollment.user">User</Translate>
          </dt>
          <dd>{courseEnrollmentEntity.user ? courseEnrollmentEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="assignmentAdministrationSystemApp.courseEnrollment.course">Course</Translate>
          </dt>
          <dd>{courseEnrollmentEntity.course ? courseEnrollmentEntity.course.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/course-enrollment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/course-enrollment/${courseEnrollmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CourseEnrollmentDetail;
