import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Layout, Menu, Row, Col,
    Avatar} from 'antd';
const { Header } = Layout;
const { SubMenu } = Menu;

import { userActions } from '../_actions';
import { history } from '../_helpers';

class HeaderComponent extends React.Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);
  }
  
  logOut(){
    this.props.logOut();
    history.push('/');
  }

  render() {
    return (
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