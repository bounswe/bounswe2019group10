import React from 'react';
import { connect } from 'react-redux';
import { Avatar, Card,Icon,Modal,Typography } from 'antd';

import { userActions } from '../_actions';
import { history } from '../_helpers';
const { Title } = Typography;

const { confirm } = Modal;

class WritingResultComponent extends React.Component {

    constructor(props) {
      super(props);
      this.onClick = this.onClick.bind(this);
    }

    onClick(e){
      const { writingId,writingName,taskText,solved } = this.props;
      console.log(writingId,writingName,taskText,solved);
      if(solved){
      history.push({
        pathname: '/mywritings-page',
      }
      )}else{
      history.push({
        pathname: '/writing-page',
        state: { writingId: writingId }
        }
        )    
      }
    }

    componentDidMount() {
    }

    render() {
      const { writingId,writingName,taskText,solved } = this.props;
      return (
        <Card style={{textAlign: 'left'}} hoverable={true} onClick={this.onClick}>
            <div style={{display: "flex", justifyContent: "flex-end"}}>
              { solved ?(
                  <div style={{display: "flex", justifyContent: "flex-start"}}>
                    <p> 
                      Click to review your answer
                    </p>
                    <Icon type="check-circle" style={{ fontSize: '24px', color: '#08c' }} />
                  </div>
                ) : (
                  <p> 
                    Click to start exercise
                  </p>
                )
              }
            </div>
          <Title style={{paddingTop:"25px",paddingBottom:"25px"}} level={4}>{ writingName }</Title>
          <p>{ taskText }</p>
        </Card>      
        );
    }
}

function mapState(state) {
  
  return {  };
}

const actionCreators = {
}

const connectedWritingResultComponent = connect(mapState, actionCreators)(WritingResultComponent);
export { connectedWritingResultComponent as WritingResultComponent };