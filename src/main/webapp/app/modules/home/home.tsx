import './home.scss';

import React from 'react';
import { Link, useHistory } from 'react-router-dom';

import { Row, Col, Alert, Button } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const history = useHistory();
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col className="col-md-6 mt-5">
        <h2>Welcome, Students!</h2> <br />
        {account?.login ? (
          <div>
            <Alert color="success">You are logged in as user {account.login}.</Alert>
          </div>
        ) : (
          <div>
            <br />
            <Alert color="warning">
              If you have already Register then
              <span>&nbsp;</span>
              <Link to="/login" className="alert-link">
                {' '}
                Sign In
              </Link>
            </Alert>
            <br />
            <Alert color="warning">
              You do not have an account yet?&nbsp;
              <Link to="/account/register" className="alert-link">
                Register a new account
              </Link>
            </Alert>
            <br />
          </div>
        )}
      </Col>
    </Row>
  );
};

export default Home;
