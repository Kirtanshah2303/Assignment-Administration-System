import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICourseAssignment } from 'app/shared/model/course-assignment.model';
import { getEntities as getCourseAssignments } from 'app/entities/course-assignment/course-assignment.reducer';
import { getEntity, updateEntity, createEntity, reset } from './course-assignment-output.reducer';
import { ICourseAssignmentOutput } from 'app/shared/model/course-assignment-output.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseAssignmentOutputUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const courseAssignments = useAppSelector(state => state.courseAssignment.entities);
  const courseAssignmentOutputEntity = useAppSelector(state => state.courseAssignmentOutput.entity);
  const loading = useAppSelector(state => state.courseAssignmentOutput.loading);
  const updating = useAppSelector(state => state.courseAssignmentOutput.updating);
  const updateSuccess = useAppSelector(state => state.courseAssignmentOutput.updateSuccess);
  const handleClose = () => {
    props.history.push('/course-assignment-output' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCourseAssignments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...courseAssignmentOutputEntity,
      ...values,
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
          ...courseAssignmentOutputEntity,
          courseAssignment: courseAssignmentOutputEntity?.courseAssignment?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="assignmentAdministrationSystemApp.courseAssignmentOutput.home.createOrEditLabel"
            data-cy="CourseAssignmentOutputCreateUpdateHeading"
          >
            <Translate contentKey="assignmentAdministrationSystemApp.courseAssignmentOutput.home.createOrEditLabel">
              Create or edit a CourseAssignmentOutput
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
                  id="course-assignment-output-id"
                  label={translate('assignmentAdministrationSystemApp.courseAssignmentOutput.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('assignmentAdministrationSystemApp.courseAssignmentOutput.output')}
                id="course-assignment-output-output"
                name="output"
                data-cy="output"
                type="text"
                validate={{
                  maxLength: { value: 200, message: translate('entity.validation.maxlength', { max: 200 }) },
                }}
              />
              <ValidatedField
                id="course-assignment-output-courseAssignment"
                name="courseAssignment"
                data-cy="courseAssignment"
                label={translate('assignmentAdministrationSystemApp.courseAssignmentOutput.courseAssignment')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/course-assignment-output" replace color="info">
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

export default CourseAssignmentOutputUpdate;
