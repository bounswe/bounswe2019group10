import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Layout, Menu, Row, Col,
    Avatar, Card,List, Spin,Skeleton,Typography,Empty } from 'antd';
const { Header, Content, Footer } = Layout;
const { SubMenu } = Menu;
const { Title } = Typography;

import { userActions,quizActions, writingActions } from '../_actions';
import { history } from '../_helpers';

import './HomePage.css';
import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';
import { WritingResultComponent } from '../SearchPage/WritingResultComponent';
import { QuizResultComponent } from '../SearchPage/QuizResultComponent';

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
            this.props.getWritingList(activeLanguageId);
        }
    }

    render() {
        const { profile,quizList } = this.props;
        let searchResults = [];
        if (this.props.writing){
            searchResults = this.props.writing.result;
        }
        return (
            <Layout className="layout">
            <HeaderComponent />
            <Content style={{ padding: '0 50px'}}>
                <Row>
                    <Col span={2} />
                    <Col span={8}>
                        <Title level={3} style={{textAlign: "center"}}>Quizes</Title>                        
                        {quizList && (
                        quizList.map((searchResult, i) => {
                        return (
                            <Row key={i}>
                            <QuizResultComponent quizId={searchResult.quiz.id} quizType={searchResult.quiz.quizType} 
                                levelName={searchResult.quiz.levelName} solved={searchResult.solved} score={searchResult.score} />
                            </Row>
                            ) 
                        }))}
                        {(quizList && quizList.length==0) && <Empty description="No quiz found :("/>}
                    </Col>
                    <Col span={8} offset={2}>
                        <Title level={3} style={{textAlign: "center"}}>Writing Exercises</Title>
                        { searchResults && (
                            searchResults.map((searchResult, i) => {
                            return (
                                <Row key={i}>
                                <WritingResultComponent writingId={searchResult.writingDTO.id} writingName={searchResult.writingDTO.writingName} 
                                    taskText={searchResult.writingDTO.taskText} solved={searchResult.solved} />
                                </Row>
                            ) 
                        }))}
                        {(searchResults && searchResults.length==0) && <Empty description="No writing found :("/>}
                    </Col>
                </Row>
            </Content>
            <FooterComponent />
            </Layout>
        );
    }
}

function mapState(state) {
    const { users,quiz,writing } = state;
    const { profile,activeLanguage } = users;
    const { quizList } = quiz;
    return { profile,quizList,activeLanguage,writing };
}

const actionCreators = {
    getProfile: userActions.getProfile,
    getQuizes: quizActions.getQuizes,
    getWritingList: writingActions.getWritingList
}

const connectedHomePage = connect(mapState, actionCreators)(HomePage);
export { connectedHomePage as HomePage };