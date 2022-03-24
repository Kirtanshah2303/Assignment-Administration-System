import './course.scss';

import React, { Component } from 'react';
import { Link } from 'react-router-dom';

import { Button, Card } from 'react-bootstrap';
import { Row } from 'reactstrap';
import any = jasmine.any;
import _ from 'lodash';
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

interface match {
  match?: any;
  // id?:any
}

class Course extends Component<match> {
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

    console.log(this.props.match);

    token = token.slice(1, -1);

    bearer = bearer + token;

    if (!this.props.match) {
      console.log('No Props available');
      fetch(`/api/courses/enrolled`, {
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
    } else {
      console.log('Props Available');
      fetch(`/api/courses/category/${this.props.match}`, {
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

    // fetch('/api/courses', {
    // fetch('/api/courses/enrolled', {
    //   fetch(`/api/courses/category/${this.props.match}`, {
    //   method: 'GET',
    //   headers: {
    //     accept: '*/*',
    //     Authorization: bearer,
    //   },
    // })
    //   .then(response => response.json())
    //   .then(result => {
    //     this.setState({ course: result });
    //     console.log(this.state);
    //   })
    //   .catch(error => {
    //     console.log(error);
    //   });
  }

  render() {
    return (
      <Row>
        {this.state.course.map(course => (
          <Card key={course.id} className="coursedetail" style={{ width: '18rem', margin: '2px' }}>
            <Card.Img style={{ border: '1px solid black' }} variant="top" width="276px" height="180px" src={course.courseLogo} />
            <Card.Body>
              <Card.Title>
                <h6 style={{ textOverflow: 'ellipsis', whiteSpace: 'nowrap', overflow: 'hidden' }}>{course.courseTitle}</h6>
              </Card.Title>
              <p>
                <svg
                  role="img"
                  aria-hidden="true"
                  focusable="false"
                  data-prefix="fas"
                  data-icon="calendar-check"
                  className="svg-inline--fa fa-calendar-check fa-w-14"
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 448 512"
                >
                  <path
                    fill="currentColor"
                    d="M436 160H12c-6.627 0-12-5.373-12-12v-36c0-26.51 21.49-48 48-48h48V12c0-6.627 5.373-12 12-12h40c6.627 0 12 5.373 12 12v52h128V12c0-6.627 5.373-12 12-12h40c6.627 0 12 5.373 12 12v52h48c26.51 0 48 21.49 48 48v36c0 6.627-5.373 12-12 12zM12 192h424c6.627 0 12 5.373 12 12v260c0 26.51-21.49 48-48 48H48c-26.51 0-48-21.49-48-48V204c0-6.627 5.373-12 12-12zm333.296 95.947l-28.169-28.398c-4.667-4.705-12.265-4.736-16.97-.068L194.12 364.665l-45.98-46.352c-4.667-4.705-12.266-4.736-16.971-.068l-28.397 28.17c-4.705 4.667-4.736 12.265-.068 16.97l82.601 83.269c4.667 4.705 12.265 4.736 16.97.068l142.953-141.805c4.705-4.667 4.736-12.265.068-16.97z"
                  ></path>
                </svg>
                <i className="fas fa-calendar-day"></i>&nbsp;&nbsp;<span>{course.courseCreatedOn}</span>
              </p>
              <p>
                <svg
                  role="img"
                  aria-hidden="true"
                  focusable="false"
                  data-prefix="fas"
                  data-icon="user-tie"
                  className="svg-inline--fa fa-user-tie fa-w-14"
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 448 512"
                >
                  <path
                    fill="currentColor"
                    d="M224 256c70.7 0 128-57.3 128-128S294.7 0 224 0 96 57.3 96 128s57.3 128 128 128zm95.8 32.6L272 480l-32-136 32-56h-96l32 56-32 136-47.8-191.4C56.9 292 0 350.3 0 422.4V464c0 26.5 21.5 48 48 48h352c26.5 0 48-21.5 48-48v-41.6c0-72.1-56.9-130.4-128.2-133.8z"
                  ></path>
                </svg>
                <i className="fas fa-chalkboard-teacher"></i>&nbsp;&nbsp;
                <span>
                  {course.user.firstName} {course.user.lastName}
                </span>
              </p>
            </Card.Body>

            {
              // _.isEqual(course.enrolled,false) ? (  <Button
              course.enrolled ? (
                <Button
                  onClick={e => {
                    // window.location.href = this.onbuttonclick(course.id);
                    window.location.href = '/videoSession/' + course.id;
                    console.log(course.enrolled);
                  }}
                >
                  <h6>View Course</h6>
                </Button>
              ) : (
                <Button
                  onClick={e => {
                    // window.location.href = this.onbuttonclick(course.id);
                    window.location.href = '/videoSession/' + course.id;
                    console.log(course.enrolled);
                  }}
                >
                  <h6>Enroll</h6>
                </Button>
              )
            }

            {/* <Button*/}
            {/*  onClick={e => {*/}
            {/*    // window.location.href = this.onbuttonclick(course.id);*/}
            {/*    window.location.href = '/videoSession/' + course.id;*/}
            {/*  }}*/}
            {/* >*/}
            {/*  <h6>View Course</h6>*/}
            {/* </Button>*/}
          </Card>
        ))}
      </Row>
    );
  }
}

export default Course;
