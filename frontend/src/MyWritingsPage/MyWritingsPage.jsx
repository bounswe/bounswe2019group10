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

import Annotation from 'react-image-annotation/lib';

import { history } from '../_helpers';
import { userActions, writingActions } from '../_actions';
const { Header, Content, Footer } = Layout;
const { SubMenu } = Menu;
const { TextArea } = Input;
const { Title } = Typography;
const {Option} = Select;

class MyWritingsPage extends React.Component {
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
      imageUrl: File,
      oktext: "Done",
      hovered: true,
      annotatedAnswer: [],
      newAnnotatedAnswer: [],
      annotationText: "",
      annotationStart: 0,
      annotationEnd: 0,
      annotationImage: {},
      annotationsImage: [],
    };
    this.handleChange = this.handleChange.bind(this);
    this.selectScore= this.selectScore.bind(this);
    this.setModalVisible = this.setModalVisible.bind(this);
    this.convertDate = this.convertDate.bind(this);
    this.createAnnotation = this.createAnnotation.bind(this);
    this.deleteAnnotation = this.deleteAnnotation.bind(this);
    this.onAnnotationChange = this.onAnnotationChange.bind(this);
    this.onAnnotationSubmit = this.onAnnotationSubmit.bind(this);
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
    this.setState({ scored });
    this.setState({ score });
    this.setState({ imageUrl });
    this.setState({oktext: "Return to your Writings"});
    if (writingResultId){
      this.props.getWritingAnnotations(writingResultId);
    }else{
      this.props.clearAnnotations();
      this.setState({
        annotatedAnswer: [],
      });
    }
    if (imageUrl){
      this.props.getWritingImageAnnotations(imageUrl);
    }else{
      this.props.clearImageAnnotations();
      this.setState({
        annotationsImage: [],
      });
    }
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
    this.props.getMyWritings();
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
    // image annotation
    if (this.props.newImageAnnotation && Object.entries(this.props.newImageAnnotation).length !== 0){
      this.props.clearNewImageAnnotations();
      this.props.clearImageAnnotations();
      this.setState({
        annotationImage: {},
        annotationsImage: [],
      });
      this.props.getWritingImageAnnotations(this.state.imageUrl);
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

  onAnnotationChange = (annotationImage) => {
    this.setState({ annotationImage });
  }
 
  onAnnotationSubmit = (annotation) => {
    const { geometry, data } = annotation;
    console.log(annotation);
    const imageUrl = this.state.imageUrl;
    const annotationParam = {
      imageUrl: this.state.imageUrl,
      annotationText: data.text,
      x: geometry.x,
      y: geometry.y,
      h: geometry.height,
      w: geometry.width,
    };
    
    this.props.createImageAnnotation(annotationParam);
  }

  render() {
    const { writings } = this.props;

    const selectedAnswer = this.state.selectedAnswer;
    const annotatedAnswer = this.state.annotatedAnswer;
    const newAnnotatedAnswer = this.state.newAnnotatedAnswer;

    const imageAnnotationList = []
    if (this.props.imageAnnotations){
      for (const annotation of this.props.imageAnnotations) {
        const target = annotation.target.selector.value;
        const splitted = target.split("=");
        const coordinates = splitted[splitted.length-1].split(",");
        const newAnnotation = {
          data: {
            text: annotation.bodyValue,
            id: Math.random()
          },
          geometry: {
            height: parseInt(coordinates[3]),
            width: parseInt(coordinates[2]),
            x:parseInt(coordinates[0]),
            y:parseInt(coordinates[1]),
            type: "RECTANGLE"
          }
        }
        imageAnnotationList.push(newAnnotation);
      }
    }

    return (
      <Layout className="layout">
        <HeaderComponent />
        <Content style={{ padding: '0 50px' }}>
          <Col span={4} />
          <Col span={16}>

            <div style={{ margin: '10px 0' }} />
            <Card title="My Writings">
              <p
                style={{
                  fontSize: 14,
                  color: 'rgba(0, 0, 0, 0.85)',
                  marginBottom: 16,
                  fontWeight: 500,
                }}
              >
            </p>
              { writings && writings.length==0 &&
                <Card type="inner" title="No Writings yet" >
                  
                </Card>
              }
              {writings && writings.map((value, index) => {
                let sc;
                if(value.scored){
                sc=" ,Your Score: "+ value.score;
                }else{
                sc= " ,Score is Pending";
                }
                let t = value.writingName + sc;
                let t2 = value.answerText ? value.answerText.split('.') + " ... " : "";
                return (
                  <Card type="inner" title={t} key={index + 1}
                    extra={<Button type="primary" onClick={() => this.setModalVisible(true,value.writingName, value.answerText,value.assignedMemberName,value.id,value.scored,value.score,value.imageUrl)} >View</Button>}>
                    { value.imageUrl ?
                     (
                      <div>
                        <img style={{width: "100%"}} src={value.imageUrl}/>
                      </div>
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
            onOk={() => this.setModalVisible(false)}
            onCancel={() => this.setModalVisible(false)}
            okText= {this.state.oktext}
          >
            <Title style={{ paddingTop: "25px", paddingBottom: "25px" }} level={2}>Question: {this.state.selectedAssignment}</Title>
            <div style={{ margin: '10px 0' }} />
            <h1> Answer: <span style={{fontSize: "14px"}}>select answer to annotate</span></h1>
            <h1>
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
              { this.state.imageUrl &&  
                <Annotation 
                  src={this.state.imageUrl}
                  alt='Writing exercise'
                  annotations={imageAnnotationList}
                  value={this.state.annotationImage}
                  onChange={this.onAnnotationChange}
                  onSubmit={this.onAnnotationSubmit}
                  />
               }
            </h1>
            <div style={{ margin: '10px 0' }} />
            <h2> Sent to User: {this.state.selectedUser}</h2>
            {
            !this.state.scored &&
            <h2> Not Scored Yet!  </h2>
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
  const { writings } = writing;
  const { annotations } = writing;
  const { newAnnotation } = writing;
  const { deletedAnnotation } = writing;
  const { newImageAnnotation } = writing;
  const { imageAnnotations } = writing;
  const { profile } = users;
  return { writings,annotations,newAnnotation,deletedAnnotation,profile,newImageAnnotation,imageAnnotations };
}

const actionCreators = {
  getMyWritings: writingActions.getMyWritings,
  getWritingAnnotations: writingActions.getWritingAnnotations,
  clearAnnotations: writingActions.clearAnnotations,
  createAnnotation: writingActions.createAnnotation,
  clearNewAnnotations: writingActions.clearNewAnnotations,
  deleteAnnotation: writingActions.deleteAnnotation,
  clearDeleteAnnotation: writingActions.clearDeleteAnnotation,
  createImageAnnotation: writingActions.createImageAnnotation,
  clearNewImageAnnotations: writingActions.clearNewImageAnnotations,
  getWritingImageAnnotations: writingActions.getWritingImageAnnotations,
  clearImageAnnotations: writingActions.clearImageAnnotations,
}

const connectedMyWritingsPage = connect(mapState, actionCreators)(MyWritingsPage);
export { connectedMyWritingsPage as MyWritingsPage };