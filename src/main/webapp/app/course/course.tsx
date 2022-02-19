import './course.scss';

import React, { useEffect } from 'react';
import { useHistory } from 'react-router-dom';

import { Card, Button } from 'react-bootstrap';

import { REDIRECT_URL } from 'app/shared/util/url-utils';
import { useAppSelector } from 'app/config/store';

export const Course = () => {
  const history = useHistory();
  const account = useAppSelector(state => state.authentication.account);
  useEffect(() => {
    const redirectURL = localStorage.getItem(REDIRECT_URL);
    if (redirectURL) {
      localStorage.removeItem(REDIRECT_URL);
      location.href = `${location.origin}${redirectURL}`;
    }
  });
  //
  // fetch("http://localhost:8080/showAllCourses", {
  //   method: "GET"
  // }).then(response => response.json()).then(postMessage => {
  //   console.log("Hello Ayush");
  // })

  return (
    <Card className="coursedetail" style={{ width: '18rem' }}>
      <Card.Img variant="top" width="276px" height="180px" src="content/images/img.png" />
      <Card.Body>
        <Card.Title>
          <h5>IT348-CRNS</h5>
        </Card.Title>
        <p>
          <i className="fas fa-calendar-day"></i>&nbsp;&nbsp;<span>10/01/2022</span>
        </p>
        <p>
          <i className="fas fa-chalkboard-teacher"></i>&nbsp;&nbsp;<span>Parth Shah</span>
        </p>
        <Button onClick={() => history.push('/assignments')} variant="primary">
          <h6>View Assignments</h6>
        </Button>
      </Card.Body>
    </Card>
  );
};

export default Course;
