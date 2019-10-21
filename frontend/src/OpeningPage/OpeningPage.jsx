import React from 'react';
import logo from '../images/logo.png';
import { connect } from 'react-redux';
import { Layout, Menu, Button, Col, Row, Modal, Input } from 'antd';
const { Header, Content } = Layout;
const { SubMenu } = Menu;
import { Tabs } from 'antd';
const { TabPane } = Tabs;

import './OpeningPage.css';
import { history } from '../_helpers';

import { userActions } from '../_actions';

class OpeningPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modalVisible: false,
      okText: "Log In",
      mail:"",
      username:"",
      password:""
    };
    this.changeForm = this.changeForm.bind(this);
    this.setModalVisible = this.setModalVisible.bind(this);
    this.handleClick = this.handleClick.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.clearForms = this.clearForms.bind(this);
  }

  setModalVisible(modalVisible) {
    this.setState({ modalVisible });
  }

  handleClick(action){
    // TODO send login register
    this.props.login(this.state.username, this.state.password);
  }

  changeForm(okText) {
    this.clearForms();
    this.setState({ okText });
  }

  handleChange(e) {
    const { name, value } = e.target;
    this.setState({ [name]: value });
  }

  clearForms(){
    this.setState({
      username:"",
      password:"",
      mail:""
    });
  }


  render() {
    const { username, password, mail } = this.state;
    return (
        <Layout>
        <Header style={{ position: "fixed", zIndex: 1, width: "100%", height: "64px" }}>
          <Row style={{ height: "64px" }}>
            <Col span={0} />
            <Col id='yallp' span={10}> 
            YALLP 
            </Col>
            <Col span={8} />
            <Col span={6}>
              <Menu theme="dark" mode="horizontal" style={{ lineHeight: "64px" }}>
                <SubMenu
                  title={
                    <span className="submenu-title-wrapper">
                      SITE LANGUAGE: ENGLISH
                    </span>
                  }
                >
                  <Menu.ItemGroup title='Spanish' />
                  <Menu.ItemGroup title='Turkish' />
                  <Menu.ItemGroup title='German' />
                </SubMenu>
              </Menu>
            </Col>
          </Row>
        </Header>
        <Content style={{ marginTop: '250px', minHeight: '80vh' }}>
          <Row>
            <Col span={6} />
            <Col span={6}>
              <img src={logo} className="App-logo" alt="logo" />  
            </Col>
            <Col span={2} />
            <Col span={6}>
              <Row>
                <Col> Learn a language for free. Forever. </Col>
              </Row>
              <Row style={{ marginTop: '24px' }}>
                <Col>
                  <Button type='primary' style={{width: '250px'}}>Get Started</Button>
                </Col>
              </Row>
              <Row style={{ marginTop: '24px' }}>
                <Col>
                  <Button type='primary' onClick={() => this.setModalVisible(true)}
                  style={{width: '250px'}}>
                    I Already Have An Account</Button>
                </Col>
              </Row>
            </Col>
          </Row>
          <Modal
            title=""
            centered
            visible={this.state.modalVisible}
            onOk={() => this.handleClick("signin")}
            onCancel={() => this.setModalVisible(false)}
            okText={ this.state.okText }
          >
            <Tabs defaultActiveKey="1" onChange={this.changeForm}>
              <TabPane tab="Log In" key="Log In">
                <Input placeholder="Username" value={username} name="username" onChange={this.handleChange} />
                <Input.Password placeholder="Password" name="password" value={password} onChange={this.handleChange} style={{marginTop: '12px'}} />
              </TabPane>
              <TabPane tab="Sign Up" key="Sign Up">
                <Input placeholder="E-mail" value={mail} name="mail" onChange={this.handleChange} />
                <Input placeholder="Username" style={{marginTop: '12px'}} name="username" value={username} onChange={this.handleChange} />
                <Input.Password placeholder="Password" style={{marginTop: '12px'}} name="password" value={password} onChange={this.handleChange}/>
              </TabPane>
            </Tabs>
          </Modal>
        </Content>
      </Layout>
    );
  }
}

function mapState(state) {
  return { };
}

const actionCreators = {
  login: userActions.login
}

const connectedOpeningPage = connect(mapState, actionCreators)(OpeningPage);
export { connectedOpeningPage as OpeningPage };