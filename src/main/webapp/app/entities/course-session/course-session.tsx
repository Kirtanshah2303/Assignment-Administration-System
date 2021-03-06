import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './course-session.reducer';
import { ICourseSession } from 'app/shared/model/course-session.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseSession = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const courseSessionList = useAppSelector(state => state.courseSession.entities);
  const loading = useAppSelector(state => state.courseSession.loading);
  const totalItems = useAppSelector(state => state.courseSession.totalItems);

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
      <h2 id="course-session-heading" data-cy="CourseSessionHeading">
        <Translate contentKey="assignmentAdministrationSystemApp.courseSession.home.title">Course Sessions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="assignmentAdministrationSystemApp.courseSession.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="assignmentAdministrationSystemApp.courseSession.home.createLabel">Create new Course Session</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {courseSessionList && courseSessionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseSession.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sessionTitle')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseSession.sessionTitle">Session Title</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sessionDescription')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseSession.sessionDescription">Session Description</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sessionVideo')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseSession.sessionVideo">Session Video</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sessionDuration')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseSession.sessionDuration">Session Duration</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sessionOrder')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseSession.sessionOrder">Session Order</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sessionResource')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseSession.sessionResource">Session Resource</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isPreview')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseSession.isPreview">Is Preview</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isDraft')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseSession.isDraft">Is Draft</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isApproved')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseSession.isApproved">Is Approved</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isPublished')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseSession.isPublished">Is Published</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sessionLocation')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseSession.sessionLocation">Session Location</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('quizLink')}>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseSession.quizLink">Quiz Link</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="assignmentAdministrationSystemApp.courseSession.courseSection">Course Section</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {courseSessionList.map((courseSession, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${courseSession.id}`} color="link" size="sm">
                      {courseSession.id}
                    </Button>
                  </td>
                  <td>{courseSession.sessionTitle}</td>
                  <td>{courseSession.sessionDescription}</td>
                  <td>{courseSession.sessionVideo}</td>
                  <td>{courseSession.sessionDuration}</td>
                  <td>{courseSession.sessionOrder}</td>
                  <td>{courseSession.sessionResource}</td>
                  <td>{courseSession.isPreview ? 'true' : 'false'}</td>
                  <td>{courseSession.isDraft ? 'true' : 'false'}</td>
                  <td>{courseSession.isApproved ? 'true' : 'false'}</td>
                  <td>{courseSession.isPublished ? 'true' : 'false'}</td>
                  <td>{courseSession.sessionLocation}</td>
                  <td>{courseSession.quizLink}</td>
                  <td>
                    {courseSession.courseSection ? (
                      <Link to={`course-section/${courseSession.courseSection.id}`}>{courseSession.courseSection.sectionTitle}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${courseSession.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${courseSession.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${courseSession.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="assignmentAdministrationSystemApp.courseSession.home.notFound">No Course Sessions found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={courseSessionList && courseSessionList.length > 0 ? '' : 'd-none'}>
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

export default CourseSession;
