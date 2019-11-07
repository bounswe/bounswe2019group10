import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Layout, Menu, Row, Col,
    Avatar, Card,List, Spin,Skeleton } from 'antd';
const { Header, Content, Footer } = Layout;
const { SubMenu } = Menu;

import { userActions,quizActions } from '../_actions';
import { history } from '../_helpers';

import './HomePage.css';
import { HeaderComponent } from '../HeaderComponent';

class HomePage extends React.Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        this.props.getProfile();
        this.props.getQuizes();
    }

    render() {
        const { profile,quizList } = this.props;
        return (
            <Layout className="layout">
            <HeaderComponent />
            <Content style={{ padding: '0 50px' }}>
                <Row>
                    <Col span={2} />
                    <Col span={8}>

                        <Card title="English" style={{ width: 500, height: 300, marginTop: '24px' }}>
                            <div className="scrollable">
                            {quizList && quizList.map((value, index) => {
                                return (
                                <p key={index}>
                                <Link to={{
                                    pathname: '/quiz',
                                    state: {
                                        quizId: value.id
                                    }
                                    }}>Quiz {value.id} - Level: {value.level}</Link>
                                </p>
                                );
                            })}
                            </div>
                        </Card>
                        <Card title="Spanish" style={{ width: 500, height: '50vh'}}>
                            <Skeleton />
                        </Card>
                    </Col>
                    <Col span={4} />
                    <Col span={8}>
                        <Card title="Completed Quizes" style={{ width: 500, height: '50vh', marginTop: '24px' }}>
                        <Skeleton />
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
    const { users,quiz } = state;
    const { profile } = users;
    const { quizList } = quiz;
    return { profile,quizList };
}

const actionCreators = {
    getProfile: userActions.getProfile,
    getQuizes: quizActions.getQuizes
}

const connectedHomePage = connect(mapState, actionCreators)(HomePage);
export { connectedHomePage as HomePage };