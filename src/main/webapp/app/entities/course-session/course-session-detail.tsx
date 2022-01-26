import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './course-session.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseSessionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const courseSessionEntity = useAppSelector(state => state.courseSession.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="courseSessionDetailsHeading">
          <Translate contentKey="assignmentAdministrationSystemApp.courseSession.detail.title">CourseSession</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSession.id">Id</Translate>
            </span>
          </dt>
          <dd>{courseSessionEntity.id}</dd>
          <dt>
            <span id="sessionTitle">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSession.sessionTitle">Session Title</Translate>
            </span>
          </dt>
          <dd>{courseSessionEntity.sessionTitle}</dd>
          <dt>
            <span id="sessionDescription">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSession.sessionDescription">Session Description</Translate>
            </span>
          </dt>
          <dd>{courseSessionEntity.sessionDescription}</dd>
          <dt>
            <span id="sessionVideo">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSession.sessionVideo">Session Video</Translate>
            </span>
          </dt>
          <dd>{courseSessionEntity.sessionVideo}</dd>
          <dt>
            <span id="sessionDuration">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSession.sessionDuration">Session Duration</Translate>
            </span>
          </dt>
          <dd>
            {courseSessionEntity.sessionDuration ? (
              <TextFormat value={courseSessionEntity.sessionDuration} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="sessionOrder">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSession.sessionOrder">Session Order</Translate>
            </span>
          </dt>
          <dd>{courseSessionEntity.sessionOrder}</dd>
          <dt>
            <span id="sessionResource">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSession.sessionResource">Session Resource</Translate>
            </span>
          </dt>
          <dd>{courseSessionEntity.sessionResource}</dd>
          <dt>
            <span id="sessionQuiz">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSession.sessionQuiz">Session Quiz</Translate>
            </span>
          </dt>
          <dd>{courseSessionEntity.sessionQuiz}</dd>
          <dt>
            <span id="isPreview">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSession.isPreview">Is Preview</Translate>
            </span>
          </dt>
          <dd>{courseSessionEntity.isPreview ? 'true' : 'false'}</dd>
          <dt>
            <span id="isDraft">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSession.isDraft">Is Draft</Translate>
            </span>
          </dt>
          <dd>{courseSessionEntity.isDraft ? 'true' : 'false'}</dd>
          <dt>
            <span id="isApproved">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSession.isApproved">Is Approved</Translate>
            </span>
          </dt>
          <dd>{courseSessionEntity.isApproved ? 'true' : 'false'}</dd>
          <dt>
            <span id="isPublished">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSession.isPublished">Is Published</Translate>
            </span>
          </dt>
          <dd>{courseSessionEntity.isPublished ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="assignmentAdministrationSystemApp.courseSession.courseSection">Course Section</Translate>
          </dt>
          <dd>{courseSessionEntity.courseSection ? courseSessionEntity.courseSection.sectionTitle : ''}</dd>
        </dl>
        <Button tag={Link} to="/course-session" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/course-session/${courseSessionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CourseSessionDetail;
