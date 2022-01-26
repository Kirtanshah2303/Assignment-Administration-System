import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './course-level.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseLevelDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const courseLevelEntity = useAppSelector(state => state.courseLevel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="courseLevelDetailsHeading">
          <Translate contentKey="assignmentAdministrationSystemApp.courseLevel.detail.title">CourseLevel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="assignmentAdministrationSystemApp.courseLevel.id">Id</Translate>
            </span>
          </dt>
          <dd>{courseLevelEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="assignmentAdministrationSystemApp.courseLevel.title">Title</Translate>
            </span>
          </dt>
          <dd>{courseLevelEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="assignmentAdministrationSystemApp.courseLevel.description">Description</Translate>
            </span>
          </dt>
          <dd>{courseLevelEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/course-level" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/course-level/${courseLevelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CourseLevelDetail;
