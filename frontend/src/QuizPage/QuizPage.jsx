import React from 'react';
import { connect } from 'react-redux';
import { Layout, Menu, Breadcrumb, Row, Col, Radio, Button,Alert,
  Avatar, Descriptions, List } from 'antd';
const { Header, Content, Footer } = Layout;
import { quizActions } from '../_actions';
import './QuizPage.css';

class QuizPage extends React.Component {
    constructor(props) {
      super(props);
      this.state = {
        selectedOption: '',
        quiz: [],
        current: 0,
        showScore: false,
        score: 0,
        answers: [],
        isSubmit: false,
        optionClassNames: {},
        quizFinished: false
      };
      this.handleOptionChange = this.handleOptionChange.bind(this);
      this.handleFormSubmit = this.handleFormSubmit.bind(this);
      this.getInitialState = this.getInitialState.bind(this);
    }
    componentDidMount() {
      this.props.getQuiz(1);
    }
    getInitialState() {
      this.setState({
        selectedOption: ""
      });
    }

    handleOptionChange(changeEvent) {
      this.setState({
        selectedOption: changeEvent.target.value
      });
    }

    handleFormSubmit(formSubmitEvent) {
      console.log('You have selected:', this.state);
      if (this.state.isSubmit){
        const quiz = this.props.quiz.quiz;
        const questions = quiz.questions;
        console.log("questions leng",questions.length,this.state.current);
        if (questions.length-1 == this.state.current){
          console.log("asdsd");
          console.log(quiz.id,this.state.answers);
          this.props.submitQuiz(quiz.id,this.state.answers);
          this.setState({
            quizFinished: true
          });
        }else {
          this.setState({
            current: this.state.current+1,
            selectedOption: "",
            isSubmit: false,
            optionClassNames: {}
          });
        }
      } else {
        const question = this.props.quiz.quiz.questions[this.state.current];
        let optionClassNames = {}
        optionClassNames[this.state.selectedOption] = "wrongOption";
        optionClassNames[question.correctChoiceId] = "correctOption";
        this.setState({
          isSubmit: true,
          answers: [...this.state.answers, {"questionId":question.id,"choiceId":this.state.selectedOption}],
          optionClassNames: optionClassNames
        });
      }
    }

    render() {
      const radioStyle = {
        display: 'block',
        height: '30px',
        lineHeight: '30px',
        margin: '20px',
        paddingLeft: "10px",
        paddingRight: "10px",
        paddingTop: "10px",
        paddingBottom: "40px",
      };
      const { quiz } = this.props;
      let question = {};
      let options = [];
      if (Object.keys(quiz).length!=0 && !this.state.quizFinished){
        if (quiz.quiz.questions.length > this.state.current){
          question = quiz.quiz.questions[this.state.current];
          options.push(question.firstChoice);
          options.push(question.secondChoice);
          options.push(question.thirdChoice);
        }
      }
      console.log(this.props);
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
          {
            this.state.quizFinished
            ? (
              <div>
                {
                  this.props.quiz.result
                  ? 
                  (
                  <div className="score-header">
                    <h1>
                      Your Score: {this.props.quiz.result.score}
                    </h1>
                    <h1>
                      Your Level: {this.props.quiz.result.level}
                    </h1>
                  </div>
                  )
                  : "Score is loading..."
                }
              </div>
            )
            : (
              <div>
              {
                Object.keys(question).length === 0 && !this.state.quizFinished
                ? <h1 className="display-4">Quiz is loading..</h1>
                : (
                <div>
                <h1 className="display-4">Quiz Name</h1>
                <h2 style={{ margin: '20px' }} className="lead">{question.questionText}</h2>
                <Radio.Group onChange={this.handleOptionChange} value={this.state.selectedOption}>
                  {options.map((value, index) => {
                    return (
                      <Radio style={radioStyle} disabled={this.state.isSubmit} className={this.state.optionClassNames[index+1]} value={index+1} key={index+1}>
                        {value}
                      </Radio>
                    );
                  })}
                </Radio.Group>
                <br></br>
                <Button style={{ margin: '20px' }} type="primary" onClick={this.handleFormSubmit}>{this.state.isSubmit ? "Next Question" : "Submit" }</Button>
                {this.state.isSubmit && this.state.selectedOption==question.correct &&
                  <Alert message="Your answer is correct!" type="success" />
                }
                {this.state.isSubmit && this.state.selectedOption!=question.correctChoiceId &&
                  <Alert message={"Your answer is wrong! Correct answer is " + options[question.correctChoiceId-1]+"!"} type="error" />
                }
                </div>
                )
              }
              </div>
            )
          }
          
        </Content>
        <Footer style={{ textAlign: 'center' }}>
          YALLP ©2019 Created by three awesome front-end developers.
        </Footer>
      </Layout>
      );
    }
}

function mapState(state) {
  const { quiz } = state;
  return { quiz };
}

const actionCreators = {
  getQuiz: quizActions.getQuiz,
  submitQuiz: quizActions.submitQuiz,
};

const connectedQuizPage = connect(mapState, actionCreators)(QuizPage);
export { connectedQuizPage as QuizPage };