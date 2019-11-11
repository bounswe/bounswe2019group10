import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Layout, Menu, Row, Col,
    Avatar, Card,List,Typography } from 'antd';
const { Header, Content, Footer } = Layout;
const { SubMenu } = Menu;
const { Title } = Typography;

import { userActions } from '../_actions';
import { history } from '../_helpers';
import ukFlag from '../images/flags/uk.png';
import spanishFlag from '../images/flags/spanish.png';

import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';
import { LanguageCard } from './LanguageCard';

class CoursesPage extends React.Component {

    constructor(props) {
      super(props);
    }

    componentDidMount() {
      this.props.getUserLanguages();
      this.props.getAllLanguages();
    }

    addLanguage(language){
      console.log(language + " added");
    }

    render() {
      const gridStyle = {
        width: '25%',
        textAlign: 'center',
      };
      return (
          <Layout className="layout">
          <HeaderComponent />
          <Content style={{ padding: '0 50px' }}>
            <Row>
            <Col span={4} />
            <Col span={16}>
            <Title style={{paddingTop:"25px",paddingBottom:"25px"}} level={3}>Language Courses</Title>
            <Row type="flex">
              <Col span={6}>
              <LanguageCard language="English"/>
              </Col>
              <Col span={6}>
              <LanguageCard language="Spanish"/>
              </Col>
              <Col span={6}>
              <LanguageCard language="English"/>
              </Col>
              <Col span={6}>
              <LanguageCard language="Spanish"/>
              </Col>
              <Col span={6}>
              <LanguageCard language="English"/>
              </Col>
              <Col span={6}>
              <LanguageCard language="Spanish"/>
              </Col>
            </Row>
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
  
  return {  };
}

const actionCreators = {
  getUserLanguages: userActions.getUserLanguages,
  getAllLanguages: userActions.getAllLanguages
}

const connectedCoursesPage = connect(mapState, actionCreators)(CoursesPage);
export { connectedCoursesPage as CoursesPage };