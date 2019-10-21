import React from 'react';
import { connect } from 'react-redux';
import { Layout, Menu, Breadcrumb, Row, Col,
      Avatar, Descriptions, List } from 'antd';
import 'antd//dist/antd.css';
import './ProfilePage.css';

const { Header, Content, Footer } = Layout;
const { SubMenu } = Menu;

class ProfilePage extends React.Component {
  render() {
    return (
      <Layout className="layout">
        <Header>
          <Row>
          <Col span={0} />
            <Col id='yallp' span={10}> 
            YALLP 
            </Col>
            <Col span={8} />
            <Col span={6}>
              <Menu
                theme="dark"
                mode="horizontal"
                defaultSelectedKeys={['1']}
                style={{ lineHeight: '64px' }}
              >
                <SubMenu title={
                  <span className="submenu-title-wrapper">
                    <Avatar className="logo" style={{ backgroundColor: '#87d068' }} icon="user" />
                  </span>
                }>
                  <Menu.Item
                    key="1"
                    // onClick={() => this.setState({selectedTab: 'Profile'})}>
                  >
                    Profile
                  </Menu.Item>
                  <Menu.Item
                    key="2"
                  >
                    Log out
                  </Menu.Item>
                </SubMenu>
              </Menu>
            </Col>
          </Row>
        </Header>
        <Content style={{ marginTop: '24px'}}>
          <Row>
            <Col span={1} />
            <Col span={11}>
              <Profile />
            </Col>
            <Col span={12}>
              <Language />
            </Col>
          </Row>
          
        </Content>
        <Footer style={{ textAlign: 'center' }}>
          YALLP ©2019 Created by three awesome front-end developers.
        </Footer>
      </Layout>
    )
  }
}

const languageData = [
  {
    title: 'English',
    avatar: 'https://image.flaticon.com/icons/svg/294/294059.svg',
    color: 'red'
  },
  {
    title: 'Spanish',
    avatar: 'https://image.flaticon.com/icons/svg/323/323365.svg',
    color: 'blue'
  }
]

class Profile extends React.Component {
  render() {
      return (
        <div style={{ background: '#fff', padding: 24, minHeight: 280, width:800 }}>
          <Descriptions title="User Info" bordered={true} column={1}>
            <Descriptions.Item label="E-mail">hsnbsrbalaban@gmail.com</Descriptions.Item>
            <Descriptions.Item label="User Name">hsnbsrbalaban</Descriptions.Item>
            <Descriptions.Item label="Bio">
              Database name: MongoDB
              <br />
              Database version: 3.4
              <br />
              Package: dds.mongo.mid
              <br />
              Storage space: 10 GB
              <br />
              Replication_factor:3
              <br />
              Region: East China 1<br />
            </Descriptions.Item>
          </Descriptions>
        </div>
      )
  }
}

class Language extends React.Component {
  render() {
      return(
        <div style={{ background: '#fff', padding: 24, minHeight: 280, width:800 }}>
          <p> <b>Language</b> </p>
          <List
            itemLayout='horizontal'
            dataSource={languageData}
            renderItem={item => (
              <List.Item>
                <List.Item.Meta
                  avatar={<Avatar src={item.avatar} />}
                  title={<a href="https://ant.design" style={{color: item.color }}>{item.title}</a>}
                  description="Ant Design, a design language for background applications, is refined by Ant UED Team"
                />
              </List.Item>
            )}
          />
        </div>
      )
  }
}

function mapState(state) {
  return { };
}

const actionCreators = {
}

const connectedProfilePage = connect(mapState, actionCreators)(ProfilePage);
export { connectedProfilePage as ProfilePage };