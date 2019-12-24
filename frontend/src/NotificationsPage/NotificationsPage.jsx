import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import {
  Layout, Menu, Breadcrumb, Row, Col, Card, Radio,
  Avatar, Descriptions, List, Input, Button, Typography, Modal, Select, notification
} from 'antd';
import 'antd//dist/antd.css';
import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';

import { history } from '../_helpers';
import { userActions, writingActions } from '../_actions';
const { Header, Content, Footer } = Layout;
const { SubMenu } = Menu;
const { TextArea } = Input;
const { Title } = Typography;
const {Option} = Select;

class NotificationsPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      notificationType:"",
      notificationText:"",
    };
    this.handleChange = this.handleChange.bind(this);
    this.seeNotifications = this.seeNotifications.bind(this);
  }
  handleChange(e) {
    const { name, value } = e.target;
    this.setState({ [name]: value });
  }
  componentDidMount() {
    this.props.getNotifications();
  }

  componentWillUnmount() {
    this.props.notificationsSeen(this.props.notifications); 
  }

  seeNotifications(notifications){
    this.props.notificationsSeen(notifications);
  }

  onChange = e => {
    this.setState({
      reviewer: e.target.value,
    });
  };
  render() {
    const { notifications } = this.props;

    return (
      <Layout className="layout">
        <HeaderComponent />
        <Content style={{ padding: '0 50px' }}>
          <Col span={4} />
          <Col span={16}>

            <div style={{ margin: '10px 0' }} />
            <Card title="Your Notifications">
              <p
                style={{
                  fontSize: 14,
                  color: 'rgba(0, 0, 0, 0.85)',
                  marginBottom: 16,
                  fontWeight: 500,
                }}
              >
            </p>
              { !notifications && 
                <Card type="inner" title="No New Notifications" >
                </Card>
              }
              {notifications && notifications.map((value, index) => {
                let firstfield;
                if(value.notificationType==="NEW_MESSAGE"){
                    firstfield=" You have a message of from another user.";
                }else if(value.notificationType==="WRITING_EVALUATE"){
                    firstfield= " A user requested for an evaluation of their writing.";
                }else if(value.notificationType==="WRITING_RESULT"){
                    firstfield= " Your writing is evaluated";
                }else{
                    firstfield= " Your have a notification";
                }    
                let secondfield = value.text;
                return (
                  <Card type="inner" title={firstfield} key={index + 1}>
                    {secondfield}
                  </Card>
                );
              })}
            </Card>
            <div style={{ margin: '10px 0' }} />
            

          </Col>
          <Col span={4} />
        </Content>
        <FooterComponent />
      </Layout>
    );
    }
}

function mapState(state) {
const { notifications } = state.users;
return { notifications };
}

const actionCreators = {
getNotifications: userActions.getNotifications,
notificationsSeen: userActions.notificationsSeen
}

const connectedNotificationsPage = connect(mapState, actionCreators)(NotificationsPage);
export { connectedNotificationsPage as NotificationsPage };
