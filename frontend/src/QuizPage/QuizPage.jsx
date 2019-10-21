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
        optionClassNames: {}
      };
      this.handleOptionChange = this.handleOptionChange.bind(this);
      this.handleFormSubmit = this.handleFormSubmit.bind(this);
      this.getInitialState = this.getInitialState.bind(this);
    }
    componentDidMount() {
      this.setState({
        quiz: [
          {
          "question": "q1",
          "options": ["o11","o12","o13"],
          "correct": 1
          },
          {
          "question": "q2",
          "options": ["o21","o22","o23"],
          "correct": 2
          }
        ],
        current: 0
      });
      // this.props.getQuiz(1);
    }
    getInitialState() {
      this.setState({
        selectedOption: ""
      });
    }

    handleOptionChange(changeEvent) {
      console.log(this.state);
      this.setState({
        selectedOption: changeEvent.target.value
      });
    }

    handleFormSubmit(formSubmitEvent) {
      console.log('You have selected:', this.state.selectedOption);
      if (this.state.isSubmit){
        if (this.state.current==this.state.quiz.length-1){
          // send request to eval
          this.setState({
            showScore: true,
            score: 10
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
        const question = this.state.quiz[this.state.current];
        let optionClassNames = {}
        optionClassNames[this.state.selectedOption] = "wrongOption";
        optionClassNames[question.correct] = "correctOption";
        this.setState({
          isSubmit: true,
          answers: [...this.state.answers, {"questionId":this.state.current,"choiceId":this.state.selectedOption}],
          optionClassNames: optionClassNames
        });
      }
      
    }

    render() {
      if (this.state.quiz.length == 0){
        return (
          <div>
            <h1 className="display-4">Loading</h1>
          </div>
        );
      }
      if (this.state.showScore){
        return (
          <div className="jumbotron">
            <h1 className="display-4">Your Score: {this.state.score}</h1>
          </div>
        );
      }
      const radioStyle = {
        display: 'block',
        height: '30px',
        lineHeight: '30px',
        margin: '20px',
        "padding-left": "10px",
        "padding-right": "10px",
        "padding-top": "10px",
        "padding-bottom": "40px",
      };
      const option1ClassName = this.state.submitted;
      const question = this.state.quiz[this.state.current];
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
            this.state.quiz.length == 0 
            ? <h1 className="display-4">Quiz is loading..</h1>
            : (
            <div>
            <h1 className="display-4">Quiz Name</h1>
            <h2 style={{ margin: '20px' }} className="lead">{this.state.quiz[this.state.current].question}</h2>
            <Radio.Group onChange={this.handleOptionChange} value={this.state.selectedOption}>
              {question.options.map((value, index) => {
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
            {this.state.isSubmit && this.state.selectedOption!=question.correct &&
              <Alert message={"Your answer is wrong! Correct answer is " + question.options[question.correct]+"!"} type="error" />
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
  getQuiz: quizActions.getQuiz
};

const connectedQuizPage = connect(mapState, actionCreators)(QuizPage);
export { connectedQuizPage as QuizPage };