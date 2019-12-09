import React from 'react';
import { connect } from 'react-redux';
import { Layout, Row, Col,Typography,Input,Card,Icon,Avatar } from 'antd';

import { userActions } from '../_actions';

import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';

const { Content } = Layout;
const { Title } = Typography;

class MessagingPage extends React.Component {

    constructor(props) {
      super(props);
      this.state = {
        message: ""
      }
      this.onChange = this.onChange.bind(this);
      this.sendMessage = this.sendMessage.bind(this);
      
    }
    onChange(e) {
      this.setState({
        message: e.target.value,
      });
    };

    sendMessage(e){
      console.log("send message",e);
      this.setState({
        message: "",
      });
    }
    
    componentDidMount() {
    }

    render() {
      const {searchResults} = this.props;
      return (
          <Layout className="layout">
          <HeaderComponent />
          <Content style={{ padding: '50px 50px' }}>
            <Row>
            <Col span={4} />
            <Col span={6}>
            <Card title="Messaging" extra={<Icon type="form" style={{ fontSize: '18px' }}/>} bodyStyle={{overflow:"scroll", height:"70vh"}}>
              <Card hoverable>
                <Row>
                  <Col span={8} >
                    <Avatar size={64} icon="user" />
                  </Col>
                  <Col span={16} >
                    <div style={{display:"flex"}}>
                      <span style={{flex:"1"}}>Username</span>
                      <span>Date</span>
                    </div>
                    <p>Message...</p>
                  </Col>
                </Row>
              </Card>
              <Card hoverable>
                <Row>
                  <Col span={8} >
                    <Avatar size={64} icon="user" />
                  </Col>
                  <Col span={16} >
                    <div style={{display:"flex"}}>
                      <span style={{flex:"1"}}>Username</span>
                      <span>Date</span>
                    </div>
                    <p>Message...</p>
                  </Col>
                </Row>
              </Card>
            </Card>
            </Col>
            <Col span={10}>
            <Card title="Username" bodyStyle={{height:"70vh",display:"flex",  flexDirection: "column"}}>
              <div style={{overflow:"scroll"}}>
                <Card>
                  <Row>
                    <Col span={4} >
                      <Avatar size={32} icon="user" />    
                    </Col>
                    <Col span={20} >
                      <div style={{display:"flex"}}>
                        <span style={{flex:"1"}}>Username</span>
                        <span>Date</span>
                      </div>
                      <p>Message...</p>        
                    </Col>
                  </Row>
                </Card>
                <Card>
                  <Row>
                    <Col span={4} >
                      <Avatar size={32} icon="user" />    
                    </Col>
                    <Col span={20} >
                      <div style={{display:"flex"}}>
                        <span style={{flex:"1"}}>Username</span>
                        <span>Date</span>
                      </div>
                      <p>Message...</p>        
                    </Col>
                  </Row>
                </Card>
                <Card>
                  <Row>
                    <Col span={4} >
                      <Avatar size={32} icon="user" />    
                    </Col>
                    <Col span={20} >
                      <div style={{display:"flex"}}>
                        <span style={{flex:"1"}}>Username</span>
                        <span>Date</span>
                      </div>
                      <p>Message...</p>        
                    </Col>
                  </Row>
                </Card>
                <Card>
                  <Row>
                    <Col span={4} >
                      <Avatar size={32} icon="user" />    
                    </Col>
                    <Col span={20} >
                      <div style={{display:"flex"}}>
                        <span style={{flex:"1"}}>Username</span>
                        <span>Date</span>
                      </div>
                      <p>Message...</p>        
                    </Col>
                  </Row>
                </Card>
                <Card>
                  <Row>
                    <Col span={4} >
                      <Avatar size={32} icon="user" />    
                    </Col>
                    <Col span={20} >
                      <div style={{display:"flex"}}>
                        <span style={{flex:"1"}}>Username</span>
                        <span>Date</span>
                      </div>
                      <p>Message...</p>        
                    </Col>
                  </Row>
                </Card>
                <Card>
                  <Row>
                    <Col span={4} >
                      <Avatar size={32} icon="user" />    
                    </Col>
                    <Col span={20} >
                      <div style={{display:"flex"}}>
                        <span style={{flex:"1"}}>Username</span>
                        <span>Date</span>
                      </div>
                      <p>Message...</p>        
                    </Col>
                  </Row>
                </Card>
                <Card>
                  <Row>
                    <Col span={4} >
                      <Avatar size={32} icon="user" />    
                    </Col>
                    <Col span={20} >
                      <div style={{display:"flex"}}>
                        <span style={{flex:"1"}}>Username</span>
                        <span>Date</span>
                      </div>
                      <p>Message...</p>        
                    </Col>
                  </Row>
                </Card>
                <Card>
                  <Row>
                    <Col span={4} >
                      <Avatar size={32} icon="user" />    
                    </Col>
                    <Col span={20} >
                      <div style={{display:"flex"}}>
                        <span style={{flex:"1"}}>Username</span>
                        <span>Date</span>
                      </div>
                      <p>Message...</p>        
                    </Col>
                  </Row>
                </Card>
                <Card>
                  <Row>
                    <Col span={4} >
                      <Avatar size={32} icon="user" />    
                    </Col>
                    <Col span={20} >
                      <div style={{display:"flex"}}>
                        <span style={{flex:"1"}}>Username</span>
                        <span>Date</span>
                      </div>
                      <p>Message...</p>        
                    </Col>
                  </Row>
                </Card>
              </div>
              <Input addonAfter={<Icon type="caret-right" onClick={this.sendMessage}/>} 
                    placeholder="Send a message"
                    onChange={this.changeTerm} 
                    style={{marginTop:"auto",paddingTop:"20px"}} />
            </Card>
            </Col>
            <Col span={4} />
            </Row>
          </Content>
          <FooterComponent />
          </Layout>
        );
    }
}

function mapState(state) {
  const { users } = state;
  const { activeLanguage,searchResults } = users;
  return { searchResults,activeLanguage };
}

const actionCreators = {
  search: userActions.search,
  clearSearch: userActions.clearSearch
}

const connectedMessagingPage = connect(mapState, actionCreators)(MessagingPage);
export { connectedMessagingPage as MessagingPage };