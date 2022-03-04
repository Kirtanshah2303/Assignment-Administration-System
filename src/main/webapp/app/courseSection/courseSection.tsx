import './courseSection.scss';

import React, { Component, Props, useEffect, useState } from 'react';
import { RouteComponentProps, RouteProps, useHistory, useLocation, useParams } from 'react-router-dom';

import { Card, Button } from 'react-bootstrap';

import { REDIRECT_URL } from 'app/shared/util/url-utils';
import { useAppSelector } from 'app/config/store';
import { Row } from 'reactstrap';
import { Link } from 'react-router-dom';
import { State } from 'react-toastify/dist/hooks/toastContainerReducer';
import { getUrlParameter } from 'react-jhipster';

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

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

// interface IMyProps {}
//
// interface IReactRouterParams {
//   id:string;
// }

interface match {
  match?: any;
  // id?:any
}

class courseSection extends Component<match> {
  state = {
    courseSection: [],
    // id : this.props.match.params.id
    // id : ""
  };
  constructor(props) {
    super(props);
    // this.state={
    //   courseSection: [],
    //   // id : this.props.match.params.id
    //   id : []
    // }
    // this.key = useState(getUrlParameter('id', props.location.search));
  }

  componentDidMount() {
    // this.setState({id: this.props.match.params.id})
    const demo = this.props.match.params.id;
    console.log(demo);
    console.log('HAHAHAHAHAHAHAHA' + demo);
    // this.setState({courseSection: []})

    let bearer = 'Bearer ';
    let token = sessionStorage.getItem('jhi-authenticationToken');

    token = token.slice(1, -1);

    bearer = bearer + token;

    console.log(bearer);

    // this.setState({id :this.props.match.params.id})
    // console.log(this.state.id)
    // console.log("print---> "+ this.state.id)

    // console.log("HAHAHAAH "+code);

    // const value = id.get("id");
    // console.log("JAHAHA-->" + id)

    // const link = `http://localhost:8080/api/course-sections/${this.state.id}`;
    const link = `http://localhost:8080/api/course-sections/${this.props.match.params.id}`;
    // const demo = this.state.id
    // console.log(demo);
    // const link = `/api/course-sections/${this.state.id}`;
    // const link = `http://localhost:8080/api/course-sections/${this.state.id}`;
    console.log(link);

    fetch(link + '', {
      method: 'GET',
      headers: {
        accept: '*/*',
        Authorization: bearer,
      },
    })
      .then(response => response.json())
      .then(result => {
        this.setState({ courseSection: result });
        // console.log(this.state.id);
        console.log(this.state);
      })
      .catch(error => {
        console.log(error);
      });
  }

  render() {
    // const valueofstate =  this.state.courseSection;
    // const content = this.state.courseSection.map(course => {
    //   <h1>{course.sectionTitle}</h1>
    // })
    const content = Object.keys(this.state.courseSection).map(key => {
      return (
        <Card key={key} className="coursedetail" style={{ width: '18rem' }}>
          <Card.Img variant="top" width="276px" height="180px" src="content/images/img.png" />
          <Card.Body>
            <Card.Title>
              <h5>{this.state.courseSection['sectionTitle']}</h5>
            </Card.Title>
            <p>
              <i className="fas fa-calendar-day"></i>&nbsp;&nbsp;<span>{this.state.courseSection['sectionDescription']}</span>
            </p>
            <p>
              <i className="fas fa-chalkboard-teacher"></i>&nbsp;&nbsp;<span>Parth Shah</span>
            </p>
            <Link to="/assignments" className="btn btn-primary">
              <Button>
                <h6>View Assignments</h6>
              </Button>
            </Link>
          </Card.Body>
        </Card>
        // <h1 key={key}>{key}</h1>
      );
    });
    // if (this.state.courseSection && this.state.courseSection.length){
    //   return (
    //     <div>
    //       <Row>
    //         {this.state.courseSection && this.state.courseSection.map((course) =>
    //           <Card key={course.id} className="coursedetail" style={{ width: '18rem' }}>
    //             <Card.Img variant="top" width="276px" height="180px" src="content/images/img.png" />
    //             <Card.Body>
    //               <Card.Title>
    //                 <h5>{course.sectionTitle}</h5>
    //               </Card.Title>
    //               <p>
    //                 <i className="fas fa-calendar-day"></i>&nbsp;&nbsp;<span>{course.sectionDescription}</span>
    //               </p>
    //               <p>
    //                 <i className="fas fa-chalkboard-teacher"></i>&nbsp;&nbsp;<span>Parth Shah</span>
    //               </p>
    //               <Link to="/assignments" className="btn btn-primary">
    //                 <Button>
    //                   <h6>View Assignments</h6>
    //                 </Button>
    //               </Link>
    //             </Card.Body>
    //           </Card>
    //         )}
    //       </Row>
    //     </div>
    //
    //   );
    // }else {
    return (
      <div>
        <Row>{content}</Row>
      </div>
    );
  }
}

export default courseSection;
