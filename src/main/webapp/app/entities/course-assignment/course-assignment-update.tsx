import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICourseSession } from 'app/shared/model/course-session.model';
import { getEntities as getCourseSessions } from 'app/entities/course-session/course-session.reducer';
import { getEntity, updateEntity, createEntity, reset } from './course-assignment.reducer';
import { ICourseAssignment } from 'app/shared/model/course-assignment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseAssignmentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const courseSessions = useAppSelector(state => state.courseSession.entities);
  const courseAssignmentEntity = useAppSelector(state => state.courseAssignment.entity);
  const loading = useAppSelector(state => state.courseAssignment.loading);
  const updating = useAppSelector(state => state.courseAssignment.updating);
  const updateSuccess = useAppSelector(state => state.courseAssignment.updateSuccess);
  const handleClose = () => {
    props.history.push('/course-assignment' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCourseSessions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...courseAssignmentEntity,
      ...values,
      courseSession: courseSessions.find(it => it.id.toString() === values.courseSession.toString()),
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
          ...courseAssignmentEntity,
          courseSession: courseAssignmentEntity?.courseSession?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="assignmentAdministrationSystemApp.courseAssignment.home.createOrEditLabel" data-cy="CourseAssignmentCreateUpdateHeading">
            <Translate contentKey="assignmentAdministrationSystemApp.courseAssignment.home.createOrEditLabel">
              Create or edit a CourseAssignment
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
                  id="course-assignment-id"
                  label={translate('assignmentAdministrationSystemApp.courseAssignment.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseAssignment.assignmentTitle')}
                id="course-assignment-assignmentTitle"
                name="assignmentTitle"
                data-cy="assignmentTitle"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 10, message: translate('entity.validation.minlength', { min: 10 }) },
                  maxLength: { value: 42, message: translate('entity.validation.maxlength', { max: 42 }) },
                }}
              />
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseAssignment.assignmentDescription')}
                id="course-assignment-assignmentDescription"
                name="assignmentDescription"
                data-cy="assignmentDescription"
                type="text"
                validate={{
                  minLength: { value: 10, message: translate('entity.validation.minlength', { min: 10 }) },
                  maxLength: { value: 400, message: translate('entity.validation.maxlength', { max: 400 }) },
                }}
              />
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseAssignment.assignmentOrder')}
                id="course-assignment-assignmentOrder"
                name="assignmentOrder"
                data-cy="assignmentOrder"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseAssignment.assignmentResource')}
                id="course-assignment-assignmentResource"
                name="assignmentResource"
                data-cy="assignmentResource"
                type="text"
                validate={{
                  minLength: { value: 10, message: translate('entity.validation.minlength', { min: 10 }) },
                  maxLength: { value: 42, message: translate('entity.validation.maxlength', { max: 42 }) },
                }}
              />
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseAssignment.isPreview')}
                id="course-assignment-isPreview"
                name="isPreview"
                data-cy="isPreview"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseAssignment.isDraft')}
                id="course-assignment-isDraft"
                name="isDraft"
                data-cy="isDraft"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseAssignment.isApproved')}
                id="course-assignment-isApproved"
                name="isApproved"
                data-cy="isApproved"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseAssignment.isPublished')}
                id="course-assignment-isPublished"
                name="isPublished"
                data-cy="isPublished"
                check
                type="checkbox"
              />
              <ValidatedField
                id="course-assignment-courseSession"
                name="courseSession"
                data-cy="courseSession"
                label={translate('assignmentAdministrationSystemApp.courseAssignment.courseSession')}
                type="select"
              >
                <option value="" key="0" />
                {courseSessions
                  ? courseSessions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.sessionTitle}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/course-assignment" replace color="info">
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

export default CourseAssignmentUpdate;
