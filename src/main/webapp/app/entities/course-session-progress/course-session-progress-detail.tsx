import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './course-session-progress.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseSessionProgressDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const courseSessionProgressEntity = useAppSelector(state => state.courseSessionProgress.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="courseSessionProgressDetailsHeading">
          <Translate contentKey="assignmentAdministrationSystemApp.courseSessionProgress.detail.title">CourseSessionProgress</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSessionProgress.id">Id</Translate>
            </span>
          </dt>
          <dd>{courseSessionProgressEntity.id}</dd>
          <dt>
            <span id="watchSeconds">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSessionProgress.watchSeconds">Watch Seconds</Translate>
            </span>
          </dt>
          <dd>
            {courseSessionProgressEntity.watchSeconds ? (
              <TextFormat value={courseSessionProgressEntity.watchSeconds} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="assignmentAdministrationSystemApp.courseSessionProgress.user">User</Translate>
          </dt>
          <dd>{courseSessionProgressEntity.user ? courseSessionProgressEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="assignmentAdministrationSystemApp.courseSessionProgress.courseSession">Course Session</Translate>
          </dt>
          <dd>{courseSessionProgressEntity.courseSession ? courseSessionProgressEntity.courseSession.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/course-session-progress" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/course-session-progress/${courseSessionProgressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CourseSessionProgressDetail;
