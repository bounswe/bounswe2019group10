import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import {
  Layout, Col, Radio, Input, Button, Typography, Modal
} from 'antd';
import 'antd//dist/antd.css';
import './WritingPage.css';
import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';

import { writingActions } from '../_actions';
const { Content } = Layout;
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
      isSubmit: false,
      writingFinished: false,
      modalVisible: false
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleClick = this.handleClick.bind(this);
    this.setModalVisible = this.setModalVisible.bind(this);
  }
  handleClick(action) {
    const t = {
      writingId: this.state.writingId,
      answer: this.state.answer,
      evaluatorUsername: this.state.reviewer
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
  onChange = e => {
    this.setState({
      reviewer: e.target.value,
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
                    <TextArea placeholder="Write your answer here"
                      autoSize={{ minRows: 10, maxRows: 15 }}
                      name="answer"
                      value={this.state.answer}
                      onChange={this.handleChange}
                    />
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
  const { writing } = state;
  return { writing };
}

const actionCreators = {
  getWriting: writingActions.getWriting,
  submitWriting: writingActions.submitWriting,
}

const connectedWritingPage = connect(mapState, actionCreators)(WritingPage);
export { connectedWritingPage as WritingPage };