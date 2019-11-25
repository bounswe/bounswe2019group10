import React, { useReducer } from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import {
    Layout, Menu, Row, Col, Descriptions,
    Input, Button, Typography, Avatar, Card, Icon
} from 'antd';
import 'antd//dist/antd.css';
import './ProfilePage.css';
import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';

import { flags, history } from '../_helpers';
import { userActions } from '../_actions';

const { Content } = Layout;
const { Title } = Typography;
const { Meta } = Card;

class ProfilePage extends React.Component {
    constructor(props) {
        super(props);
        this.logOut = this.logOut.bind(this);
    }

    componentDidMount() {
        this.props.getProfile();
    }

    logOut() {
        this.props.logOut();
        history.push('/');
    }

    render() {
        const { profile } = this.props;
        return (
            <Layout className="layout menu-style">
                <HeaderComponent />
                <Content style={{ marginTop: '24px' }}>
                    <Row>
                        <Col span={10} offset={1}>
                            {profile && <Profile profile={profile} />}
                        </Col>
                        <Col span={10} offset={13}>
                            {profile && <Language profile={profile} />}
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

        this.handleClick = this.handleClick.bind(this);
        this.handleChange = this.handleChange.bind(this);

        this.state = {
            isUpdating: false
        }
    }

    handleClick(e) {
        this.props.updateProfile(this.state)

        this.state.isUpdating = false
    }

    handleChange(e) {
        const { name, value } = e.target;
        this.setState({ [name]: value });
    }

    handleClickEdit(e) {
        this.state.isUpdating = true
    }

    render() {
        const { profile } = this.props;
        const { isUpdating } = this.state;
        return (
            <div style={{ background: '#fff', padding: 24, minHeight: 280, width: 800 }}>
                <Descriptions title="User Info" bordered={true} column={1}>
                    <Descriptions.Item label="E-mail">{profile.mail}</Descriptions.Item>
                    <Descriptions.Item label="User Name">{profile.username}</Descriptions.Item>
                    <Descriptions.Item label="Bio"> {profile.bio ? profile.bio : "No bio provided."}</Descriptions.Item>

                    {/* 
                    <Descriptions.Item label="E-mail">
                        <Input defaultValue={profile.mail} name="mail" onChange={this.handleChange} />
                    </Descriptions.Item>
                    <Descriptions.Item label="User Name">
                        <Input defaultValue={profile.username} name="username" onChange={this.handleChange} />
                    </Descriptions.Item>
                    <Descriptions.Item label="Bio">
                        <Input placeholder="No bio provided." defaultValue={profile.bio ? profile.bio : "" } name="bio" onChange={this.handleChange} />
                    </Descriptions.Item>
                    <Button type="primary" onClick={this.handleClick}>DONE</Button>
                    */}
                </Descriptions>
                <Col span={2} offset={11}>
                    <Button type="primary" onClick={this.handleClickEdit} style={{ marginTop: '12px' }}>EDIT</Button>
                </Col>

            </div>
        )
    }
}

class Language extends React.Component {
    constructor(props) {
        super(props);

        this.addNewCourse = this.addNewCourse.bind(this);
        this.removeCourse = this.removeCourse.bind(this);
    }

    addNewCourse() {
        history.push('/courses');
    }

    removeCourse(language) {
        this.props.removeLanguage(language)
    }

    render() {
        const { profile } = this.props;
        let memberLanguages = [];
        if (profile) {
            memberLanguages = profile.memberLanguages.map((l) => l.language.languageName);
            languageLevels = profile.memberLanguages.map((l) => l.levelName);
        }
        return (
            <Col span={16}>
                <Title style={{ paddingTop: "25px", paddingBottom: "25px" }} level={3}>Languages</Title>
                <Row>
                    {memberLanguages && memberLanguages.map((memberLanguage, i) => {
                        return (
                            <Col span={6} key={i}>
                                <Card>
                                    <Meta
                                        avatar={<Avatar size='small' src={flags[memberLanguage]} />}
                                        title={memberLanguage}
                                        description={languageLevels[i]}
                                    />
                                </Card>
                            </Col>
                        )
                    })}
                </Row>
            </Col>
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
    removeLanguage: userActions.removeLanguage
}

const connectedProfilePage = connect(mapState, actionCreators)(ProfilePage);
export { connectedProfilePage as ProfilePage };