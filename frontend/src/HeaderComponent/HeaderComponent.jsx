import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Layout, Menu, Row, Col,Icon,
    Avatar,Badge} from 'antd';

import { userActions } from '../_actions';
import { flags,history } from '../_helpers';
import { getBeforeSelectionText } from 'rc-mentions/lib/util';
const { Header } = Layout;
const { SubMenu } = Menu;

class HeaderComponent extends React.Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);
    this.addNewCourse = this.addNewCourse.bind(this);
    this.changeActiveLanguage = this.changeActiveLanguage.bind(this);
  }
  
  logOut(){
    this.props.logOut();
    history.push('/');
  }

  addNewCourse(){
    history.push('/courses');
  }

  changeActiveLanguage(memberLanguage){
    this.props.changeActiveLanguage(memberLanguage);
    history.push('/');
  }
  
  search(){
    history.push('/search');
  }

  notifications(){
    history.push('/notifications');
  }
  
  message(){
    history.push('/messaging');
  }

  componentDidMount() {
    this.props.getProfile();
    this.props.getNotifications();
  }

  render() {
    const { profile,activeLanguage,notifications } = this.props;
    let memberLanguages = [];
    if (profile){
      memberLanguages = profile.memberLanguages.map((l) => l.language);
    } 
    return (
      <Header>
        <Row style={{ height: "64px" }}>
          <Col span={0} />
          <Col id='yallp' span={10}> 
            <Link to={{pathname: '/'}}>YALLP</Link>
          </Col>
          <Col span={2} >
            <Badge
              count={notifications && notifications.length}   
              onClick={() => this.notifications()}>    
              <Icon type="bell" onClick={() => this.notifications()} style={{ fontSize: '18px', color: '#FFFFFF' }}/>              
            </Badge>
            <Menu
              theme="dark"
              mode="horizontal"
              style={{ lineHeight: '64px' }} 
              onClick={() => this.message()}>
              <Menu.Item>
                <Icon type="message" style={{ fontSize: '18px', color: '#FFFFFF' }}/>
              </Menu.Item>

            </Menu>
          </Col>
          <Col span={2} >
            <Menu
              theme="dark"
              mode="horizontal"
              style={{ lineHeight: '64px' }} 
              onClick={() => this.search()}>
              <Menu.Item>
              <Icon type="search" style={{ fontSize: '18px', color: '#FFFFFF' }}/>
              </Menu.Item>
            </Menu>
          </Col>
          <Col span={2}>
          <Menu
            theme="dark"
            mode="horizontal"
            style={{ lineHeight: '64px' }} >
            <SubMenu title={
              <span className="submenu-title-wrapper">
                {"languageName" in activeLanguage ?(
                  <Avatar size="small" src={flags[activeLanguage.languageName]["src"]} />
                ) : (
                  <Icon type="plus-circle" style={{ fontSize: '16px', color: '#FFFFFF' }}/>
                )}
              </span>
            }>
            {memberLanguages && memberLanguages.map((memberLanguage, i) => {
                return (
                  <Menu.Item
                    key={i} onClick={() => this.changeActiveLanguage(memberLanguage)}>
                    <Avatar size="small" src={flags[memberLanguage.languageName]["src"]} /> {memberLanguage.languageName}
                  </Menu.Item>
                  ) 
              })}
            <Menu.Item
              key="-1"
              onClick={this.addNewCourse}
            >
              <Icon type="plus-circle" /> Add a new course
            </Menu.Item>
            </SubMenu>
          </Menu>
          </Col>
          <Col span={6}>
          <Menu
              theme="dark"
              mode="horizontal"
              style={{ lineHeight: '64px' }}
          >
              <SubMenu title={
              <span className="submenu-title-wrapper">
                  <Avatar className="logo" style={{ backgroundColor: '#87d068' }} icon="user" />&nbsp;
              </span>
              }>
              <Menu.Item
                  key="1"
              >
              <Link to={{pathname: '/profile-page'}}>Profile</Link>
              </Menu.Item>
              
              <Menu.Item
                  key="2"
              >
              <Link to={{pathname: '/mywritings-page'}}>My Writings</Link>        
              </Menu.Item>

              <Menu.Item
              key="3"
              >
              <Link to={{pathname: '/writing-review-page'}}>Evaluate Writings</Link>                       
              </Menu.Item>

              <Menu.Item
              key="4"
              >
              <Link to={{pathname: '/writing-topic-suggest-page'}}>Suggest a New Writing Topic</Link>                       
              </Menu.Item>

              <Menu.Item
                  key="5"
                  onClick={this.logOut}
              >
                  Log out
              </Menu.Item>
              </SubMenu>
          </Menu>
          </Col>
        </Row>
      </Header>
    );
  }
}

function mapState(state) {
  const { profile,activeLanguage,notifications } = state.users;
  return { profile,activeLanguage,notifications };
}

const actionCreators = {
  logOut: userActions.logout,
  getProfile: userActions.getProfile,
  changeActiveLanguage: userActions.changeActiveLanguage,
  getNotifications: userActions.getNotifications
}

const connectedHeaderPage = connect(mapState, actionCreators)(HeaderComponent);
export { connectedHeaderPage as HeaderComponent };