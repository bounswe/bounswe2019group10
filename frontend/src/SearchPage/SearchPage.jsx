import React from 'react';
import { connect } from 'react-redux';
import { Layout, Row, Col,Radio,Typography,Button,Input } from 'antd';

import { userActions } from '../_actions';

import { HeaderComponent } from '../HeaderComponent';
import { FooterComponent } from '../FooterComponent';
import { WritingResultComponent } from './WritingResultComponent';
import { QuizResultComponent } from './QuizResultComponent';
const { Content } = Layout;
const { Title } = Typography;

class SearchPage extends React.Component {

    constructor(props) {
      super(props);
      this.state = {
        type: "quiz",
        term: "",
        searchedType: ""
      }
      this.onChange = this.onChange.bind(this);
      this.search = this.search.bind(this);
      this.changeTerm = this.changeTerm.bind(this);
    }
    onChange(e) {
      this.setState({
        type: e.target.value,
      });
    };

    changeTerm(e){
      this.setState({
        term: e.target.value,
      });
    }
    search(e){
      this.setState({
        searchType: this.state.type
      });
      this.props.clearSearch();
      this.props.search(this.state.type,this.state.term,this.props.activeLanguage.id);
    }
    componentDidMount() {
    }

    render() {
      const {searchResults} = this.props;
      return (
          <Layout className="layout">
          <HeaderComponent />
          <Content style={{ padding: '0 50px' }}>
            <Row>
            <Col span={4} />
            <Col span={16}>
            <Title style={{paddingTop:"25px",paddingBottom:"25px"}} level={3}>Search Options</Title>
            <p>
            <Title level={4}>Search In</Title>
            <Radio.Group onChange={this.onChange} value={this.state.type}>
              <Radio value="quiz">Quiz</Radio>
              <Radio value="writing">Writing</Radio>
            </Radio.Group>
            </p>
            <p style={{width:"60%"}}>
              <Input placeholder="Search in yallp" onChange={this.changeTerm}/>
            </p>
            <Button type="primary" onClick={this.search}>Search</Button>
            {searchResults &&
            <Title style={{paddingTop:"25px",paddingBottom:"25px"}} level={4}>Search Results</Title>
            }
            {this.state.searchType=="writing" && searchResults && (
              searchResults.map((searchResult, i) => {
              return (
                <Row key={i}>
                  <WritingResultComponent writingId={searchResult.writingDTO.id} writingName={searchResult.writingDTO.writingName} 
                    taskText={searchResult.writingDTO.taskText} solved={searchResult.solved} />
                </Row>
                ) 
            }))}
            {this.state.searchType=="quiz" && searchResults && (
              searchResults.map((searchResult, i) => {
              return (
                <Row key={i}>
                  <QuizResultComponent quizId={searchResult.quiz.id} quizType={searchResult.quiz.quizType} 
                    levelName={searchResult.quiz.levelName} solved={searchResult.solved} score={searchResult.score} />
                </Row>
                ) 
            }))}
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

const connectedSearchPage = connect(mapState, actionCreators)(SearchPage);
export { connectedSearchPage as SearchPage };