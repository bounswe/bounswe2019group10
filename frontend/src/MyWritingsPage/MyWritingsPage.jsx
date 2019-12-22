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
      oktext: "Done" 
    };
    this.handleChange = this.handleChange.bind(this);
    this.selectScore= this.selectScore.bind(this);
    this.setModalVisible = this.setModalVisible.bind(this);
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
    this.setState({oktext: "Return to your Writings"}) 
  }
  handleChange(e) {
    const { name, value } = e.target;
    this.setState({ [name]: value });
  }
  componentDidMount() {
    this.props.getMyWritings();
  }
  onChange = e => {
    this.setState({
      reviewer: e.target.value,
    });
  };

  render() {
    const { writings } = this.props;
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
                let t2 = value.answerText && value.answerText.split('.') + " ... ";
                return (
                  <Card type="inner" title={t} key={index + 1}
                    extra={<Button type="primary" onClick={() => this.setModalVisible(true,value.writingName, value.answerText,value.assignedMemberName,value.id,value.scored,value.score,value.imageUrl)} >View</Button>}>
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
            onOk={() => this.setModalVisible(false)}
            onCancel={() => this.setModalVisible(false)}
            okText= {this.state.oktext}
          >
            <Title style={{ paddingTop: "25px", paddingBottom: "25px" }} level={2}>Question: {this.state.selectedAssignment}</Title>
            <div style={{ margin: '10px 0' }} />
            <h1> Answer: {this.state.selectedAnswer}</h1>
            <img src={this.state.imageUrl}/>
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
  const { writing } = state;
  const { writings } = writing;
  return { writings };
}

const actionCreators = {
  getMyWritings: writingActions.getMyWritings
}

const connectedMyWritingsPage = connect(mapState, actionCreators)(MyWritingsPage);
export { connectedMyWritingsPage as MyWritingsPage };