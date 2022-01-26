import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ICourseSession } from 'app/shared/model/course-session.model';
import { getEntities as getCourseSessions } from 'app/entities/course-session/course-session.reducer';
import { getEntity, updateEntity, createEntity, reset } from './course-review-status.reducer';
import { ICourseReviewStatus } from 'app/shared/model/course-review-status.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseReviewStatusUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const courseSessions = useAppSelector(state => state.courseSession.entities);
  const courseReviewStatusEntity = useAppSelector(state => state.courseReviewStatus.entity);
  const loading = useAppSelector(state => state.courseReviewStatus.loading);
  const updating = useAppSelector(state => state.courseReviewStatus.updating);
  const updateSuccess = useAppSelector(state => state.courseReviewStatus.updateSuccess);
  const handleClose = () => {
    props.history.push('/course-review-status' + props.location.search);
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
      ...courseReviewStatusEntity,
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
          ...courseReviewStatusEntity,
          user: courseReviewStatusEntity?.user?.id,
          courseSession: courseReviewStatusEntity?.courseSession?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="assignmentAdministrationSystemApp.courseReviewStatus.home.createOrEditLabel"
            data-cy="CourseReviewStatusCreateUpdateHeading"
          >
            <Translate contentKey="assignmentAdministrationSystemApp.courseReviewStatus.home.createOrEditLabel">
              Create or edit a CourseReviewStatus
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
                  id="course-review-status-id"
                  label={translate('assignmentAdministrationSystemApp.courseReviewStatus.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseReviewStatus.status')}
                id="course-review-status-status"
                name="status"
                data-cy="status"
                type="text"
                validate={{
                  maxLength: { value: 20, message: translate('entity.validation.maxlength', { max: 20 }) },
                }}
              />
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseReviewStatus.statusUpdatedOn')}
                id="course-review-status-statusUpdatedOn"
                name="statusUpdatedOn"
                data-cy="statusUpdatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseReviewStatus.feedback')}
                id="course-review-status-feedback"
                name="feedback"
                data-cy="feedback"
                type="text"
                validate={{
                  maxLength: { value: 200, message: translate('entity.validation.maxlength', { max: 200 }) },
                }}
              />
              <ValidatedField
                id="course-review-status-user"
                name="user"
                data-cy="user"
                label={translate('assignmentAdministrationSystemApp.courseReviewStatus.user')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="course-review-status-courseSession"
                name="courseSession"
                data-cy="courseSession"
                label={translate('assignmentAdministrationSystemApp.courseReviewStatus.courseSession')}
                type="select"
              >
                <option value="" key="0" />
                {courseSessions
                  ? courseSessions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/course-review-status" replace color="info">
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

export default CourseReviewStatusUpdate;
