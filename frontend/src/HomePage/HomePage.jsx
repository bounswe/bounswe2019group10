import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Layout, Menu, Breadcrumb, Row, Col, Radio, Button,Alert,
    Avatar, Descriptions, List } from 'antd';
const { Header, Content, Footer } = Layout;

import { userActions } from '../_actions';
import { userService } from '../_services';

class HomePage extends React.Component {
    componentDidMount() {
        this.props.getProfile();
    }

    render() {
        const { profile } = this.props;
        console.log(profile);
        return (
            <Layout className="layout menu-style">
            <Header>
            <Row>
                <Col span={10} />
                <Col >
                <Avatar className="logo" style={{ backgroundColor: '#87d068' }} icon="user" />
                <Menu
                    theme="dark"
                    mode="horizontal"
                    defaultSelectedKeys={['1']}
                    style={{ lineHeight: '64px' }}
                >
                    <Menu.Item
                    key="1"
                    onClick={() => this.setState({selectedTab: 'Profile'})}>
                    Profile
                    </Menu.Item>
                    <Menu.Item
                    key="2"
                    onClick={() => this.setState({selectedTab: 'Languages'})}>
                    Languages
                    </Menu.Item>
                </Menu>
                </Col>
            </Row>
            </Header>
            <Content style={{ padding: '0 50px' }}>
            <p>asd </p>
            </Content>
            <Footer style={{ textAlign: 'center' }}>
            YALLP ©2019 Created by three awesome front-end developers.
            </Footer>
            </Layout>
        );
    }
}

function mapState(state) {
    const { users } = state;
    const { profile } = users;
    return { profile };
}

const actionCreators = {
    getProfile: userActions.getProfile,
    logout: userService.logout
}

const connectedHomePage = connect(mapState, actionCreators)(HomePage);
export { connectedHomePage as HomePage };