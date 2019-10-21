import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Layout, Menu, Row, Col,
    Avatar, Card } from 'antd';
const { Header, Content, Footer } = Layout;
const { SubMenu } = Menu;

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
                <Row style={{ height: "64px" }}>
                    <Col span={0} />
                    <Col id='yallp' span={10}> 
                    YALLP 
                    </Col>
                    <Col span={8} />
                    <Col span={6}>
                    <Menu
                        theme="dark"
                        mode="horizontal"
                        defaultSelectedKeys={['1']}
                        style={{ lineHeight: '64px' }}
                    >
                        <SubMenu title={
                        <span className="submenu-title-wrapper">
                            <Avatar className="logo" style={{ backgroundColor: '#87d068' }} icon="user" />
                        </span>
                        }>
                        <Menu.Item
                            key="1"
                            // onClick={() => this.setState({selectedTab: 'Profile'})}
                        >
                            Profile
                        </Menu.Item>
                        <Menu.Item
                            key="3"
                        >
                            Log out
                        </Menu.Item>
                        </SubMenu>
                    </Menu>
                    </Col>
                </Row>
            </Header>
            <Content style={{ padding: '0 50px' }}>
                <Row>
                    <Col span={2} />
                    <Col span={8}>
                        <Card title="English" style={{ width: 500, height: '50vh', marginTop: '24px' }}>
                            <p>Card content</p>
                            <p>Card content</p>
                            <p>Card content</p>
                        </Card>
                        <Card title="Spanish" style={{ width: 500, height: '50vh'}}>
                            <p>Card content</p>
                            <p>Card content</p>
                            <p>Card content</p>
                        </Card>
                    </Col>
                    <Col span={4} />
                    <Col span={8}>
                        <Card title="Default size card" style={{ width: 500, height: '50vh', marginTop: '24px' }}>
                        <p>Card content</p>
                        <p>Card content</p>
                        <p>Card content</p>
                        </Card>
                    </Col>
                </Row>
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