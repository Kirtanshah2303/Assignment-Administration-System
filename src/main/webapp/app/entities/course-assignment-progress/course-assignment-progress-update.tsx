import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ICourseAssignment } from 'app/shared/model/course-assignment.model';
import { getEntities as getCourseAssignments } from 'app/entities/course-assignment/course-assignment.reducer';
import { getEntity, updateEntity, createEntity, reset } from './course-assignment-progress.reducer';
import { ICourseAssignmentProgress } from 'app/shared/model/course-assignment-progress.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseAssignmentProgressUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const courseAssignments = useAppSelector(state => state.courseAssignment.entities);
  const courseAssignmentProgressEntity = useAppSelector(state => state.courseAssignmentProgress.entity);
  const loading = useAppSelector(state => state.courseAssignmentProgress.loading);
  const updating = useAppSelector(state => state.courseAssignmentProgress.updating);
  const updateSuccess = useAppSelector(state => state.courseAssignmentProgress.updateSuccess);
  const handleClose = () => {
    props.history.push('/course-assignment-progress' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getCourseAssignments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...courseAssignmentProgressEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      courseAssignment: courseAssignments.find(it => it.id.toString() === values.courseAssignment.toString()),
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
          ...courseAssignmentProgressEntity,
          user: courseAssignmentProgressEntity?.user?.id,
          courseAssignment: courseAssignmentProgressEntity?.courseAssignment?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="assignmentAdministrationSystemApp.courseAssignmentProgress.home.createOrEditLabel"
            data-cy="CourseAssignmentProgressCreateUpdateHeading"
          >
            <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentProgress.home.createOrEditLabel">
              Create or edit a CourseAssignmentProgress
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
                  id="course-assignment-progress-id"
                  label={translate('assignmentAdministrationSystemApp.courseAssignmentProgress.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseAssignmentProgress.completed')}
                id="course-assignment-progress-completed"
                name="completed"
                data-cy="completed"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseAssignmentProgress.completedDate')}
                id="course-assignment-progress-completedDate"
                name="completedDate"
                data-cy="completedDate"
                type="date"
              />
              <ValidatedField
                id="course-assignment-progress-user"
                name="user"
                data-cy="user"
                label={translate('assignmentAdministrationSystemApp.courseAssignmentProgress.user')}
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
              <ValidatedField
                id="course-assignment-progress-courseAssignment"
                name="courseAssignment"
                data-cy="courseAssignment"
                label={translate('assignmentAdministrationSystemApp.courseAssignmentProgress.courseAssignment')}
                type="select"
              >
                <option value="" key="0" />
                {courseAssignments
                  ? courseAssignments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/course-assignment-progress" replace color="info">
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

export default CourseAssignmentProgressUpdate;
