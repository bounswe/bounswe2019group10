import React from 'react';
import { connect } from 'react-redux';

import { Layout, Row, Col } from 'antd';
import 'antd//dist/antd.css';
import './ProfilePage.css';
import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';

import { Profile } from './Profile';
import { Language } from './Language';
import { Comment } from './Comment';
import { Writing } from './Writing';

import { history } from '../_helpers';
import { userActions, commentActions, writingActions } from '../_actions';

const { Content } = Layout;

class ProfilePage extends React.Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);
  }

  componentDidMount() {
    this.props.getProfile();
    this.props.getComments();
    this.props.getMyWritings();
  }

  logOut() {
    this.props.logOut();
    history.push('/');
  }

  render() {
    const { profile, writing, comment } = this.props;
    return (
      <Layout>
        <HeaderComponent />
        <Content style={{ marginTop: '24px' }}>
          <Row>
            <Col span={8} offset={4}>
              {profile && <Profile {...profile} updateProfile={this.props.updateProfile}/>}
            </Col>
            <Col span={8} offset={1}>
              {profile && <Language {...profile} removeLanguage={this.props.removeLanguage}/>}
            </Col>
          </Row>
          <Row>
            <Col span={8} offset={4} style={{ marginTop: '24px' }}>
              {comment && <Comment {...comment}/>}
            </Col>
            <Col span={8} offset={1} style={{ marginTop: '24px' }}>
              {writing && <Writing {...writing} />}
            </Col>
          </Row>
        </Content>
        <FooterComponent />
      </Layout>
    )
  }
}

function mapState(state) {
  const { comment } = state;
  const { profile } = state.users;
  const { writing } = state;

  return { profile, comment, writing };
}

const actionCreators = {
  getProfile: userActions.getProfile,
  updateProfile: userActions.updateProfile,
  logOut: userActions.logout,
  removeLanguage: userActions.removeLanguage,
  getComments: commentActions.getComments,
  getMyWritings: writingActions.getMyWritings
}

const connectedProfilePage = connect(mapState, actionCreators)(ProfilePage);
export { connectedProfilePage as ProfilePage };