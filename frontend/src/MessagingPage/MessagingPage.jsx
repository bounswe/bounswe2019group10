import React from 'react';
import { connect } from 'react-redux';
import { Layout, Row, Col,Typography,Input,Card,Icon,Avatar,AutoComplete } from 'antd';

import { userActions } from '../_actions';

import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';

const { Content } = Layout;
const { Title } = Typography;

class MessagingPage extends React.Component {

    constructor(props) {
      super(props);
      this.state = {
        message: "",
        newMessage: false,
        userSearchData: [],
        chatUsername: "Username",
        userSearch: ""
      };
      this.onChange = this.onChange.bind(this);
      this.sendMessage = this.sendMessage.bind(this);
      this.searchUser = this.searchUser.bind(this);
      this.changeUserSearch = this.changeUserSearch.bind(this);
      this.newMessage = this.newMessage.bind(this);
      this.userSelect = this.userSelect.bind(this);
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

    searchUser(e){
      console.log("search user",e);
      this.props.userSearch(this.state.userSearch);
    }

    userSelect(e){
      console.log("select user",e);
      // start conversation with new user
      this.props.clearUserSearch();
      this.setState({
        chatUsername: e,
        userSearchData: [],
        newMessage: false,
      });
    }

    changeUserSearch(e){
      this.setState({
        userSearch: e,
      });
    }

    newMessage(e){
      this.setState({
        newMessage: true
      });
    }
    
    componentDidMount() {
    }

    componentDidUpdate(){
      if (this.state.newMessage && this.state.userSearchData && this.state.userSearchData.length==0 && 
        this.props.userSearchResults && this.props.userSearchResults.length>0){
        const searchResult = [];
        for (const [index, userSearchResult] of this.props.userSearchResults.entries()) {
          searchResult.push(userSearchResult.username);
        }
        this.setState({
          userSearchData: searchResult,
        });
      }
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
            <Card title="Messaging" extra={<Icon type="form" style={{ fontSize: '18px' }} onClick={this.newMessage}/>} bodyStyle={{overflow:"scroll", height:"70vh"}}>
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
            <Card title={
              this.state.newMessage ? (
                <AutoComplete
                  className="global-search"
                  size="large"
                  dataSource={ this.state.userSearchData }
                  style={{ width: '100%' }}
                  onSelect={this.userSelect}
                  optionLabelProp="text"
                  onChange={this.changeUserSearch}
                >
                  {/* <Input
                    suffix={
                      <Button
                        className="search-btn"
                        style={{ marginRight: -12 }}
                        size="large"
                        type="primary"
                      >
                        <Icon type="search" />
                      </Button>
                    }
                  /> */}
                  <Input addonAfter={<Icon type="caret-right" onClick={this.searchUser}/>} 
                  placeholder="Search a user" />
                </AutoComplete>

                
              ) : (
                this.state.chatUsername
              ) }
              bodyStyle={{height:"70vh",display:"flex",  flexDirection: "column"}}>
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
  const { activeLanguage,searchResults, userSearchResults } = users;
  return { searchResults,activeLanguage,userSearchResults };
}

const actionCreators = {
  search: userActions.search,
  clearSearch: userActions.clearSearch,
  userSearch: userActions.userSearch,
  clearUserSearch: userActions.clearUserSearch
}

const connectedMessagingPage = connect(mapState, actionCreators)(MessagingPage);
export { connectedMessagingPage as MessagingPage };