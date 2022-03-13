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
      <div className="row">
        <meta
          httpEquiv="Content-Security-Policy"
          content="default-src 'none'; connect-src 'self'; media-src 'https://' ; font-src 'self'; img-src 'self' data: https:; style-src 'self' ; script-src 'self'"
        />
        <div className="col-sm-8">
          <ReactPlayer url={this.state.videoLink} width="100%" height="100%" controls={true} />
        </div>
        <div className="col-sm-4">
          <TreeView
            aria-label="file system navigator"
            defaultCollapseIcon={<ExpandMoreIcon />}
            defaultExpandIcon={<ChevronRightIcon />}
            sx={{ height: 240, flexGrow: 1, maxWidth: 400, overflowY: 'auto' }}
          >
            {this.state.videoSection.map(section => (
              <TreeItem key={section.id} nodeId={section.id} label={section.sectionTitle}>
                {this.state.session
                  .filter(list => list.courseSection.id === section.id)
                  .map(session => (
                    <TreeItem
                      nodeId={session.id}
                      key={session.id}
                      label={
                        <Button
                          variant="contained"
                          onClick={() => {
                            this.setState({ videoLink: session.sessionVideo });
                            console.log('Session Link is --> ' + this.state.videoLink);
                          }}
                        >
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

      // </Row>
    );
  }
}

export default video;
