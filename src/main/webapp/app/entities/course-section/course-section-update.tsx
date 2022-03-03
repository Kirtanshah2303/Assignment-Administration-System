import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICourse } from 'app/shared/model/course.model';
import { getEntities as getCourses } from 'app/entities/course/course.reducer';
import { getEntity, updateEntity, createEntity, reset } from './course-section.reducer';
import { ICourseSection } from 'app/shared/model/course-section.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseSectionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const courses = useAppSelector(state => state.course.entities);
  const courseSectionEntity = useAppSelector(state => state.courseSection.entity);
  const loading = useAppSelector(state => state.courseSection.loading);
  const updating = useAppSelector(state => state.courseSection.updating);
  const updateSuccess = useAppSelector(state => state.courseSection.updateSuccess);
  const handleClose = () => {
    props.history.push('/course-section' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCourses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...courseSectionEntity,
      ...values,
      course: courses.find(it => it.id.toString() === values.course.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...courseSectionEntity,
          course: courseSectionEntity?.course?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="assignmentAdministrationSystemApp.courseSection.home.createOrEditLabel" data-cy="CourseSectionCreateUpdateHeading">
            <Translate contentKey="assignmentAdministrationSystemApp.courseSection.home.createOrEditLabel">
              Create or edit a CourseSection
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="course-section-id"
                  label={translate('assignmentAdministrationSystemApp.courseSection.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseSection.sectionTitle')}
                id="course-section-sectionTitle"
                name="sectionTitle"
                data-cy="sectionTitle"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 10, message: translate('entity.validation.minlength', { min: 10 }) },
                  maxLength: { value: 42, message: translate('entity.validation.maxlength', { max: 42 }) },
                }}
              />
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseSection.sectionDescription')}
                id="course-section-sectionDescription"
                name="sectionDescription"
                data-cy="sectionDescription"
                type="text"
                validate={{
                  minLength: { value: 10, message: translate('entity.validation.minlength', { min: 10 }) },
                  maxLength: { value: 400, message: translate('entity.validation.maxlength', { max: 400 }) },
                }}
              />
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseSection.sectionOrder')}
                id="course-section-sectionOrder"
                name="sectionOrder"
                data-cy="sectionOrder"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseSection.isDraft')}
                id="course-section-isDraft"
                name="isDraft"
                data-cy="isDraft"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseSection.isApproved')}
                id="course-section-isApproved"
                name="isApproved"
                data-cy="isApproved"
                check
                type="checkbox"
              />
              <ValidatedField
                id="course-section-course"
                name="course"
                data-cy="course"
                label={translate('assignmentAdministrationSystemApp.courseSection.course')}
                type="select"
              >
                <option value="" key="0" />
                {courses
                  ? courses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.courseTitle}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/course-section" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CourseSectionUpdate;
