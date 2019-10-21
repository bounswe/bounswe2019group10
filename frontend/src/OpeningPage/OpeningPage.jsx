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
import { userService } from '../_services';

class OpeningPage extends React.Component {
  constructor(props) {
    super(props);
    this.props.logout();
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
    if(this.state.okText =="Log In"){
      this.props.login(this.state.username, this.state.password);
    }
    if(this.state.okText =="Sign Up"){
      const user= {
        mail:this.state.mail,
        username:this.state.username,
        password:this.state.password
      }
      this.props.register(user);
    }
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
    const { loggingIn } = this.props;
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
            {loggingIn &&
              <img src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
            }
            {alert.message &&
              <div className={`alert ${alert.type}`}>{alert.message}</div>
            } 
          </Modal>
        </Content>
      </Layout>
    );
  }
}

function mapState(state) {
  const { loggingIn } = state.authentication;
  const { alert } = state;
  return { loggingIn,alert };
}

const actionCreators = {
  login: userActions.login,
  register: userActions.register,
  logout: userActions.logout
}

const connectedOpeningPage = connect(mapState, actionCreators)(OpeningPage);
export { connectedOpeningPage as OpeningPage };