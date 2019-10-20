import React from 'react';
import logo from '../images/logo.png';
import { connect } from 'react-redux';
import { Button,Container,Row,Col,Tab,Tabs } from 'react-bootstrap';
import './OpeningPage.css';
import JwModal from './modal';
import {Login} from './login';
import {SignUp} from './signup';
import { userActions } from '../_actions';

class OpeningPage extends React.Component {
  constructor(props) {
    super(props);
    // reset login status
    this.props.logout();
  }
    handleClick(action){
      if (action==="get-started"){
        alert(action);
      } else if (action==="already-have"){
        this.setState({redirect: true});
      }
    }  

    render() {
        return (
            <div className="App">
              <header className="App-header">
                <Container>
                  <Row>
                    <Col md={4}>yallp</Col>
                    <Col md={{ span: 4, offset: 8 }}>SITE LANGUAGE: ENGLISH</Col>
                  </Row>
                  <Row>
                    <Col><img src={logo} className="App-logo" alt="logo" /></Col>
                    <Col className="App-started">
                      <p>
                        Learn a language for free. Forever.
                      </p>
                      <Button onClick={()=> this.handleClick("get-started")}>Get Started</Button>
                      <Button onClick={JwModal.open('custom-modal-1')}>I Already Have An Account</Button>
                      <JwModal id="custom-modal-1">
                        <Tabs defaultActiveKey="login" id="tabinmodal">
                          <Tab eventKey="login" title="Login">
                            <Login>
                              Login Page
                            </Login>
                          </Tab>
                          <Tab eventKey="signup" title="Sign Up">
                            <SignUp>
                              Register Page
                            </SignUp>
                          </Tab>
                        </Tabs>                
                        <Button onClick={JwModal.close('custom-modal-1')}>Close</Button>
                      </JwModal>
                    </Col>
                  </Row>
                </Container>
              </header>
            </div>
        );
    }
}

function mapState(state) {
  return { };
}

const actionCreators = {
  logout: userActions.logout
}

const connectedOpeningPage = connect(mapState, actionCreators)(OpeningPage);
export { connectedOpeningPage as OpeningPage };