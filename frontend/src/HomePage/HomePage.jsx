import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Layout, Menu, Row, Col,
    Avatar, Card,List, Spin,Skeleton } from 'antd';
const { Header, Content, Footer } = Layout;
const { SubMenu } = Menu;

import { userActions,quizActions, writingActions } from '../_actions';
import { history } from '../_helpers';

import './HomePage.css';
import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';

class HomePage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            gotQuiz: false,
            activeLanguage: "",
            activeLanguageId: -1,
            level: -1
        };
    }

    componentDidMount() {
        this.props.getProfile();
    }

    getIdandLevel() {
        let level = 0;
        let activeLanguageId = 0;
        if (this.props.profile){
            for (const [index, language] of this.props.profile.memberLanguages.entries()) {
                if (this.props.activeLanguage.languageName==language.language.languageName){
                    level = language.languageLevel;
                    activeLanguageId = language.language.id;
                    break;
                }
            }
            return [level,activeLanguageId];
        }
        return [0,0];
    }
    
    componentDidUpdate(){
        if (!this.state.gotQuiz){
            const [level,activeLanguageId] = this.getIdandLevel();
            this.props.getQuizes(activeLanguageId,level);
            this.setState({
                gotQuiz: true,
                level: level,
                activeLanguageId: activeLanguageId,
                activeLanguage: this.props.activeLanguage
            });
        }
        if (this.state.activeLanguage!=this.props.activeLanguage.languageName){
            const [level,activeLanguageId] = this.getIdandLevel();
            this.props.getQuizes(activeLanguageId,level);
            this.setState({
                gotQuiz: true,
                level: level,
                activeLanguageId: activeLanguageId,
                activeLanguage: this.props.activeLanguage.languageName
            });
        }
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
                        <Card title={ this.props.activeLanguage.languageName } style={{ width: 500, height: 300, marginTop: '24px' }}>
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
                    <Col span={2} />
                    <Col span={8}>
                        <Card title="Completed Quizes" style={{ width: 500, height: '50vh', marginTop: '24px' }}>
                        <Skeleton />
                        </Card>
                    </Col>
                </Row>
            </Content>
            <FooterComponent />
            </Layout>
        );
    }
}

function mapState(state) {
    const { users,quiz } = state;
    const { profile,activeLanguage } = users;
    const { quizList } = quiz;
    return { profile,quizList,activeLanguage };
}

const actionCreators = {
    getProfile: userActions.getProfile,
    getQuizes: quizActions.getQuizes,
    getWritingList: writingActions.getWritingList
}

const connectedHomePage = connect(mapState, actionCreators)(HomePage);
export { connectedHomePage as HomePage };