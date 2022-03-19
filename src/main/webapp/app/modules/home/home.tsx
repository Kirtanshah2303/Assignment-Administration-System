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
      <Col md="12" style={{ margin: '10px solid black', height: '100%' }}>
        <div className="align-items-center row">
          <div className="col-lg-5">
            <div className="mt-40 home-2-content">
              <h1 className="text-black fw-normal home-2-title display-4 mb-0">Make Your Learning Simple.</h1>
              <p className="text-white-70 mt-4 f-15 mb-0">The beautifuly thing about learning is that nobody can take it from you.</p>
              <div className="mt-5">
                <a className="btn btn-custom me-4" href="/zooki/react/">
                  Learn More
                </a>
              </div>
            </div>
          </div>
          <div className="col-lg-7">
            <div className="mt-40 home-2-content position-relative">
              <img src="/content/images/assignment.png" alt="" className="img-fluid mx-auto d-block home-2-img mover-img" />
              <div className="home-2-bottom-img">
                <img src="/zooki/react/static/media/home-2-bg.e74a5beee052680d051a.png" alt="" className="img-fluid d-block mx-auto" />
              </div>
            </div>
          </div>
        </div>
      </Col>

      {/*    <Col md="8"> */}
      {/*     <img src="content/images/assignment.png" /> */}
      {/*    </Col> */}
    </Row>
  );
};

export default Home;
