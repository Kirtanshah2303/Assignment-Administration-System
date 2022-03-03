import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICourseAssignment } from 'app/shared/model/course-assignment.model';
import { getEntities as getCourseAssignments } from 'app/entities/course-assignment/course-assignment.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './course-assignment-input.reducer';
import { ICourseAssignmentInput } from 'app/shared/model/course-assignment-input.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseAssignmentInputUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const courseAssignments = useAppSelector(state => state.courseAssignment.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const courseAssignmentInputEntity = useAppSelector(state => state.courseAssignmentInput.entity);
  const loading = useAppSelector(state => state.courseAssignmentInput.loading);
  const updating = useAppSelector(state => state.courseAssignmentInput.updating);
  const updateSuccess = useAppSelector(state => state.courseAssignmentInput.updateSuccess);
  const handleClose = () => {
    props.history.push('/course-assignment-input' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCourseAssignments({}));
    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...courseAssignmentInputEntity,
      ...values,
      courseAssignment: courseAssignments.find(it => it.id.toString() === values.courseAssignment.toString()),
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          ...courseAssignmentInputEntity,
          courseAssignment: courseAssignmentInputEntity?.courseAssignment?.id,
          user: courseAssignmentInputEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="assignmentAdministrationSystemApp.courseAssignmentInput.home.createOrEditLabel"
            data-cy="CourseAssignmentInputCreateUpdateHeading"
          >
            <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentInput.home.createOrEditLabel">
              Create or edit a CourseAssignmentInput
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
                  id="course-assignment-input-id"
                  label={translate('assignmentAdministrationSystemApp.courseAssignmentInput.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseAssignmentInput.input')}
                id="course-assignment-input-input"
                name="input"
                data-cy="input"
                type="text"
                validate={{
                  maxLength: { value: 200, message: translate('entity.validation.maxlength', { max: 200 }) },
                }}
              />
              <ValidatedField
                id="course-assignment-input-courseAssignment"
                name="courseAssignment"
                data-cy="courseAssignment"
                label={translate('assignmentAdministrationSystemApp.courseAssignmentInput.courseAssignment')}
                type="select"
              >
                <option value="" key="0" />
                {courseAssignments
                  ? courseAssignments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.assignmentTitle}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="course-assignment-input-user"
                name="user"
                data-cy="user"
                label={translate('assignmentAdministrationSystemApp.courseAssignmentInput.user')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/course-assignment-input" replace color="info">
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

export default CourseAssignmentInputUpdate;
