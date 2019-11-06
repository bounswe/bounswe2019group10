import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { Layout, Menu, Breadcrumb, Row, Col,
      Avatar, Descriptions, List, Input, Button } from 'antd';
import 'antd//dist/antd.css';
import './ProfilePage.css';

import { history } from '../_helpers';
import { userActions } from '../_actions';
const { Header, Content, Footer } = Layout;
const { SubMenu } = Menu;

class ProfilePage extends React.Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);
  }
  
  componentDidMount() {
    this.props.getProfile();
  }

  logOut(){
    this.props.logOut();
    history.push('/');
  }
  render() {
    const { profile } = this.props;
    return (
      <Layout className="layout menu-style">
        <Header>
          <Row style={{ height: "64px" }}>
              <Col span={0} />
              <Col id='yallp' span={10}> 
              <Link to={{pathname: '/'}}>YALLP</Link>
              </Col>
              <Col span={8} />
              <Col span={6}>
              <Menu
                  theme="dark"
                  mode="horizontal"
                  style={{ lineHeight: '64px' }}
              >
                  <SubMenu title={
                  <span className="submenu-title-wrapper">
                      <Avatar className="logo" style={{ backgroundColor: '#87d068' }} icon="user" />
                  </span>
                  }>
                  <Menu.Item
                      key="1"
                  >
                  <Link to={{pathname: '/profile-page'}}>Profile</Link>
                      
                  </Menu.Item>
                  <Menu.Item
                      key="3"
                      onClick={this.logOut}
                  >
                      Log out
                  </Menu.Item>
                  </SubMenu>
              </Menu>
              </Col>
          </Row>
      </Header>
        <Content style={{ marginTop: '24px'}}>
          <Row>
            <Col span={1} />
            <Col span={11}>
              {profile && <Profile profile={profile} updateProfile={this.props.updateProfile}/>}
            </Col>
            {/* <Col span={12}>
              <Language />
            </Col> */}
          </Row>
          
        </Content>
        <Footer style={{ textAlign: 'center' }}>
          YALLP ©2019 Created by three awesome front-end developers.
        </Footer>
      </Layout>
    )
  }
}

const languageData = [
  {
    title: 'English',
    avatar: 'https://image.flaticon.com/icons/svg/294/294059.svg',
    color: 'red'
  },
  {
    title: 'Spanish',
    avatar: 'https://image.flaticon.com/icons/svg/323/323365.svg',
    color: 'blue'
  }
]

class Profile extends React.Component {
  constructor(props) {
    super(props);
    this.handleClick = this.handleClick.bind(this);
    this.handleChange = this.handleChange.bind(this);
    const { profile } = this.props;
    this.state = {
      mail: profile.mail,
      username: profile.username,
      bio: profile.bio
    };
  }
  
  handleClick(e){
    console.log(this.state);
    this.props.updateProfile(this.state);
  }

  handleChange(e) {
    const { name, value } = e.target;
    this.setState({ [name]: value });
  }

  render() {
    const { profile } = this.props;
    return (
      <div style={{ background: '#fff', padding: 24, minHeight: 280, width:800 }}>
        <Descriptions title="User Info" bordered={true} column={1}>
          <Descriptions.Item label="E-mail">
            <Input defaultValue={profile.mail} name="mail" onChange={this.handleChange} />
          </Descriptions.Item>
          <Descriptions.Item label="User Name">
            <Input defaultValue={profile.username} name="username" onChange={this.handleChange} />
          </Descriptions.Item>
          <Descriptions.Item label="Bio">
          <Input placeholder="No bio provided." defaultValue={
              profile.bio 
              ? profile.bio 
              : ""
            } name="bio" onChange={this.handleChange} />
          </Descriptions.Item>
        </Descriptions>
        <Button type="primary" onClick={this.handleClick}>SAVE CHANGES</Button>
      </div>
    )
  }
}

class Language extends React.Component {
  render() {
      return(
        <div style={{ background: '#fff', padding: 24, minHeight: 280, width:800 }}>
          <p> <b>Language</b> </p>
          <List
            itemLayout='horizontal'
            dataSource={languageData}
            renderItem={item => (
              <List.Item>
                <List.Item.Meta
                  avatar={<Avatar src={item.avatar} />}
                  title={<a href="https://ant.design" style={{color: item.color }}>{item.title}</a>}
                  description="Ant Design, a design language for background applications, is refined by Ant UED Team"
                />
              </List.Item>
            )}
          />
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
  updateProfile: userActions.updateProfile,
  logOut: userActions.logout,
}

const connectedProfilePage = connect(mapState, actionCreators)(ProfilePage);
export { connectedProfilePage as ProfilePage };