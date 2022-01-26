import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ICourse } from 'app/shared/model/course.model';
import { getEntities as getCourses } from 'app/entities/course/course.reducer';
import { getEntity, updateEntity, createEntity, reset } from './course-enrollment.reducer';
import { ICourseEnrollment } from 'app/shared/model/course-enrollment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseEnrollmentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const courses = useAppSelector(state => state.course.entities);
  const courseEnrollmentEntity = useAppSelector(state => state.courseEnrollment.entity);
  const loading = useAppSelector(state => state.courseEnrollment.loading);
  const updating = useAppSelector(state => state.courseEnrollment.updating);
  const updateSuccess = useAppSelector(state => state.courseEnrollment.updateSuccess);
  const handleClose = () => {
    props.history.push('/course-enrollment' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getCourses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...courseEnrollmentEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          ...courseEnrollmentEntity,
          user: courseEnrollmentEntity?.user?.id,
          course: courseEnrollmentEntity?.course?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="assignmentAdministrationSystemApp.courseEnrollment.home.createOrEditLabel" data-cy="CourseEnrollmentCreateUpdateHeading">
            <Translate contentKey="assignmentAdministrationSystemApp.courseEnrollment.home.createOrEditLabel">
              Create or edit a CourseEnrollment
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
                  id="course-enrollment-id"
                  label={translate('assignmentAdministrationSystemApp.courseEnrollment.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseEnrollment.enrollementDate')}
                id="course-enrollment-enrollementDate"
                name="enrollementDate"
                data-cy="enrollementDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseEnrollment.lastAccessedDate')}
                id="course-enrollment-lastAccessedDate"
                name="lastAccessedDate"
                data-cy="lastAccessedDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="course-enrollment-user"
                name="user"
                data-cy="user"
                label={translate('assignmentAdministrationSystemApp.courseEnrollment.user')}
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
                id="course-enrollment-course"
                name="course"
                data-cy="course"
                label={translate('assignmentAdministrationSystemApp.courseEnrollment.course')}
                type="select"
              >
                <option value="" key="0" />
                {courses
                  ? courses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/course-enrollment" replace color="info">
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

export default CourseEnrollmentUpdate;
