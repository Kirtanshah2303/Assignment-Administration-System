import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './course-review-status.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseReviewStatusDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const courseReviewStatusEntity = useAppSelector(state => state.courseReviewStatus.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="courseReviewStatusDetailsHeading">
          <Translate contentKey="assignmentAdministrationSystemApp.courseReviewStatus.detail.title">CourseReviewStatus</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="assignmentAdministrationSystemApp.courseReviewStatus.id">Id</Translate>
            </span>
          </dt>
          <dd>{courseReviewStatusEntity.id}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="assignmentAdministrationSystemApp.courseReviewStatus.status">Status</Translate>
            </span>
          </dt>
          <dd>{courseReviewStatusEntity.status ? 'true' : 'false'}</dd>
          <dt>
            <span id="statusUpdatedOn">
              <Translate contentKey="assignmentAdministrationSystemApp.courseReviewStatus.statusUpdatedOn">Status Updated On</Translate>
            </span>
          </dt>
          <dd>
            {courseReviewStatusEntity.statusUpdatedOn ? (
              <TextFormat value={courseReviewStatusEntity.statusUpdatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="feedback">
              <Translate contentKey="assignmentAdministrationSystemApp.courseReviewStatus.feedback">Feedback</Translate>
            </span>
          </dt>
          <dd>{courseReviewStatusEntity.feedback}</dd>
          <dt>
            <Translate contentKey="assignmentAdministrationSystemApp.courseReviewStatus.user">User</Translate>
          </dt>
          <dd>{courseReviewStatusEntity.user ? courseReviewStatusEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="assignmentAdministrationSystemApp.courseReviewStatus.courseSession">Course Session</Translate>
          </dt>
          <dd>{courseReviewStatusEntity.courseSession ? courseReviewStatusEntity.courseSession.sessionTitle : ''}</dd>
        </dl>
        <Button tag={Link} to="/course-review-status" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/course-review-status/${courseReviewStatusEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CourseReviewStatusDetail;
