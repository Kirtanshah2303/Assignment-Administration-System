import './courseSubCategory.scss';

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
  id?: any;
}

class CourseSubCategory extends Component<match> {
  state = {
    course: [],
    coursecount: [],
    sub_category: [],
  };
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    let bearer = 'Bearer ';
    let token = sessionStorage.getItem('jhi-authenticationToken');

    console.log(this.props.match);

    token = token.slice(1, -1);

    bearer = bearer + token;

    fetch(`/api/course-category/sub-categories/${this.props.match.params.id}`, {
      // fetch(`/api/courses`, {
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

    fetch(`/api/course-category/${this.props.match.params.id}/sub-category/get-course-count`, {
      // fetch(`/api/courses`, {
      method: 'GET',
      headers: {
        accept: '*/*',
        Authorization: bearer,
      },
    })
      .then(response => response.json())
      .then(result => {
        this.setState({ coursecount: result });
        console.log(this.state);
      })
      .catch(error => {
        console.log(error);
      });
  }

  render() {
    return (
      //     <div>Hello This is Sub Categories</div>

      <div>
        {' '}
        <h5>Available Sub Categories.</h5> <br />
        <Row>
          {this.state.course.map(course => (
            <Card key={course.id} style={{ width: '18rem', margin: '1rem', flexWrap: 'wrap' }} border="dark" className="mb-2">
              <Card.Header></Card.Header>
              <Card.Body>
                <Card.Title>{course.courseCategoryTitle}</Card.Title>
                <Card.Text>Course Count : {this.state.coursecount[course.id]}</Card.Text>
                {/*               <Button variant="primary">View</Button> */}
              </Card.Body>
              <Button variant="primary">View</Button>
            </Card>
          ))}
        </Row>
      </div>
    );
  }
}

export default CourseSubCategory;