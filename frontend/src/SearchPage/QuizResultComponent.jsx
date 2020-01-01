import React from 'react';
import { connect } from 'react-redux';
import { Card,Icon,Typography } from 'antd';

import { history } from '../_helpers';
const { Title } = Typography;

class QuizResultComponent extends React.Component {

    constructor(props) {
      super(props);
      this.onClick = this.onClick.bind(this);
    }

    onClick(e){
      const { quizId } = this.props;
      history.push({
        pathname: '/quiz',
        state: { quizId: quizId }
      });
    }

    componentDidMount() {
    }

    render() {
      const { quizType,levelName,solved,score } = this.props;
      
      return (
        <Card style={{textAlign: 'left'}} hoverable={true} onClick={this.onClick}>
          <div style={{display: "flex", justifyContent: "flex-end"}}>
            { solved ?(
                <div style={{display: "flex", justifyContent: "flex-start"}}>
                  {score} &nbsp;<Icon type="check-circle" style={{ fontSize: '24px', color: '#08c' }} />
                </div>
              ) : (
                <p> 
                  Click to start exercise
                </p>
              )
            }
          </div>
          <Title style={{paddingTop:"25px",paddingBottom:"25px"}} level={4}>{ quizType.toUpperCase() } Quiz - {levelName} </Title>
        </Card>
        );
    }
}

function mapState(state) {
  
  return {  };
}

const actionCreators = {
}

const connectedQuizResultComponent = connect(mapState, actionCreators)(QuizResultComponent);
export { connectedQuizResultComponent as QuizResultComponent };