import './course.scss';

import React, { Component } from 'react';
import { Link } from 'react-router-dom';

import { Button, Card } from 'react-bootstrap';
import { Row } from 'reactstrap';
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

  onbuttonclick(courseID) {
    return '/courseSection/' + courseID;
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
          <Card key={course.id} className="coursedetail" style={{ width: '18rem', margin: '2px' }}>
            <Card.Img style={{ border: '1px solid black' }} variant="top" width="276px" height="180px" src={course.courseLogo} />
            <Card.Body>
              <Card.Title>
                <h5>{course.courseTitle}</h5>
              </Card.Title>
              <p>
                <i className="fas fa-calendar-day"></i>&nbsp;&nbsp;<span>{course.courseCreatedOn}</span>
              </p>
              <p>
                <i className="fas fa-chalkboard-teacher"></i>&nbsp;&nbsp;<span>{course.user.firstName}</span>
              </p>
              <Button
                onClick={e => {
                  // window.location.href = this.onbuttonclick(course.id);
                  window.location.href = '/videoSession/' + course.id;
                }}
              >
                <h6>View Course</h6>
              </Button>
            </Card.Body>
          </Card>
        ))}
      </Row>
    );
  }
}

export default Course;
