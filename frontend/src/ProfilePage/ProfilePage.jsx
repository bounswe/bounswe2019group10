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
          
            <Col span={8} offset={4}>
              {profile && <Profile {...profile} updateProfile={this.props.updateProfile}/>}
            </Col>
            <Col span={8} offset={1}>
              {profile && <Language {...profile} removeLanguage={this.props.removeLanguage}/>}
            </Col>
        </Content>
        <FooterComponent />
      </Layout>
    )
  }
}

class Profile extends React.Component {
  constructor(props) {
    super(props);

    this.handleChange = this.handleChange.bind(this);
    this.handleEditButton = this.handleEditButton.bind(this);
    this.handleDoneButton = this.handleDoneButton.bind(this);

    this.state = {
      isEditing: false,
      name: this.props.name,
      surname: this.props.surname,
      username: this.props.username,
      mail: this.props.mail,
      bio: this.props.bio,
    }
  }

  handleChange(e) {
    const { name, value } = e.target;
    this.setState({ [name]: value });
  }

  handleEditButton(e) {
    this.setState({ isEditing: true });
  }

  handleDoneButton(e) {
    const newProfile={
      bio: this.state.bio,
      name: this.state.name,
      surname: this.state.surname,
      username: this.state.username,
      mail: this.state.mail,
    };
    this.props.updateProfile(newProfile);
    this.setState({ isEditing: false });
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
                  defaultValue={username} name="username" onChange={this.handleChange} />
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
          style={{ marginTop: '12px' }}
          onClick={this.state.isEditing ? this.handleDoneButton : this.handleEditButton}>
          {this.state.isEditing ? "DONE" : "EDIT PROFILE"}
        </Button>
      </div>
    )
  }
}

class Language extends React.Component {
  constructor(props) {
    super(props);
    this.handleRemoveButton = this.handleRemoveButton.bind(this);
  }

  handleRemoveButton(language){
    this.props.removeLanguage(language)
  }

  render() {
    const { memberLanguages } = this.props;
    
    return (
      <div style={{ background: '#fff', padding: 24, minHeight: 280, width: 600 }}>
        <Descriptions title="Language Dashboard" bordered={true} column={1}>
          {
            memberLanguages && memberLanguages.map((memberLanguage, i) => {
              return (
                <Descriptions.Item label={memberLanguage.language.languageName} key={i}>
                  {memberLanguage.levelName}
                  {!memberLanguage.levelName && "Take level test"}
                  <Button type="link" onClick={() => this.handleRemoveButton(memberLanguage.language.languageName)}> Remove</Button>
                </Descriptions.Item>
              )
            })
          }

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
  updateProfile: userActions.updateProfile,
  logOut: userActions.logout,
  removeLanguage: userActions.removeLanguage
}

const connectedProfilePage = connect(mapState, actionCreators)(ProfilePage);
export { connectedProfilePage as ProfilePage };