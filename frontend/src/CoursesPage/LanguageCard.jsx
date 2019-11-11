import React from 'react';
import { connect } from 'react-redux';
import { Avatar, Card,Icon } from 'antd';

import ukFlag from '../images/flags/uk.png';
import spanishFlag from '../images/flags/spanish.png';
import { flags } from '../_helpers';

class LanguageCard extends React.Component {

    constructor(props) {
      super(props);
    }

    componentDidMount() {
    }

    addLanguage(language){
      console.log(language + " added");
    }

    render() {
      const { language } = this.props;
      return (
        <Card style={{textAlign: 'center'}} hoverable={true} onClick={() => this.addLanguage(language)}>
          <div style={{display: "flex", justifyContent: "flex-end"}}>
          <Icon type="check-circle" style={{ fontSize: '24px', color: '#08c' }} />
          </div>
          <p><Avatar size={96} src={flags[language]["src"]} /></p>
          <p>{ language }</p>

        </Card>      
        );
    }
}

function mapState(state) {
  
  return {  };
}

const actionCreators = {
}

const connectedLanguageCard = connect(mapState, actionCreators)(LanguageCard);
export { connectedLanguageCard as LanguageCard };