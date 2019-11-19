
import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { Layout, Menu, Breadcrumb, Row, Col,
      Avatar, Descriptions, List, Input, Button } from 'antd';
import 'antd//dist/antd.css';
import './WritingPage.css';

import { history } from '../_helpers';
import { userActions } from '../_actions';
const { Header, Content, Footer } = Layout;
const { SubMenu } = Menu;

class WritingPage extends React.Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);
  
  this.state = {
    writingId: writingId,
    answer: "",
    isSubmit: false,
    writingFinished: false,
    setModalVisible= false
  };
  this.handleChange = this.handleChange.bind(this);
  this.handleClick = this.handleClick.bind(this);
  this.setModalVisible = this.setModalVisible.bind(this);
  }
  handleClick(action){
    const t={
      writingId:this.state.writingId,
      answer:this.state.answer
    }
    this.props.submitWriting(t);
  }
  setModalVisible(modalVisible) {
    this.setState({ modalVisible });
  }
  handleChange(e) {
    const { name, value } = e.target;
    this.setState({ [name]: value });
  }
  componentDidMount() {
    this.props.getWriting();
  }
  logOut(){
    this.props.logOut();
    history.push('/');
  }
  render() {
    const { writing }=this.props;

  return(
    <Layout className="layout">
          <HeaderComponent />
          <Content style={{ padding: '0 50px' }}>
            
            <Col span={4} />
            <Col span={16}>
            <Title style={{paddingTop:"25px",paddingBottom:"25px"}} level={3}>{writing.question}</Title>
            <div style={{ margin: '20px 0' }} />
            <TextArea placeholder="Write your answer here"
            autoSize={{minRows: 2, maxRows: 10}}
            value={answer}
            onChange= {this.handleChange}
            />            
            </Col>
            <Col span={4} />
      
          </Content>
          <FooterComponent />
          </Layout>
        );
  }
}

function mapState(state) {
  const { writing } = state;  
  return { writing };
}

const actionCreators = {
  getWriting: userActions.getWriting,
  submitWriting: userActions.submitWriting,
  logOut: userActions.logout,
}

const connectedWritingPage = connect(mapState, actionCreators)(WritingPage);
export { connectedWritingPage as WritingPage };