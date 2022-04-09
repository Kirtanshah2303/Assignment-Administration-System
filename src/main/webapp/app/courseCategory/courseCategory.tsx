import './courseCategory.scss';

import React, { Component } from 'react';
import { Link } from 'react-router-dom';

import { Button, Card } from 'react-bootstrap';
import { Row } from 'reactstrap';
import any = jasmine.any;
import _ from 'lodash';
import CourseSubCategory from 'app/courseSubCategory/courseSubCategory';

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

class CourseCategory extends Component<match> {
  state = {
    course: [],
    coursecount: [],
    sub_category: [],
  };
  constructor(props) {
    super(props);
    this.subcategories = this.subcategories.bind(this);
  }

  subcategories(id) {
    console.log('hello -> ' + id);
    fetch(`/course-category/sub-categories/{id}`, {
      // fetch(`/api/courses`, {
      method: 'GET',
      headers: {
        accept: '*/*',
      },
    })
      .then(response => response.json())
      .then(resul => {
        this.setState({ sub_category: resul });
        console.log(this.state.sub_category);
      })
      .catch(error => {
        console.log(error);
      });

    //           this.props.history.push("/home");
  }

  componentDidMount() {
    let bearer = 'Bearer ';
    let token = sessionStorage.getItem('jhi-authenticationToken');

    console.log(this.props.match);

    token = token.slice(1, -1);

    bearer = bearer + token;

    fetch(`/api/course-category/parent-categories`, {
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

    fetch(`/api/course-category/get-course-count`, {
      // fetch(`/api/courses`, {
      method: 'GET',
      headers: {
        accept: '*/*',
        Authorization: bearer,
      },
    })
      .then(response => response.json())
      .then(res => {
        this.setState({ coursecount: res });
        console.log(this.state);
      })
      .catch(error => {
        console.log(error);
      });
    console.log('hEllo' + this.state.coursecount);
  }

  render() {
    return (
      <div>
        {' '}
        <h5>Available Categories.</h5> <br />
        <Row>
          {this.state.course.map(course => (
            <Card key={course.id} style={{ width: '18rem', margin: '1rem', flexWrap: 'wrap' }} border="dark" className="mb-2">
              <Card.Header></Card.Header>
              <Card.Body>
                <Card.Title>{course.courseCategoryTitle}</Card.Title>
                <Card.Text>Course Count : {this.state.coursecount[course.id]}</Card.Text>
                {/*               <Button variant="primary">View</Button> */}
              </Card.Body>
              <Button variant="primary" onClick={CourseSubCategory}></Button>
            </Card>
          ))}
        </Row>
      </div>
    );
  }
}
// onClick={e => { window.location.href = '/coursesubCategory/' + course.id;}}
export default CourseCategory;
