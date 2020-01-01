import React from 'react';
import { connect } from 'react-redux';

import {
    Layout, Row, Col, Descriotions, Input,
    Progress, Button, Card
} from 'antd';

import Avatar from 'react-avatar-edit';
import 'antd//dist/antd.css';

import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';

import { Profile, Language, Comment } from '../ProfilePage';

import { history } from '../_helpers';
import { userActions, commentActions } from '../_actions';

const { Content } = Layout;

class ProfilePageOfUser extends React.Component {
    constructor(props) {
        super(props);
        this.logOut = this.logOut.bind(this);
    }


}

function mapState(state) {
    const { users } = state;
    const { profile } = users;

    return { profile };
}

const actionCreators = {
    getProfile: userActions.getMemberProfile,
    logOut: userActions.logout,
    getComments: commentActions.getCommentsForMember
}

const connectedProfilePageOfUser = connect(mapState, actionCreators)(ProfilePageOfUser);
export { connectedProfilePageOfUser as ProfilePageOfUser};