import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import {
  Layout, Menu, Breadcrumb, Row, Col, Card, Radio,
  Avatar, Descriptions, List, Input, Button, Typography, Modal, Select,Popover,Icon
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
      oktext: "Score this assignment",
      hovered: true,
      annotatedAnswer: [],
      newAnnotatedAnswer: [],
      annotationText: "",
      annotationStart: 0,
      annotationEnd: 0
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleClick = this.handleClick.bind(this);
    this.selectScore= this.selectScore.bind(this);
    this.setModalVisible = this.setModalVisible.bind(this);
    this.convertDate = this.convertDate.bind(this);
    this.createAnnotation = this.createAnnotation.bind(this);
    this.deleteAnnotation = this.deleteAnnotation.bind(this);
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
  setModalVisible(modalVisible, selectedAssignment,selectedAnswer,selectedUser,writingResultId,scored,score,imageUrl) {
    this.setState({ modalVisible });
    this.setState({ selectedAssignment });
    this.setState({ selectedAnswer });
    this.setState({ selectedUser });
    this.setState({ writingResultId });
    if (writingResultId){
      this.props.getWritingAnnotations(writingResultId);
    }else{
      this.props.clearAnnotations();
      this.setState({
        annotatedAnswer: [],
      });
    }
    this.setState({ scored });
    this.setState({ score });
    this.setState({ imageUrl });
    if(!scored)
    {
      this.setState({oktext: "Score this assignment"})
    }
    else{
      this.setState({oktext: "Return to assignments"})
    };
  }
  handleChange(e) {
    const { name, value } = e.target;
    this.setState({ [name]: value });
  }

  createAnnotation(e){
    const text = this.state.annotationText;
    const writingResultId = this.state.writingResultId;
    const start = this.state.annotationStart;
    const end = this.state.annotationEnd;
    this.props.createAnnotation(writingResultId, text, start,end);
  }

  componentDidMount() {
    this.props.getnonCompletedAssignments();
    this.props.getCompletedAssignments();
    document.onmouseup = () => {
      const selected = window.getSelection();
      if (selected.baseNode && selected.baseNode.parentNode.classList 
        && selected.baseNode.parentNode.classList[0]==="part" && selected.baseNode.parentNode.classList[1]){
        const start = Math.min(window.getSelection().baseOffset,window.getSelection().focusOffset);
        const end = Math.max(window.getSelection().baseOffset,window.getSelection().focusOffset);
        if (start - end != 0){
          const partId = parseInt(selected.baseNode.parentNode.classList[1]);
          let newAnnotatedAnswer = this.state.annotatedAnswer.slice();
          if (this.state.annotatedAnswer.length===0){
            newAnnotatedAnswer = [this.state.selectedAnswer];
          }
          const selectedPart = newAnnotatedAnswer[partId];
          const selectedText = <Popover placement="top" title={"Add a new annotation"} content={
            <div>
              <Input placeholder="Your annotation..." name="annotationText" onChange={this.handleChange}/>
              <Button onClick={this.createAnnotation}>Submit</Button>
            </div>
          } 
          trigger="click" visible={this.state.hovered} onVisibleChange={this.handleHoverChange} >
              <span className="1" style={{backgroundColor: "#FFFF00"}}>{ selectedPart.substring(start,end) }</span>
          </Popover>;
          newAnnotatedAnswer.splice(partId+1, 0, selectedPart.substring(end));
          newAnnotatedAnswer.splice(partId+1, 0, selectedText);
          newAnnotatedAnswer[partId] = selectedPart.substring(0,start);
          let annotationStart = start;
          for (let index = 0; index < partId; index++) {
            const element = newAnnotatedAnswer[index];
            if (element.props){
              annotationStart += element.props.children.props.text.length;
            }else{
              annotationStart += element.length;
            }
          }
          const annotationEnd = annotationStart + end - start;
          this.setState({newAnnotatedAnswer,annotationStart,annotationEnd});
        }
      }
    };
  }
  convertDate(date){
    let d = date;
    d = [
      '0' + d.getDate(),
      '0' + (d.getMonth() + 1),
      '' + d.getFullYear(),
      '0' + d.getHours(),
      '0' + d.getMinutes()
    ].map(component => component.slice(-2));
    d[2] = date.getFullYear();
    return ""+d[3]+"."+d[4]+" "+d[0]+"."+d[1]+"."+d[2];
  }
  
  deleteAnnotation(annotationId) {
    this.props.deleteAnnotation(annotationId);
  }

  componentDidUpdate(){
    if (this.state.selectedAnswer && 
      this.props.annotations && this.props.annotations.length>0 && 
      this.state.annotatedAnswer && this.state.annotatedAnswer.length===0){
      let annotatedAnswer = [this.state.selectedAnswer];
      const annotations = this.props.annotations;
      annotations.sort((a, b) => (a.target.selector.start < b.target.selector.start) ? 1 : -1);

      annotations.forEach(annotation => {
        const start = Math.min(annotation.target.selector.start,annotation.target.selector.end);
        const end = Math.max(annotation.target.selector.start,annotation.target.selector.end);
        const annotatedText = annotatedAnswer[0].substring(start,end);
        const username = annotation.creator.nickname;
        const updatedDate = this.convertDate(new Date(annotation.modified));
        const id = annotation.id.split("/").pop();
        const selectedText = <Popover placement="top" 
        title={
          <div>
            <span>{ username + " " }</span>
            <span style={{fontSize: "10px"}}>{ " "+updatedDate }</span>
            { this.props.profile.username===username && <Icon type="delete" onClick={()=>this.deleteAnnotation(id)}/>}  
          </div>
          } content={annotation.bodyValue} trigger="hover">
            <span text={ annotatedText } className="1" style={{backgroundColor: "#FFFF00"}}>{ annotatedText }</span>
        </Popover>;
        
        annotatedAnswer.splice(1, 0, annotatedAnswer[0].substring(end));
        annotatedAnswer.splice(1, 0, selectedText);
        annotatedAnswer[0] = annotatedAnswer[0].substring(0,start);
      });
      this.setState({ annotatedAnswer });
    }
    if ((this.props.newAnnotation && Object.entries(this.props.newAnnotation).length !== 0)
      || (this.props.deletedAnnotation && this.props.deletedAnnotation!=="")){
      this.props.clearNewAnnotations();
      this.props.clearDeleteAnnotation();
      this.props.clearAnnotations();
      this.setState({
        annotatedAnswer: [],
        newAnnotatedAnswer: [],
      });
      this.props.getWritingAnnotations(this.state.writingResultId);
    }
  }

  onChange = e => {
    this.setState({
      reviewer: e.target.value,
    });
  };

  handleHoverChange = visible => {
    if (visible){
      this.setState({
        hovered: visible,
      });
    } else {
      this.setState({
        hovered: visible,
        newAnnotatedAnswer: [],
        hovered: true,
        annotatedText: ""
      });
    }
  };

  render() {
    const { assignments } = this.props;
    const { cassignments } = this.props;

    const selectedAnswer = this.state.selectedAnswer;
    const annotatedAnswer = this.state.annotatedAnswer;
    const newAnnotatedAnswer = this.state.newAnnotatedAnswer;
    
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
              { assignments && assignments.length==0 &&
                <Card type="inner" title="No New Assignments" >
                </Card>
              }
              {assignments && assignments.map((value, index) => {
                let t = value.writingName + " by " + value.memberName;
                let t2 = value.answerText && value.answerText.split('.') + " ... ";
                return (
                  <Card type="inner" title={t} key={index + 1}
                    extra={<Button type="primary" onClick={() => this.setModalVisible(true,value.writingName, value.answerText,value.memberName,value.id,value.scored,value.score,value.imageUrl)} >Score</Button>}>
                    { value.image ?
                     (
                      <img style={{width: "100%"}} src={value.imageUrl}/>
                     ) : 
                      t2 }
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
                let t2 = value.answerText && value.answerText.split('.') + " ... ";
                return (
                  <Card type="inner" title={t} key={index + 1}
                  extra={<Button type="primary" onClick={() => this.setModalVisible(true,value.writingName, value.answerText,value.memberName,value.id,value.scored,value.score,value.imageUrl)} >Review</Button>}>
                  { value.image ?
                     (
                      <img style={{width: "100%"}} src={value.imageUrl}/>
                     ) : 
                      t2 }
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
            <div style={{ margin: '10px 0' } } />
            <div style={{ margin: '10px 0' }} />
            <h2> Answer: 
              <div className="answerText">
                { newAnnotatedAnswer && newAnnotatedAnswer.map((part, i) => {     
                  return (<span key={i} className={"part "+i}>{part}</span>) 
                })}
                { newAnnotatedAnswer.length===0 && annotatedAnswer && annotatedAnswer.map((part, i) => {
                  return (<span key={i} className={"part "+i}>{part}</span>) 
                })}
                { ( newAnnotatedAnswer.length===0 && annotatedAnswer.length===0 && selectedAnswer) && 
                  <span className={"part 0"}>{selectedAnswer}</span> 
                }
              </div>
              {this.state.imageUrl && <img style={{width: "100%"}} src={this.state.imageUrl}/>}
            </h2>
            <div style={{ margin: '10px 0' }} />
            <h2 style={{fontSize: 20 }}> by user: {this.state.selectedUser}</h2>
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
            {
            this.state.scored &&
            <h2> Your Score is: {this.state.score} </h2>
            }
          </Modal>
        </Content>
        <FooterComponent />
      </Layout>
    );
  }
}

function mapState(state) {
  const { writing,users } = state;
  const { assignments } = writing;
  const { cassignments } = writing;
  const { annotations } = writing;
  const { newAnnotation } = writing;
  const { deletedAnnotation } = writing;
  const { profile } = users;
  return { assignments,cassignments,annotations,newAnnotation,profile,deletedAnnotation };
}

const actionCreators = {
  getnonCompletedAssignments: writingActions.getnonCompletedAssignments,
  scoreWriting: writingActions.scoreWriting,
  getCompletedAssignments: writingActions.getCompletedAssignments,
  getWritingAnnotations: writingActions.getWritingAnnotations,
  clearAnnotations: writingActions.clearAnnotations,
  createAnnotation: writingActions.createAnnotation,
  clearNewAnnotations: writingActions.clearNewAnnotations,
  deleteAnnotation: writingActions.deleteAnnotation,
  clearDeleteAnnotation: writingActions.clearDeleteAnnotation,
}

const connectedWritingReviewPage = connect(mapState, actionCreators)(WritingReviewPage);
export { connectedWritingReviewPage as WritingReviewPage };