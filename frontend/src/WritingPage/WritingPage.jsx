import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import {
  Layout, Menu, Breadcrumb, Row, Col, Radio,
  Avatar, Descriptions, List, Input, Button, Typography, Modal
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

class WritingPage extends React.Component {
  constructor(props) {
    super(props);
    let writingId = 2;
    if (this.props.location.state) {
      writingId = this.props.location.state.writingId;
    }
    this.state = {
      writingId: writingId,
      answer: "",
      reviewer: "",
      answerType : "text",
      file: null,
      imageUrl: "",
      isSubmit: false,
      writingFinished: false,
      modalVisible: false
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleClick = this.handleClick.bind(this);
    this.setModalVisible = this.setModalVisible.bind(this);
    this.setPic = this.setPic.bind(this);
  }
  handleClick(action) {
    const t = {
      writingId: this.state.writingId,
      answer: this.state.answer,
      imageUrl: this.state.imageUrl,
      evaluatorUsername: this.state.reviewer,
      answerType: this.state.answerType
    }
    this.props.submitWriting(t);
    this.setState({
      writingFinished: true
    });
  }
  setModalVisible(modalVisible) {
    this.setState({ modalVisible });
  }
  handleChange(e) {
    const { name, value } = e.target;
    this.setState({ [name]: value });
  }
  componentDidMount() {
    this.props.getWriting(this.state.writingId);
  }
  setPic(e){
    this.setState({file: e.target.files[0]});
    const data = new FormData()
    data.append('file', e.target.files[0])
    this.props.uploadWritingImage(data);
  }
  onChange = e => {
    this.setState({
      reviewer: e.target.value,
    });
  };
  typeChange = e => {
    this.setState({
      answerType: e.target.value,
    });
  };

  render() {
    const { writing } = this.props;
    const radioStyle = {
      display: 'block',
      height: '30px',
      lineHeight: '30px',
    };
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
                    {writing.writing &&
                      <Title style={{ paddingTop: "25px", paddingBottom: "25px" }} level={3}>{writing.writing.writingDTO.taskText}</Title>
                    }
                    <div style={{ margin: '10px 0' }} />
                    <div>
                    <Title level={4}>Select a format for writing</Title>
                    <Radio.Group onChange={this.typeChange} value={this.state.answerType}>
                    <Radio value="text">Text</Radio>
                    <Radio value="picture">Picture</Radio>
                     </Radio.Group>
                    </div>
                    <div style={{ margin: '10px 0' }} />
                    {this.state.answerType==="text" &&
                    <TextArea placeholder="Write your answer here"
                    autoSize={{ minRows: 10, maxRows: 15 }}
                    name="answer"
                    value={this.state.answer}
                    onChange={this.handleChange}
                    />
                    }
                    {this.state.answerType==="picture" &&
                    <input type="file"
                    onChange= {this.setPic}
                    id="writingpic" name="writingpic"
                    accept="image/png, image/jpeg"/>
                    }
                    <img src={this.state.file}/>
                    
                    <div style={{ margin: '10px 0' }} />
                    <Button type="primary" onClick={() => this.setModalVisible(true)} >Submit</Button>
                  </Col>
                  <Col span={4} />
                  <Modal
                    title=""
                    centered
                    visible={this.state.modalVisible}
                    onOk={() => this.handleClick()}
                    onCancel={() => this.setModalVisible(false)}
                    okText="Send to Review"
                  >
                    <Radio.Group onChange={this.onChange} value={this.state.reviewer}>
                      {writing.writing && writing.writing.usernames.map((value, index) => {
                        return (
                          <Radio style={radioStyle} value={value} key={index + 1}>
                            {value}
                          </Radio>
                        );
                      })}
                    </Radio.Group>
                  </Modal>
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
  const { writing,upload } = state;
  return { writing,upload };
}

const actionCreators = {
  getWriting: writingActions.getWriting,
  submitWriting: writingActions.submitWriting,
  uploadWritingImage:writingActions.uploadWritingImage
}

const connectedWritingPage = connect(mapState, actionCreators)(WritingPage);
export { connectedWritingPage as WritingPage };