import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { Layout, Menu, Breadcrumb, Row, Col,
      Avatar, Descriptions, List } from 'antd';
import 'antd//dist/antd.css';
import './ProfilePage.css';
import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';

import { history } from '../_helpers';
import { userActions } from '../_actions';
const { Header, Content, Footer } = Layout;
const { SubMenu } = Menu;

class ProfilePagee extends React.Component {
  constructor(props) {
    super(props);
  }
  
  componentDidMount() {
    this.props.getProfile();
  }

  render() {
    const { profile } = this.props;
    return (
      <Layout className="layout menu-style">
        <HeaderComponent />        
        <Content style={{ marginTop: '24px'}}>
          <Row>
            <Col span={20} offset={2}>
              {profile && <Profile profile={profile} />}
            </Col>
          </Row>
        </Content>
        <FooterComponent />        
      </Layout>
    )
  }
}

class Profile extends React.Component {
  constructor(props) {
    super(props);
    console.log(props.profile);
  }
  
  render() {
    const { profile } = this.props;
    return (
      <div style={{ background: '#fff', padding: 24, minHeight: 280, width:800 }}>
        <Descriptions title="User Info" bordered={true} column={1}>
          <Descriptions.Item label="E-mail">{profile.mail}</Descriptions.Item>
          <Descriptions.Item label="User Name">{profile.username}</Descriptions.Item>
          <Descriptions.Item label="Bio">
            {
              profile.bio 
              ? profile.bio 
              : "No bio provided."
            }
          </Descriptions.Item>
        </Descriptions>
      </div>
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
  updateProfile: userActions.updateProfile
}

const connectedProfilePage = connect(mapState, actionCreators)(ProfilePage);
export { connectedProfilePage as ProfilePage };