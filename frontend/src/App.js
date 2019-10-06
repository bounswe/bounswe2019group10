import React from 'react';
import logo from './logo.png';
import { Redirect } from 'react-router-dom'
import { Button,Container,Row,Col } from 'react-bootstrap';
import './App.css';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {redirect: false};
  }
  handleClick(action){
    if (action==="get-started"){
      alert(action);
    } else if (action==="already-have"){
      this.setState({redirect: true});
    }
  }
  render(){
    if (this.state.redirect) {
      return <Redirect push to="/users" />;
    }
    return (
      <div className="App">
        <header className="App-header">
          <Container>
            <Row>
              <Col md={4}>yallp</Col>
              <Col md={{ span: 4, offset: 4 }}>SITE LANGUAGE: ENGLISH</Col>
            </Row>
            <Row>
              <Col><img src={logo} className="App-logo" alt="logo" /></Col>
              <Col className="App-started">
                <p>
                  Learn a language for free. Forever.
                </p>
                <Button onClick={()=> this.handleClick("get-started")}>Get Started</Button>
                <Button onClick={()=> this.handleClick("already-have")}>I Already Have An Account</Button>    
              </Col>
            </Row>
          </Container>
        </header>
      </div>
    );
  }
}

export default App;
