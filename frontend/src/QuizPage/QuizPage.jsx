import React from 'react';
import { connect } from 'react-redux';
import { Layout, Radio, Button,Alert } from 'antd';
import { Link } from 'react-router-dom';
import { quizActions } from '../_actions';
import './QuizPage.css';
import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';
const { Content } = Layout;

class QuizPage extends React.Component {
    constructor(props) {
      super(props);
      let quizId = 10;
      if (this.props.location.state){
        quizId = this.props.location.state.quizId;
      }
      this.state = {
        selectedOption: '',
        quiz: [],
        quizId: quizId,
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
      this.props.getQuiz(this.state.quizId);
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
        if (questions.length-1 === this.state.current){
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
      let { quiz } = this.props;
      let question = {};
      let options = [];
      if ("quiz" in quiz && !this.state.quizFinished){
        if ("questions" in quiz.quiz && quiz.quiz.questions.length > this.state.current){
          question = quiz.quiz.questions[this.state.current];
          options.push(question.firstChoice);
          options.push(question.secondChoice);
          options.push(question.thirdChoice);
        }
      }
      return (
        <Layout className="layout menu-style">
        <HeaderComponent />        
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
                    <Link to={{pathname: '/'}}>Return to home page</Link>
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
                <h1 className="display-4">Quiz {quiz.quiz.levelName} - Type: {quiz.quiz.quizType.toUpperCase()}</h1>
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
                {this.state.isSubmit && this.state.selectedOption===question.correct &&
                  <Alert message="Your answer is correct!" type="success" />
                }
                {this.state.isSubmit && this.state.selectedOption!==question.correctChoiceId &&
                  <Alert message={"Your answer is wrong! Correct answer is " + options[question.correctChoiceId-1]+"!"} type="error" />
                }
                </div>
                )
              }
              </div>
            )
          }
          
        </Content>
        <FooterComponent />
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