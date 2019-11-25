import React, { useReducer } from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import {
    Layout, Menu, Row, Col,
    Button, Avatar
} from 'antd';
import 'antd//dist/antd.css';

import { history } from '../_helpers';
import { userActions } from '../_actions';

import { LanguageDashboardCard }  from './LanguageDashboardCard';

const { Content, Sider } = Layout;
const { MenuItem } = Menu;

class LanguageDashboard extends React.Component {
    constructor(props) {
        super(props);
        
        this.addNewCourse = this.addNewCourse.bind(this);
    }

    addNewCourse() {
        history.push('/courses');
    }

    render() {
        const profile = this.props.profile;
        let memberLanguages = [];
        // let languageLevels = [];
        // let selectedIndex = 0;
        if(profile){
            console.log(profile)
            memberLanguages = profile.memberLanguages.map((l) => l.language.languageName);
        }
        // languageLevels = profile.memberLanguages.map((l) => l.levelName);

        return (
            <Layout>
                <Sider
                    style={{overflow: 'auto', height: '100px', position: 'fixed', left: 0}}
                >
                    {/* <Menu theme="light" mode="inline" defaultSelectedKeys={['1']}>
                        {
                            memberLanguages && memberLanguages.map((memberLanguage, i) => {
                                return(
                                    <MenuItem key={i}>
                                        <p>Hello</p>
                                        <LanguageDashboardCard language={memberLanguage}/> 
                                    </MenuItem>
                                )
                            })
                        }
                    </Menu> */}
                </Sider>
                {/* <Layout style={{marginLeft: 200}}>
                <Content style={{ margin: '24px 16px 0', overflow: 'initial' }}>
                    <p>
                        {languageLevels[selectedIndex]}
                    </p>
                </Content>

                </Layout> */}
            </Layout>
        )
    }
}

function mapState(state) {
    const { users } = state;
    const { profile } = users;

    return { profile };
}

const actionCreators = {
    getProfile: userActions.getProfile
}

const connectedLanguageDashboard = connect(mapState, actionCreators)(LanguageDashboard);
export { connectedLanguageDashboard as LanguageDashboard };