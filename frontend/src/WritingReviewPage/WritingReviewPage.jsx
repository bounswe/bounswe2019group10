import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import {
  Layout, Menu, Breadcrumb, Row, Col, Card, Radio,
  Avatar, Descriptions, List, Input, Button, Typography, Modal, Select
} from 'antd';
import 'antd//dist/antd.css';
import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';

import { history } from '../_helpers';
import { userActions, writingActions } from '../_actions';
const { Header, Content, Footer } = Layout;
const { SubMenu } = Menu;
const { TextArea } = Input;
const { Title } = Typography;
const {Option} = Select;

class WritingReviewPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modalVisible: false,
      score: 0,
      assignmentId: 0,
      selectedAssignment: "",
      selectedAnswer: "",
      selectedUser: "",
      writingResultId: 0,
      oktext: "Score this assignment" 
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleClick = this.handleClick.bind(this);
    this.selectScore= this.selectScore.bind(this);
    this.setModalVisible = this.setModalVisible.bind(this);
  }
  handleClick(action) {
    const IdnScore = {
      writingResultId: this.state.writingResultId,
      score: this.state.score
    }
    {!this.state.scored &&
    this.props.scoreWriting(IdnScore);
    }
    this.setState({modalVisible: false});
  }
  selectScore(score){
    this.setState({score});
  }
  setModalVisible(modalVisible, selectedAssignment,selectedAnswer,selectedUser,writingResultId,scored) {
    this.setState({ modalVisible });
    this.setState({ selectedAssignment });
    this.setState({ selectedAnswer });
    this.setState({ selectedUser });
    this.setState({ writingResultId });
    this.setState({ scored });
    {
      if(!scored)
    {
      this.setState({oktext: "Score this assignment"})
    }
    else{
      this.setState({oktext: "Return to assignments"})
    }};
  }
  handleChange(e) {
    const { name, value } = e.target;
    this.setState({ [name]: value });
  }
  componentDidMount() {
    this.props.getnonCompletedAssignments();
    this.props.getCompletedAssignments();
    document.onmouseup = () => {
      console.log("selected text: " + window.getSelection().toString());
      const baseOffset = window.getSelection().baseOffset;
      const focusOffset = window.getSelection().focusOffset;
      console.log(window.getSelection());
      if (window.getSelection().baseNode){
        console.log(window.getSelection().baseNode.data.substring(baseOffset,focusOffset));
        console.log(window.getSelection().baseNode.data.substring(0,baseOffset));

      }
    };
  }
  onChange = e => {
    this.setState({
      reviewer: e.target.value,
    });
  };

  render() {
    const { assignments } = this.props;
    const { cassignments } = this.props;
    // HERE
    const annotations = [{start:1,end:5, text:"hahaa"},{start:10,end:15, text:"hahaa"}];
    let selectedAnswer = this.state.selectedAnswer;
    if (selectedAnswer){
      annotations.forEach(annotation => {
        const targetText = this.state.selectedAnswer.substring(annotation.start,annotation.end);
        const selectedText = "<span>"+targetText+"</span>";
        selectedAnswer = selectedAnswer.replace(new RegExp(targetText), selectedText)
      });  
    }
    console.log("original");
    console.log(this.state.selectedAnswer);
    console.log("updated");
    console.log(selectedAnswer);

    return (
      <Layout className="layout">
        <HeaderComponent />
        <Content style={{ padding: '0 50px' }}>
          <Col span={4} />
          <Col span={16}>
            <div style={{ margin: '10px 0' }} />
            <Card title="Requested Writing Reviews">
              <p
                style={{
                  fontSize: 14,
                  color: 'rgba(0, 0, 0, 0.85)',
                  marginBottom: 16,
                  fontWeight: 500,
                }}
              >
                Not Yet Reviewed
            </p>
              { !assignments && 
                <Card type="inner" title="No New Assignments" >
                  
                </Card>
              }
              {assignments && assignments.map((value, index) => {
                let t = value.writingName + " by " + value.memberName;
                let t2 = value.answerText.split('.') + " ... ";
                return (
                  <Card type="inner" title={t} key={index + 1}
                    extra={<Button type="primary" onClick={() => this.setModalVisible(true,value.writingName, value.answerText,value.memberName,value.id,value.scored)} >Score</Button>}>
                    {t2}
                  </Card>
                );
              })}
            <p
                style={{
                  fontSize: 14,
                  color: 'rgba(0, 0, 0, 0.85)',
                  marginBottom: 16,
                  fontWeight: 500,
                }}
              >
                Already Reviewed
            </p>
            {cassignments && cassignments.map((value, index) => {
                let t = value.writingName + " by " + value.memberName;
                let t2 = value.answerText.split('.') + " ... ";
                return (
                  <Card type="inner" title={t} key={index + 1}
                  extra={<Button type="primary" onClick={() => this.setModalVisible(true,value.writingName, value.answerText,value.memberName,value.id,value.scored)} >Review</Button>}>
                    {t2} 
                  </Card>
                );
              })}
            </Card>
            <div style={{ margin: '10px 0' }} />
          </Col>
          <Col span={4} />
          <Modal
            title=""
            centered
            width= "1000px"
            visible={this.state.modalVisible}
            onOk={() => this.handleClick()}
            onCancel={() => this.setModalVisible(false)}
            okText= {this.state.oktext}
          >
            <Title style={{ paddingTop: "25px", paddingBottom: "25px" }} level={2}>Question: {this.state.selectedAssignment}</Title>
            <div style={{ margin: '10px 0' }} />
            <h1> Answer: {this.state.selectedAnswer}</h1>
            <div style={{ margin: '10px 0' }} />
            <h2> by user: {this.state.selectedUser}</h2>
            {
            !this.state.scored &&
            <Select defaultValue="score:" style={{ width: 120 }} onChange={this.selectScore}>
              <Option value="0">0</Option>
              <Option value="1">1</Option>
              <Option value="2">2</Option>
              <Option value="3">3</Option>
              <Option value="4">4</Option>
              <Option value="5">5</Option>
              <Option value="6">6</Option>
              <Option value="7">7</Option>
              <Option value="8">8</Option>
              <Option value="9">9</Option>
              <Option value="10">10</Option>
            </Select>
            }
          </Modal>
        </Content>
        <FooterComponent />
      </Layout>
    );
  }
}

function mapState(state) {
  const { writing } = state;
  const { assignments } = writing;
  const { cassignments } = writing;
  return { assignments,cassignments };
}

const actionCreators = {
  getnonCompletedAssignments: writingActions.getnonCompletedAssignments,
  scoreWriting: writingActions.scoreWriting,
  getCompletedAssignments: writingActions.getCompletedAssignments
}

const connectedWritingReviewPage = connect(mapState, actionCreators)(WritingReviewPage);
export { connectedWritingReviewPage as WritingReviewPage };