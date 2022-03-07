import React, { Component } from 'react';
import ReactPlayer from 'react-player';

interface match {
  match?: any;
  // id?:any
}

class video extends Component<match> {
  state = {
    session: [],
  };

  constructor(props) {
    super(props);
  }

  componentDidMount() {
    let bearer = 'Bearer ';
    let token = sessionStorage.getItem('jhi-authenticationToken');

    token = token.slice(1, -1);

    bearer = bearer + token;
    const link = `http://localhost:8080/api/videoSession/${this.props.match.params.id}`;

    fetch(link, {
      method: 'GET',
      headers: {
        accept: '*/*',
        Authorization: bearer,
      },
    })
      .then(response => response.json())
      .then(result => {
        // this.setState({ course: result });
        console.log(result);
      })
      .catch(error => {
        console.log(error);
      });
  }

  render() {
    return (
      <div className="App">
        <>
          <meta charSet="utf-8" />
          <title>Udemy</title>
          {/* {/<link rel="stylesheet" href="./styles.css" />/} */}
          <link rel="stylesheet" href="./style.css" />
          <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
          <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />

          <div className="grid-container">
            <div className="grid-item1">
              <div className="player-wrapper">
                <ReactPlayer className="react-player" url="https://youtu.be/4d8m59ct2wQ" width="900px" height="500px" controls={true} />
              </div>
            </div>
            <div className="grid-item2">
              <h2>Course content</h2>
              <div className="vertical-menu">
                <a href="#" className="active">
                  Home
                </a>
                <a href="#">Link 1</a>
                <a href="#">Link 2</a>
                <a href="#">Link 3</a>
                <a href="#">Link 4</a>
                <a href="#">Link 5</a>
                <a href="#">Link 6</a>
                <a href="#">Link 1</a>
                <a href="#">Link 2</a>
                <a href="#">Link 3</a>
                <a href="#">Link 4</a>
                <a href="#">Link 5</a>
                <a href="#">Link 6</a>
              </div>
            </div>
          </div>
        </>
      </div>
    );
  }
}

export default video;
