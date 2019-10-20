import React from 'react';
import logo from './logo.png';
import { Redirect } from 'react-router-dom'
import 'antd/dist/antd.css';
import './App.css';

import { Layout, Menu, Button, Col, Row, Modal, Input } from 'antd';

const { Header, Content } = Layout;
const { SubMenu } = Menu;

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      redirect: false,
      modalVisible: false
    };
  }

  handleClick(action){
    if (action==="get-started"){
      
    } else if (action==="signin"){
      this.setState({redirect: true});
    }
  }

  setModalVisible(modalVisible) {
    this.setState({ modalVisible });
  }
  
  render(){
    if (this.state.redirect) {
      return <Redirect push to="/profile-page" />;
    }
    return (
      <Layout>
        <Header style={{ position: "fixed", zIndex: 1, width: "100%", height: "64px" }}>
          <Row style={{ height: "64px" }}>
            <Col span={2} />
            <Col id='yallp' span={10}> 
              YALLP 
            </Col>
            <Col span={6} />
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
            title="Log in"
            centered
            visible={this.state.modalVisible}
            onOk={() => this.handleClick("signin")}
            onCancel={() => this.setModalVisible(false)}
          >
            <Input placeholder="E-mail" />
            <Input.Password placeholder="Password" style={{marginTop: '12px'}} />
          </Modal>
        </Content>
      </Layout>
    );
  }
}

export default App;
