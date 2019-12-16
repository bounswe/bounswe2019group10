import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import {
  Layout, Menu, Breadcrumb, Row, Col, Radio,
  Avatar, Descriptions, List, Input, Button, Typography, Modal,Select
} from 'antd';
import 'antd//dist/antd.css';
import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';

import { history } from '../_helpers';
import { userActions, writingActions } from '../_actions';
const { Header, Content, Footer } = Layout;
const { SubMenu } = Menu;
const { TextArea } = Input;
const { Title } = Typography
const {Option} = Select;

class WritingTopicPage extends React.Component {
  constructor(props) {
    super(props);
    
    this.state = {
      questionTitle: "",
      questionBody: "",
      language: 1
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleClick = this.handleClick.bind(this);
    this.selectLanguage = this.selectLanguage.bind(this);
  }
  handleClick(action) {
    const t = {
      questionTitle: this.state.questionTitle,
      questionBody: this.state.questionBody,
      language: this.state.language
    }
    this.props.submitWritingTopic(t);
    this.setState({
      writingFinished: true
    });
  }
  handleChange(e) {
    const { name, value } = e.target;
    this.setState({ [name]: value });
  }
  componentDidMount() {
    this.props.getAllLanguages();
  }
  onChange = e => {
    this.setState({
      reviewer: e.target.value,
    });
  };

  selectLanguage(language){
    this.setState({language});
  }

  render() {
    const { all_languages } = this.props;
    let options = [];
    
    return (
      <Layout className="layout">
        <HeaderComponent />
        <Content style={{ padding: '0 50px' }}>
          {
            this.state.writingFinished
              ? (
                <div>
                  <Col span={4} />
                  <Col span={16}>
                  <h1>
                    Sended your writing for review.
                    </h1>
                  <Link to={{ pathname: '/' }}>Return to home page</Link>
                  </Col>
                  <Col span={4} />
                </div>
              )
              : (
                <div>
                  <Col span={4} />
                  <Col span={16}>
                    <Title style={{ paddingTop: "25px", paddingBottom: "25px" }} level={3}>Suggest a New Question for Writing Assignments</Title>
                    
                    <div style={{ margin: '10px 0' }} />
                    <Title style={{ paddingTop: "10px", paddingBottom: "5px" }} level={4}>Title:</Title>
                    <TextArea placeholder="Write a short title description for your question"
                      autoSize={{ minRows: 1, maxRows: 2 }}
                      name="questionTitle"
                      value={this.state.questionTitle}
                      onChange={this.handleChange}
                    />
                    <div style={{ margin: '10px 0' }} />
                    <Title style={{ paddingTop: "10px", paddingBottom: "5px" }} level={4}>Question:</Title>
                    <TextArea placeholder="Write your question in full here"
                      autoSize={{ minRows: 5, maxRows: 10 }}
                      name="questionBody"
                      value={this.state.questionBody}
                      onChange={this.handleChange}
                    />
                    <div style={{ margin: '10px 0' }} />
                    <Select defaultValue="Select Language" style={{ width: 160 }} onChange={this.selectLanguage}>
                      {all_languages && all_languages.map((value, index) => {
                        return (
                          <Option value={value.id} key={index + 1}>
                            {value.languageName}
                          </Option>
                        );
                      })}
                    </Select>
                    <div style={{ margin: '10px 0' }} />
                    <Button type="primary" onClick={this.handleClick}>Send Question for Approval</Button>
                  </Col>
                  <Col span={4} />
                  
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
  const {all_languages} = state.users;
  return { all_languages };
}

const actionCreators = {
  submitWritingTopic: writingActions.submitWritingTopic,
  getAllLanguages: userActions.getAllLanguages
}

const connectedWritingTopicPage = connect(mapState, actionCreators)(WritingTopicPage);
export { connectedWritingTopicPage as WritingTopicPage };