import React, { Component } from 'react';
import ReactPlayer from 'react-player';
// import {Collapse} from "antd";
// const { Panel } = Collapse;
// import {Button} from "@mui/material";
import { Row } from 'reactstrap';
import TreeView from '@mui/lab/TreeView';
// import {TreeView} from "devextreme-react";
// import DataSource from "devextreme/data/data_source"
// import ODataStore from 'devextreme/data/odata/store';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import TreeItem from '@mui/lab/TreeItem';
import { Button, Card } from 'react-bootstrap';

interface match {
  match?: any;
  // id?:any
}

class video extends Component<match> {
  state = {
    session: [],
    videoLink: '',
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
        this.setState({ session: result });
        this.setState({ videoLink: result[0].sessionVideo });
        console.log(result);
        console.log(result[0].sessionVideo);
      })
      .catch(error => {
        console.log(error);
      });
  }

  render() {
    return (
      // <Row>
      <div className="row">
        <div className="col-sm-8">
          <ReactPlayer url={this.state.videoLink} width="100%" height="100%" />
        </div>
        <div className="col-sm-4">
          {this.state.session.map(session => (
            <div className="alert-info" role="alert" key={session.id} style={{ margin: '10px' }}>
              <Button
                variant="contained"
                onClick={() => {
                  this.setState({ videoLink: session.sessionVideo });
                }}
              >
                {' '}
                {session.sessionDescription}{' '}
              </Button>
            </div>
          ))}
        </div>
      </div>

      // </Row>
    );
  }
}

export default video;
