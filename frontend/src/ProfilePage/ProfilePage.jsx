import React from 'react';
import { connect } from 'react-redux';

import {
  Layout, Row, Col, Progress, Button, Card
} from 'antd';

import 'antd//dist/antd.css';
import './ProfilePage.css';
import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';

import { Profile } from './Profile';
import { Language } from './Language';
import { Comment } from './Comment';

import { history } from '../_helpers';
import { userActions, commentActions } from '../_actions';

const { Content } = Layout;

class ProfilePage extends React.Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);
  }

  componentDidMount() {
    this.props.getProfile();
    this.props.getComments();
  }

  logOut() {
    this.props.logOut();
    history.push('/');
  }

  render() {
    const { profile } = this.props;
    const { comments } = this.props;

    return (
      <Layout>
        <HeaderComponent />
        <Content style={{ marginTop: '24px' }}>
          <Row>
            {/* <Col span={8} offset={4}>
              {profile && <Profile {...profile} updateProfile={this.props.updateProfile} 
              selfPage={true} isHidden={false}/>}
            </Col> */}
            <Col span={8} offset={1}>
              {profile && <Language {...profile} removeLanguage={this.props.removeLanguage} 
              selfPage={false} isHidden={false}/>}
            </Col>
          </Row>
          <Row>
            <Col span={8} offset={4}>
              {comments && <Comment {...comments} />}
            </Col>
          </Row>
        </Content>
        <FooterComponent />
      </Layout>
    )
  }
}

function mapState(state) {
  const { users } = state;
  const { profile } = users;

  return { profile };
}

const actionCreators = {
  getProfile: userActions.getProfile,
  updateProfile: userActions.updateProfile,
  logOut: userActions.logout,
  removeLanguage: userActions.removeLanguage,
  getComments: commentActions.getComments
}

const connectedProfilePage = connect(mapState, actionCreators)(ProfilePage);
export { connectedProfilePage as ProfilePage };