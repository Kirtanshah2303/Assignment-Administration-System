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
              {/*, you can try the default accounts:*/}
              {/*<br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)*/}
              {/*<br />- User (login=&quot;user&quot; and password=&quot;user&quot;).*/}
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
        {/*<p>If you have any question on JHipster:</p>*/}
        {/*<ul>*/}
        {/*  <li>*/}
        {/*    <a href="https://www.jhipster.tech/" target="_blank" rel="noopener noreferrer">*/}
        {/*      JHipster homepage*/}
        {/*    </a>*/}
        {/*  </li>*/}
        {/*  <li>*/}
        {/*    <a href="https://stackoverflow.com/tags/jhipster/info" target="_blank" rel="noopener noreferrer">*/}
        {/*      JHipster on Stack Overflow*/}
        {/*    </a>*/}
        {/*  </li>*/}
        {/*  <li>*/}
        {/*    <a href="https://github.com/jhipster/generator-jhipster/issues?state=open" target="_blank" rel="noopener noreferrer">*/}
        {/*      JHipster bug tracker*/}
        {/*    </a>*/}
        {/*  </li>*/}
        {/*  <li>*/}
        {/*    <a href="https://gitter.im/jhipster/generator-jhipster" target="_blank" rel="noopener noreferrer">*/}
        {/*      JHipster public chat room*/}
        {/*    </a>*/}
        {/*  </li>*/}
        {/*  <li>*/}
        {/*    <a href="https://twitter.com/jhipster" target="_blank" rel="noopener noreferrer">*/}
        {/*      follow @jhipster on Twitter*/}
        {/*    </a>*/}
        {/*  </li>*/}
        {/*</ul>*/}
        {/*<p>*/}
        {/*  If you like JHipster, do not forget to give us a star on{' '}*/}
        {/*  <a href="https://github.com/jhipster/generator-jhipster" target="_blank" rel="noopener noreferrer">*/}
        {/*    GitHub*/}
        {/*  </a>*/}
        {/*  !*/}
        {/*</p>*/}
        {/*<Button onClick={() => history.push('/course')} variant="primary">*/}
        {/*  View Course*/}
        {/*</Button>*/}
      </Col>
    </Row>
  );
};

export default Home;
