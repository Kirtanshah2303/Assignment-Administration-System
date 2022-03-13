import React, { Component } from 'react';
import ReactPlayer from 'react-player';
import { Collapse } from 'antd';
const { Panel } = Collapse;
// import {Button} from "@mui/material";
import { Button, Card } from 'react-bootstrap';
import TreeView from '@mui/lab/TreeView';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import TreeItem from '@mui/lab/TreeItem';

interface match {
  match?: any;
  // id?:any
}

class video extends Component<match> {
  state = {
    session: [],
    videoLink: '',
    videoSection: [],
    videoTitle: '',
    videoDescription: '',
  };

  constructor(props) {
    super(props);
  }

  // callback(nodeId) {
  //   {
  //     this.state.videoSection.map(session => <TreeItem nodeId={session.id} key={session.id} label={session.sessionTitle} />);
  //   }
  // }

  componentDidMount() {
    let bearer = 'Bearer ';
    let token = sessionStorage.getItem('jhi-authenticationToken');

    token = token.slice(1, -1);

    bearer = bearer + token;
    const link1 = `http://localhost:8080/api/course-section/${this.props.match.params.id}`;
    const link2 = `http://localhost:8080/api/videoSessions/${this.props.match.params.id}`;
    // const link = `http://localhost:8080/api/course-section/${this.props.match.params.id}`;

    fetch(link1, {
      method: 'GET',
      headers: {
        accept: '*/*',
        Authorization: bearer,
      },
    })
      .then(response => response.json())
      .then(result => {
        this.setState({ videoSection: result });
        // this.setState({ videoLink: result[0].sessionVideo });
        console.log('Section Details are..');
        console.log(result);
        // console.log(result[0].sessionVideo);
      })
      .catch(error => {
        console.log(error);
      });

    fetch(link2, {
      method: 'GET',
      headers: {
        accept: '*/*',
        Authorization: bearer,
      },
    })
      .then(response => response.json())
      .then(result => {
        this.setState({ session: result });
        this.setState({ videoLink: result[0].sessionVideo });
        this.setState({ videoTitle: result[0].sessionTitle, videoDescription: result[0].sessionDescription });
        console.log('Session Details are..');
        console.log(result);
        // console.log(result[0].sessionVideo);
      })
      .catch(error => {
        console.log(error);
      });
  }

  render() {
    return (
      // <Row>
      <div>
        <div>
          <h5>{this.state.videoTitle}</h5>
        </div>
        <div className="row">
          <meta
            httpEquiv="Content-Security-Policy"
            content="default-src 'none'; connect-src 'self'; media-src 'https://' ; font-src 'self'; img-src 'self' data: https:; style-src 'self' ; script-src 'self'"
          />
          <div className="col-sm-8">
            <ReactPlayer style={{ border: '1px solid black' }} url={this.state.videoLink} width="100%" height="80%" controls={true} />
            <h5 style={{ marginTop: '25px' }}>Description</h5>
            <h6>{this.state.videoDescription}</h6>
          </div>
          <div className="col-sm-4">
            <TreeView
              aria-label="file system navigator"
              defaultCollapseIcon={<ExpandMoreIcon />}
              defaultExpandIcon={<ChevronRightIcon />}
              sx={{ height: '80%', flexGrow: 1, maxWidth: 400, overflowY: 'auto' }}
            >
              {this.state.videoSection.map(section => (
                <TreeItem
                  className="btn btn-dark"
                  style={{ margin: '0.15rem', color: 'white', width: '100%', textAlign: 'left', borderRadius: '0.5rem', padding: '1rem' }}
                  key={section.id}
                  nodeId={section.id}
                  label={section.sectionTitle}
                >
                  {this.state.session
                    .filter(list => list.courseSection.id === section.id)
                    .map(session => (
                      <TreeItem
                        nodeId={session.id}
                        key={session.id}
                        className="btn btn-dark"
                        style={{ color: 'white', width: '100%', textAlign: 'left' }}
                        label={
                          <Button
                            variant="contained"
                            style={{ color: 'white', textAlign: 'start', padding: '0.3rem', margin: '0.3rem' }}
                            onClick={() => {
                              this.setState({
                                videoLink: session.sessionVideo,
                                videoTitle: session.sessionTitle,
                                videoDescription: session.sessionDescription,
                              });
                              console.log('Session Link is --> ' + this.state.videoLink);
                            }}
                          >
                            <svg
                              role="img"
                              aria-hidden="true"
                              focusable="false"
                              data-prefix="fas"
                              data-icon="play-circle"
                              className="svg-inline--fa fa-play-circle fa-w-16"
                              xmlns="http://www.w3.org/2000/svg"
                              viewBox="0 0 512 512"
                              style={{ margin: '1px' }}
                            >
                              <path
                                fill="currentColor"
                                d="M256 8C119 8 8 119 8 256s111 248 248 248 248-111 248-248S393 8 256 8zm115.7 272l-176 101c-15.8 8.8-35.7-2.5-35.7-21V152c0-18.4 19.8-29.8 35.7-21l176 107c16.4 9.2 16.4 32.9 0 42z"
                              ></path>
                            </svg>
                            {session.sessionTitle}
                          </Button>
                        }
                      />

                      // <h1 key={session.id}>{session.sessionTitle}</h1>
                    ))}
                </TreeItem>
              ))}
            </TreeView>
          </div>
        </div>
      </div>

      // </Row>
    );
  }
}

export default video;
