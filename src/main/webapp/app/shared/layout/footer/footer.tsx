import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = () => (
  <div className="footer">
    <p style={{ textAlign: 'center' }}>
      <b>© Copyright CHARUSAT | All rights reserved</b>
    </p>
    <div className="logo-img">
      <img src="../../../content/images/Charusat-logo.png" style={{ height: '15%', width: '15%' }} />
    </div>
  </div>
);

export default Footer;
