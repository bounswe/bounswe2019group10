import React from 'react';
import { connect } from 'react-redux';
import { Layout, Row, Col, Typography } from 'antd';
import { userActions } from '../_actions';

import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';
import { LanguageCard } from './LanguageCard';
const { Content } = Layout;
const { Title } = Typography;

class CoursesPage extends React.Component {

    componentDidMount() {
      this.props.getProfile();
      this.props.getAllLanguages();
    }

    render() {
      const { profile, all_languages } = this.props;
      let memberLanguages = [];
      if (profile){
        memberLanguages = profile.memberLanguages.map((l) => l.language.languageName);
      }
      
      return (
          <Layout className="layout">
          <HeaderComponent />
          <Content style={{ padding: '0 50px' }}>
            <Row>
            <Col span={4} />
            <Col span={16}>
            <Title style={{paddingTop:"25px",paddingBottom:"25px"}} level={3}>Language Courses</Title>
            <Row>
              {all_languages && all_languages.map((language, i) => {
                let isLearning = false;
                if (memberLanguages.includes(language.languageName)){
                  isLearning = true;
                }
                return (
                  <Col span={6} key={i}>
                    <LanguageCard language={language.languageName} languageId={language.id} isLearning={isLearning} />
                  </Col>
                  ) 
              })}
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
  const { all_languages } = state.users;
  const { profile } = state.users;
  return { all_languages, profile };
}

const actionCreators = {
  getProfile: userActions.getProfile,
  getAllLanguages: userActions.getAllLanguages
}

const connectedCoursesPage = connect(mapState, actionCreators)(CoursesPage);
export { connectedCoursesPage as CoursesPage };