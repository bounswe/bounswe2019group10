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
        chatUsername: "Select a chat in the left bar",
        userSearch: "",
        conversationId: -1
      };
      this.onChange = this.onChange.bind(this);
      this.sendMessage = this.sendMessage.bind(this);
      this.searchUser = this.searchUser.bind(this);
      this.changeUserSearch = this.changeUserSearch.bind(this);
      this.newMessage = this.newMessage.bind(this);
      this.userSelect = this.userSelect.bind(this);
      this.getActiveConversation = this.getActiveConversation.bind(this);
      this.convertDate = this.convertDate.bind(this);
    }

    onChange(e) {
      this.setState({
        message: e.target.value,
      });
    };

    sendMessage(e){
      this.props.sendMessage(this.state.chatUsername,this.state.message);
      this.setState({
        message: "",
      });
    }

    searchUser(e){
      console.log("search user",e);
      this.props.userSearch(this.state.userSearch);
    }

    userSelect(e){
      this.props.clearUserSearch();
      const conversation = this.props.allConversations.find(conversation => conversation.otherUsername===e);
      if (conversation){
        const conversationId = conversation.messages[0].conversationId;
        this.props.getConversation(conversationId);
      }else{
        this.props.clearActiveConversation();
      }
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
      this.props.clearActiveConversation();
      this.setState({
        newMessage: true
      });
    }

    getActiveConversation(conversationId,chatUsername){
      this.props.getConversation(conversationId);
      this.setState({ chatUsername,conversationId, newMessage: false });
    }

    convertDate(date){
      let d = date;
      d = [
        '0' + d.getDate(),
        '0' + (d.getMonth() + 1),
        '' + d.getFullYear(),
        '0' + d.getHours(),
        '0' + d.getMinutes()
      ].map(component => component.slice(-2));
      d[2] = date.getFullYear();
      return ""+d[3]+"."+d[4]+" "+d[0]+"."+d[1]+"."+d[2];
    }

    componentDidMount() {
      this.props.getConversations();
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
      if (this.props.messageSent){
        this.props.clearMessageSent();
        this.props.getConversations();
        this.props.getConversation(this.state.conversationId);
      }
    }

    render() {
      const { allConversations,activeConversation } = this.props;
      return (
          <Layout className="layout">
          <HeaderComponent />
          <Content style={{ padding: '50px 50px' }}>
            <Row>
            <Col span={4} />
            <Col span={6}>
              <Card title="Messaging" extra={<Icon type="form" style={{ fontSize: '18px' }} onClick={this.newMessage}/>} bodyStyle={{overflow:"scroll", height:"70vh"}}>
                {allConversations && allConversations.map((conversation, index) => {
                  const lastMessage = conversation.messages[conversation.messages.length - 1];
                  const messageDate = new Date(lastMessage.messageTime);
                  const conversationId = conversation.messages[0].conversationId;
                  const messageTime = this.convertDate(messageDate);
                  return (
                    <Card hoverable key={index} onClick={() => this.getActiveConversation(conversationId,conversation.otherUsername) }>
                      <Row>
                        <Col span={8} >
                          <Avatar size={64} icon="user" />
                        </Col>
                        <Col span={16} >
                          <div style={{display:"flex",flexDirection: "column"}}>
                            {/* <span style={{flex:"1"}}> {conversation.otherUsername} </span> */}
                            <span> {conversation.otherUsername} </span>
                            <span style={{fontSize: "10px"}}>{ messageTime }</span>
                          </div>
                          <p>
                            { lastMessage.messageText.substring(0, 15) }
                            { lastMessage.messageText.length>15 && "..." }
                          </p>
                        </Col>
                      </Row>
                    </Card>
                  )
                })}  
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
                  <Input addonBefore="to:" addonAfter={<Icon type="caret-right" onClick={this.searchUser}/>} 
                  placeholder="Search a user" />
                </AutoComplete>                
              ) : (
                this.state.chatUsername
              ) }
              bodyStyle={{height:"70vh",display:"flex",  flexDirection: "column"}}>
              <div style={{overflow:"scroll", display:"flex",flexDirection: "column-reverse"}}>
                {activeConversation && activeConversation.messages && activeConversation.messages.length > 0 &&
                 activeConversation.messages.slice().reverse().map((message, index) => {
                  const messageDate = new Date(message.messageTime);
                  const messageTime = this.convertDate(messageDate);
                  return (
                    <Card key={index}>
                      <Row>
                        <Col span={4} >
                          <Avatar size={32} icon="user" />    
                        </Col>
                        <Col span={20} >
                          <div style={{display:"flex"}}>
                            <span style={{flex:"1"}}>{message.senderUsername}</span>
                            <span>{ messageTime }</span>
                          </div>
                          <p>{ message.messageText }</p>        
                        </Col>
                      </Row>
                    </Card>
                  );
                })}
              </div>
              <Input addonAfter={<Icon type="caret-right" onClick={this.sendMessage}/>} 
                    placeholder="Send a message"
                    onChange={this.onChange} 
                    value={this.state.message}
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
  const { activeLanguage,searchResults, userSearchResults, allConversations,activeConversation,messageSent } = users;
  return { searchResults,activeLanguage,userSearchResults,allConversations,activeConversation,messageSent };
}

const actionCreators = {
  search: userActions.search,
  clearSearch: userActions.clearSearch,
  userSearch: userActions.userSearch,
  clearUserSearch: userActions.clearUserSearch,
  sendMessage: userActions.sendMessage,
  getConversations: userActions.getConversations,
  getConversation: userActions.getConversation,
  clearMessageSent: userActions.clearMessageSent,
  clearActiveConversation: userActions.clearActiveConversation,
}

const connectedMessagingPage = connect(mapState, actionCreators)(MessagingPage);
export { connectedMessagingPage as MessagingPage };