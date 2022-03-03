import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './course-assignment.reducer';
import { ICourseAssignment } from 'app/shared/model/course-assignment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseAssignment = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const courseAssignmentList = useAppSelector(state => state.courseAssignment.entities);
  const loading = useAppSelector(state => state.courseAssignment.loading);
  const totalItems = useAppSelector(state => state.courseAssignment.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="course-assignment-heading" data-cy="CourseAssignmentHeading">
        <Translate contentKey="assignmentAdministrationSystemApp.courseAssignment.home.title">Course Assignments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="assignmentAdministrationSystemApp.courseAssignment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="assignmentAdministrationSystemApp.courseAssignment.home.createLabel">
              Create new Course Assignment
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {courseAssignmentList && courseAssignmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseAssignment.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('assignmentTitle')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseAssignment.assignmentTitle">Assignment Title</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('assignmentDescription')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseAssignment.assignmentDescription">
                    Assignment Description
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('assignmentOrder')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseAssignment.assignmentOrder">Assignment Order</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('assignmentResource')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseAssignment.assignmentResource">
                    Assignment Resource
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isPreview')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseAssignment.isPreview">Is Preview</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isDraft')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseAssignment.isDraft">Is Draft</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isApproved')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseAssignment.isApproved">Is Approved</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isPublished')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseAssignment.isPublished">Is Published</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseAssignment.courseSession">Course Session</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {courseAssignmentList.map((courseAssignment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${courseAssignment.id}`} color="link" size="sm">
                      {courseAssignment.id}
                    </Button>
                  </td>
                  <td>{courseAssignment.assignmentTitle}</td>
                  <td>{courseAssignment.assignmentDescription}</td>
                  <td>{courseAssignment.assignmentOrder}</td>
                  <td>{courseAssignment.assignmentResource}</td>
                  <td>{courseAssignment.isPreview ? 'true' : 'false'}</td>
                  <td>{courseAssignment.isDraft ? 'true' : 'false'}</td>
                  <td>{courseAssignment.isApproved ? 'true' : 'false'}</td>
                  <td>{courseAssignment.isPublished ? 'true' : 'false'}</td>
                  <td>
                    {courseAssignment.courseSession ? (
                      <Link to={`course-session/${courseAssignment.courseSession.id}`}>{courseAssignment.courseSession.sessionTitle}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${courseAssignment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${courseAssignment.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${courseAssignment.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="assignmentAdministrationSystemApp.courseAssignment.home.notFound">
                No Course Assignments found
              </Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={courseAssignmentList && courseAssignmentList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default CourseAssignment;
