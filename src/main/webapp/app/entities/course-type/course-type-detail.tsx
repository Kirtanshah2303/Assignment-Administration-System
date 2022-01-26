import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './course-type.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseTypeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const courseTypeEntity = useAppSelector(state => state.courseType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="courseTypeDetailsHeading">
          <Translate contentKey="assignmentAdministrationSystemApp.courseType.detail.title">CourseType</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="assignmentAdministrationSystemApp.courseType.id">Id</Translate>
            </span>
          </dt>
          <dd>{courseTypeEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="assignmentAdministrationSystemApp.courseType.title">Title</Translate>
            </span>
          </dt>
          <dd>{courseTypeEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="assignmentAdministrationSystemApp.courseType.description">Description</Translate>
            </span>
          </dt>
          <dd>{courseTypeEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/course-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/course-type/${courseTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CourseTypeDetail;
