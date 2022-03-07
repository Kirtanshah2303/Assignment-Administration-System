import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ICourseSession } from 'app/shared/model/course-session.model';
import { getEntities as getCourseSessions } from 'app/entities/course-session/course-session.reducer';
import { getEntity, updateEntity, createEntity, reset } from './course-session-progress.reducer';
import { ICourseSessionProgress } from 'app/shared/model/course-session-progress.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseSessionProgressUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const courseSessions = useAppSelector(state => state.courseSession.entities);
  const courseSessionProgressEntity = useAppSelector(state => state.courseSessionProgress.entity);
  const loading = useAppSelector(state => state.courseSessionProgress.loading);
  const updating = useAppSelector(state => state.courseSessionProgress.updating);
  const updateSuccess = useAppSelector(state => state.courseSessionProgress.updateSuccess);
  const handleClose = () => {
    props.history.push('/course-session-progress' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getCourseSessions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...courseSessionProgressEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          ...courseSessionProgressEntity,
          user: courseSessionProgressEntity?.user?.id,
          courseSession: courseSessionProgressEntity?.courseSession?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="assignmentAdministrationSystemApp.courseSessionProgress.home.createOrEditLabel"
            data-cy="CourseSessionProgressCreateUpdateHeading"
          >
            <Translate contentKey="assignmentAdministrationSystemApp.courseSessionProgress.home.createOrEditLabel">
              Create or edit a CourseSessionProgress
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
                  id="course-session-progress-id"
                  label={translate('assignmentAdministrationSystemApp.courseSessionProgress.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseSessionProgress.watchSeconds')}
                id="course-session-progress-watchSeconds"
                name="watchSeconds"
                data-cy="watchSeconds"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="course-session-progress-user"
                name="user"
                data-cy="user"
                label={translate('assignmentAdministrationSystemApp.courseSessionProgress.user')}
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
                id="course-session-progress-courseSession"
                name="courseSession"
                data-cy="courseSession"
                label={translate('assignmentAdministrationSystemApp.courseSessionProgress.courseSession')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/course-session-progress" replace color="info">
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

export default CourseSessionProgressUpdate;
