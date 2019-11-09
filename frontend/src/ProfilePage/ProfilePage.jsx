import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { Layout, Menu, Breadcrumb, Row, Col,
      Avatar, Descriptions, List } from 'antd';
import 'antd//dist/antd.css';
import './ProfilePage.css';
import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';

import { history } from '../_helpers';
import { userActions } from '../_actions';
const { Header, Content, Footer } = Layout;
const { SubMenu } = Menu;

class ProfilePage extends React.Component {
  constructor(props) {
    super(props);
  }
  
  componentDidMount() {
    this.props.getProfile();
  }

  render() {
    const { profile } = this.props;
    return (
      <Layout className="layout menu-style">
        <HeaderComponent />        
        <Content style={{ marginTop: '24px'}}>
          <Row>
            <Col span={1} />
            <Col span={11}>
              {profile && <Profile profile={profile} />}
            </Col>
            {/* <Col span={12}>
              <Language />
            </Col> */}
          </Row>
        </Content>
        <FooterComponent />        
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
  constructor(props) {
    super(props);
    console.log(props.profile);
  }
  
  render() {
    const { profile } = this.props;
    return (
      <div style={{ background: '#fff', padding: 24, minHeight: 280, width:800 }}>
        <Descriptions title="User Info" bordered={true} column={1}>
          <Descriptions.Item label="E-mail">{profile.mail}</Descriptions.Item>
          <Descriptions.Item label="User Name">{profile.username}</Descriptions.Item>
          <Descriptions.Item label="Bio">
            {
              profile.bio 
              ? profile.bio 
              : "No bio provided."
            }
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
  const { users } = state;
  const { profile } = users;

  return { profile };
}

const actionCreators = {
  getProfile: userActions.getProfile,
}

const connectedProfilePage = connect(mapState, actionCreators)(ProfilePage);
export { connectedProfilePage as ProfilePage };