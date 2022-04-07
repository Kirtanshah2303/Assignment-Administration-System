import React, { Component } from 'react';
import { Button, Card } from 'react-bootstrap';
import { useHistory } from 'react-router-dom';

interface course {
  course?: any;
}

class CourseCategoryCard extends Component<course> {
  constructor(props) {
    super(props);
  }

  render() {
    const history = useHistory();
    return (
      <div>
        <ul>
          <li>{this.props.course.courseTitle}</li>
        </ul>
      </div>
    );
  }
}

export default CourseCategoryCard;
