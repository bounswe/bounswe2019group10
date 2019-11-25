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
import { LanguageDashboard } from './LanguageDashboard';

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
        console.log(profile)
        return (
            <Layout className="layout menu-style">
                <HeaderComponent />
                <Content style={{ marginTop: '24px' }}>
                    <Row>
                        <Col span={10} offset={4}>
                            {profile && <Profile {...profile} />}
                        </Col>
                        <Col span={10} offset={13}>
                            {/* {profile && <LanguageDashboard />} */}
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
        this.handleEditButton = this.handleEditButton.bind(this);
        this.handleDoneButton = this.handleDoneButton.bind(this);

        this.state = {
            isEditing: false
        }
    }

    handleClick(e) {
        this.props.updateProfile(this.state)
    }

    handleChange(e) {
        const { name, value } = e.target;
        this.setState({ [name]: value });
    }

    handleEditButton(e) {
        this.setState({ isEditing: true })
    }

    handleDoneButton(e) {
        this.setState({ isEditing: false })
    }

    render() {
        const { mail, username, bio, name, surname } = this.props;

        return (
            <div style={{ background: '#fff', padding: 24, minHeight: 280, width: 600 }}>
                <Descriptions title="User Info" bordered={true} column={1}>
                    <Descriptions.Item label="Name">
                        {
                            this.state.isEditing ?
                                <Input placeholder={name ? name : "No name!"}
                                    defaultValue={name} name="name" onChange={this.handleChange} />
                                : name ? name : "No name!"
                        }
                    </Descriptions.Item>
                    <Descriptions.Item label="Surname">
                        {
                            this.state.isEditing ?
                                <Input placeholder={surname ? surname : "No surname!"}
                                    defaultValue={surname} name="surname" onChange={this.handleChange} />
                                : surname ? surname : "No surname!"
                        }
                    </Descriptions.Item>
                    <Descriptions.Item label="User Name">
                        {
                            this.state.isEditing ?
                                <Input placeholder={username ? username : "No username!"}
                                    defaultValue={username} name="mail" onChange={this.handleChange} />
                                : username ? username : "No username!"
                        }
                    </Descriptions.Item>
                    <Descriptions.Item label="E-mail">
                        {
                            this.state.isEditing ?
                                <Input placeholder={mail ? mail : "No mail!"}
                                    defaultValue={mail} name="mail" onChange={this.handleChange} />
                                : mail ? mail : "No mail!"
                        }
                    </Descriptions.Item>
                    <Descriptions.Item label="Bio">
                        {
                            this.state.isEditing ?
                                <Input placeholder={bio ? bio : "No biography!"}
                                    defaultValue={bio ? bio : ""} name="bio" onChange={this.handleChange} />
                                : bio ? bio : "No bio!"
                        }
                    </Descriptions.Item>
                </Descriptions>
                <Button type="default"
                    style={{marginTop:'12px'}}
                    onClick={ this.state.isEditing ? this.handleDoneButton : this.handleEditButton}>
                    { this.state.isEditing ? "DONE" : "EDIT PROFILE" }
                </Button>
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
    removeLanguage: userActions.removeLanguage
}

const connectedProfilePage = connect(mapState, actionCreators)(ProfilePage);
export { connectedProfilePage as ProfilePage };