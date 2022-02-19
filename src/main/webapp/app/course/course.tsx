import './course.scss';

import React, { Component, useEffect } from 'react';
import { useHistory } from 'react-router-dom';

import { Card, Button } from 'react-bootstrap';

import { REDIRECT_URL } from 'app/shared/util/url-utils';
import { useAppSelector } from 'app/config/store';
import { Row } from 'reactstrap';
import { Link } from 'react-router-dom';
import CourseCard from './courseCard';
// export const Course = () => {
//   const history = useHistory();
//   const account = useAppSelector(state => state.authentication.account);
//   useEffect(() => {
//     const redirectURL = localStorage.getItem(REDIRECT_URL);
//     if (redirectURL) {
//       localStorage.removeItem(REDIRECT_URL);
//       location.href = `${location.origin}${redirectURL}`;
//     }
//   });
//   //
//   // fetch("http://localhost:8080/showAllCourses", {
//   //   method: "GET"
//   // }).then(response => response.json()).then(postMessage => {
//   //   console.log("Hello Ayush");
//   // })
//
//   return (
//     <Card className="coursedetail" style={{ width: '18rem' }}>
//       <Card.Img variant="top" width="276px" height="180px" src="content/images/img.png" />
//       <Card.Body>
//         <Card.Title>
//           <h5>IT348-CRNS</h5>
//         </Card.Title>
//         <p>
//           <i className="fas fa-calendar-day"></i>&nbsp;&nbsp;<span>10/01/2022</span>
//         </p>
//         <p>
//           <i className="fas fa-chalkboard-teacher"></i>&nbsp;&nbsp;<span>Parth Shah</span>
//         </p>
//         <Button onClick={() => history.push('/assignments')} variant="primary">
//           <h6>View Assignments</h6>
//         </Button>
//       </Card.Body>
//     </Card>
//   );
// };

class Course extends Component {
  state = {
    course: [],
  };
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    let bearer = 'Bearer ';
    let token = sessionStorage.getItem('jhi-authenticationToken');

    token = token.slice(1, -1);

    bearer = bearer + token;

    fetch('/api/courses', {
      method: 'GET',
      headers: {
        accept: '*/*',
        Authorization: bearer,
      },
    })
      .then(response => response.json())
      .then(result => {
        this.setState({ course: result });
        console.log(this.state);
      })
      .catch(error => {
        console.log(error);
      });
  }

  render() {
    return (
      <Row>
        {this.state.course.map(course => (
          <Card key={course.id} className="coursedetail" style={{ width: '18rem' }}>
            <Card.Img variant="top" width="276px" height="180px" src="content/images/img.png" />
            <Card.Body>
              <Card.Title>
                <h5>{course.courseTitle}</h5>
              </Card.Title>
              <p>
                <i className="fas fa-calendar-day"></i>&nbsp;&nbsp;<span>{course.courseCreatedOn}</span>
              </p>
              <p>
                <i className="fas fa-chalkboard-teacher"></i>&nbsp;&nbsp;<span>{course.courseDescription}</span>
              </p>
              <Link to="/assignments" className="btn btn-primary">
                <Button>
                  <h6>View Assignments</h6>
                </Button>
              </Link>
            </Card.Body>
          </Card>
        ))}
      </Row>
    );
  }
}

export default Course;
