import React from 'react';
import { Layout, Menu, Breadcrumb, Row, Col,
      Avatar, Descriptions, List } from 'antd';
import 'antd//dist/antd.css';
import './ProfilePage.css';

const { Header, Content, Footer } = Layout;

class ProfilePage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    selectedTab: 'Profile'
    }
  };

  render() {
    return (
      <Layout className="layout">
        <Header>
          <Row>
            <Col span={10} />
            <Col >
              <Avatar className="logo" style={{ backgroundColor: '#87d068' }} icon="user" />
              <Menu
                theme="dark"
                mode="horizontal"
                defaultSelectedKeys={['1']}
                style={{ lineHeight: '64px' }}
              >
                <Menu.Item
                  key="1"
                  onClick={() => this.setState({selectedTab: 'Profile'})}>
                  Profile
                </Menu.Item>
                <Menu.Item
                  key="2"
                  onClick={() => this.setState({selectedTab: 'Languages'})}>
                  Languages
                </Menu.Item>
              </Menu>
            </Col>
          </Row>
        </Header>
        <Content style={{ padding: '0 50px' }}>
          <Breadcrumb style={{ margin: '16px 0' }}>
            <Breadcrumb.Item>User</Breadcrumb.Item>
            <Breadcrumb.Item>{this.state.selectedTab}</Breadcrumb.Item>
          </Breadcrumb>
          <Profile_Language
            selectedTab = {this.state.selectedTab}
          />
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

class Profile_Language extends React.Component {
  render() {
    if (this.props.selectedTab === 'Profile') {
      return (
        <div style={{ background: '#fff', padding: 24, minHeight: 280, width:600 }}>
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
    )} else {
      return(
        <div style={{ background: '#fff', padding: 24, minHeight: 280, width:800 }}>
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
}

export default ProfilePage