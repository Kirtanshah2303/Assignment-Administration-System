import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './course-section.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseSectionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const courseSectionEntity = useAppSelector(state => state.courseSection.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="courseSectionDetailsHeading">
          <Translate contentKey="assignmentAdministrationSystemApp.courseSection.detail.title">CourseSection</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSection.id">Id</Translate>
            </span>
          </dt>
          <dd>{courseSectionEntity.id}</dd>
          <dt>
            <span id="sectionTitle">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSection.sectionTitle">Section Title</Translate>
            </span>
          </dt>
          <dd>{courseSectionEntity.sectionTitle}</dd>
          <dt>
            <span id="sectionDescription">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSection.sectionDescription">Section Description</Translate>
            </span>
          </dt>
          <dd>{courseSectionEntity.sectionDescription}</dd>
          <dt>
            <span id="sectionOrder">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSection.sectionOrder">Section Order</Translate>
            </span>
          </dt>
          <dd>{courseSectionEntity.sectionOrder}</dd>
          <dt>
            <span id="isDraft">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSection.isDraft">Is Draft</Translate>
            </span>
          </dt>
          <dd>{courseSectionEntity.isDraft ? 'true' : 'false'}</dd>
          <dt>
            <span id="isApproved">
              <Translate contentKey="assignmentAdministrationSystemApp.courseSection.isApproved">Is Approved</Translate>
            </span>
          </dt>
          <dd>{courseSectionEntity.isApproved ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="assignmentAdministrationSystemApp.courseSection.course">Course</Translate>
          </dt>
          <dd>{courseSectionEntity.course ? courseSectionEntity.course.courseTitle : ''}</dd>
        </dl>
        <Button tag={Link} to="/course-section" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/course-section/${courseSectionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CourseSectionDetail;
