import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';

import { userActions } from '../_actions';

class QuizPage extends React.Component {
    constructor(props) {
      super(props);
      this.state = {
        selectedOption: '',
        quiz: [],
        current: 0,
        showScore: false,
        score: 0
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
          "options": ["o11","o12","o13"]
          },
          {
          "question": "q2",
          "options": ["o21","o22","o23"]
          }
        ],
        current: 0
      });
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
      formSubmitEvent.preventDefault();
    
      console.log('You have selected:', this.state.selectedOption);
      if (this.state.current==this.state.quiz.length-1){
        // send request to eval
        this.setState({
          showScore: true,
          score: 10
        });
      }else {
        this.setState({
          current: this.state.current+1,
          selectedOption: ""
        });
      }
    }

    render() {
      if (this.state.quiz.length == 0){
        return (
          <div className="jumbotron">
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
      return (
          <div className="jumbotron">
            <h1 className="display-4">Quiz Name</h1>
            <p className="lead">{this.state.quiz[this.state.current].question}</p>
            <form onSubmit={this.handleFormSubmit}>
            <div className="radio">
              <label>
                <input type="radio" value="option1" checked={this.state.selectedOption === 'option1'} onChange={this.handleOptionChange} />
                {this.state.quiz[this.state.current].options[0]}
              </label>
            </div>
            <div className="radio">
              <label>
                <input type="radio" value="option2" checked={this.state.selectedOption === 'option2'} onChange={this.handleOptionChange} />
                {this.state.quiz[this.state.current].options[1]}
              </label>
            </div>
            <div className="radio">
              <label>
                <input type="radio" value="option3" checked={this.state.selectedOption === 'option3'} onChange={this.handleOptionChange} />
                {this.state.quiz[this.state.current].options[2]}
              </label>
            </div>
            <button className="btn btn-primary" type="submit">Next</button>
          </form>
          </div>
      );
    }
}

function mapState(state) {
    const { loggingIn } = state.authentication;
    return { loggingIn };
}

const actionCreators = {
    login: userActions.login,
    logout: userActions.logout
};

const connectedQuizPage = connect(mapState, actionCreators)(QuizPage);
export { connectedQuizPage as QuizPage };