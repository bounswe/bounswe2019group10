import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Layout, Menu, Row, Col,Icon,
    Avatar} from 'antd';
const { Header } = Layout;
const { SubMenu } = Menu;
import ukFlag from '../images/flags/uk.png';
import spanishFlag from '../images/flags/spanish.png';

import { userActions } from '../_actions';
import { history } from '../_helpers';

class HeaderComponent extends React.Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);
    this.addNewCourse = this.addNewCourse.bind(this);
  }
  
  logOut(){
    this.props.logOut();
    history.push('/');
  }

  addNewCourse(){
    history.push('/courses');
  }

  render() {
    return (
        <Header>
            <Row style={{ height: "64px" }}>
                <Col span={0} />
                <Col id='yallp' span={10}> 
                <Link to={{pathname: '/'}}>YALLP</Link>
                </Col>
                <Col span={6} />
                <Col span={2}>
                <Menu
                    theme="dark"
                    mode="horizontal"
                    style={{ lineHeight: '64px' }}
                >
                    <SubMenu title={
                    <span className="submenu-title-wrapper">
                      <Avatar size="small" src={ukFlag} />
                    </span>
                    }>
                    <Menu.Item
                      key="1"
                    >
                      <Avatar size="small" src={ukFlag} /> English
                    </Menu.Item>
                    <Menu.Item
                      key="3"
                    >
                      <Avatar size="small" src={spanishFlag} /> Spanish
                    </Menu.Item>
                    <Menu.Item
                      key="2"
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
    );
  }
}

function mapState(state) {
  return {  }
}

const actionCreators = {
  logOut: userActions.logout,
}

const connectedHeaderPage = connect(mapState, actionCreators)(HeaderComponent);
export { connectedHeaderPage as HeaderComponent };