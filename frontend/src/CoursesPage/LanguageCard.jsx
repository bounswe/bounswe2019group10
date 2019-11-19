import React from 'react';
import { connect } from 'react-redux';
import { Avatar, Card,Icon,Modal } from 'antd';

const { confirm } = Modal;

import { flags } from '../_helpers';
import { userActions } from '../_actions';

class LanguageCard extends React.Component {

    constructor(props) {
      super(props);
    }

    componentDidMount() {
    }

    learn(){
      const { language,addLanguage } = this.props;
      confirm({
        title: 'Do you want to learn '+this.props.language+'?',
        content: 'You can use the materials of '+this.props.language+".",
        okText: 'Start',
        cancelText: 'Cancel',
        onOk() {
          console.log('OK');
          addLanguage(language);
        },
        onCancel() {
          console.log('Cancel');
        },
      });
    }

    render() {
      const { language } = this.props;
      return (
        <Card style={{textAlign: 'center'}} hoverable={true} onClick={() => this.learn()}>
            <div style={{display: "flex", justifyContent: "flex-end"}}>
              { this.props.isLearning ?(
                  <Icon type="check-circle" style={{ fontSize: '24px', color: '#08c' }} />
                ) : (
                  <Icon type="check-circle" style={{ fontSize: '24px', color: '#fff' }} />
                )
              }
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
  addLanguage: userActions.addLanguage,
}

const connectedLanguageCard = connect(mapState, actionCreators)(LanguageCard);
export { connectedLanguageCard as LanguageCard };