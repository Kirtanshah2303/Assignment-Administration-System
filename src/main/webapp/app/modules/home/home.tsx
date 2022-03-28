import './home.scss';

import React from 'react';
import { Link, useHistory } from 'react-router-dom';

import { Row, Col, Alert, Button } from 'reactstrap';

import { useAppSelector } from 'app/config/store';
import Course from 'app/course/course';

export const Home = () => {
  const history = useHistory();
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      {/* <Col md="3" className="pad">*/}
      {/*  <span className="hipster rounded" />*/}
      {/* </Col>*/}
      {/* <Col className="col-md-6 mt-5">*/}
      {/*   <h2>Welcome, Students!</h2> <br />*/}
      {account?.login ? (
        <div>
          {/* <Alert color="success">You are logged in as user {account.login}.</Alert>*/}
          {/* <Course match={'25'} />*/}
          <Course />
        </div>
      ) : (
        <div>
          <Row>
            <Col md="12" style={{ margin: '10px solid black', height: '100%', padding: '3%' }}>
              <div className="align-items-center row">
                <div className="col-lg-5">
                  <div className="mt-40 home-2-content">
                    <h1 className="text-black fw-normal home-2-title display-4 mb-0" style={{ color: 'blue' }}>
                      Make Your Learning Simple.
                    </h1>
                    <h5 className="text-white-70 mt-4 f-15 mb-0">
                      The beautiful thing about learning is that nobody can take it from you.
                    </h5>
                    <div className="mt-5">
                      <a href="/login">
                        <button className="btn btn-primary col-md-5" type="submit">
                          {' '}
                          Sign in{' '}
                        </button>
                      </a>
                      &nbsp;
                      <a href="/account/register">
                        <button className="btn btn-primary col-md-5" type="button">
                          {' '}
                          Register{' '}
                        </button>
                      </a>
                    </div>
                  </div>
                </div>
                <div className="col-lg-7">
                  <div className="mt-40 home-2-content position-relative">
                    <img src="/content/images/assignment.png" alt="" className="img-fluid mx-auto d-block home-2-img mover-img" />
                  </div>
                </div>
              </div>
            </Col>
          </Row>
        </div>
      )}
      {/* </Col>*/}
    </Row>
  );
};

export default Home;
