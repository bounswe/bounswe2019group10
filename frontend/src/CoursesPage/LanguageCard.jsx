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
      const { isLearning,language,addLanguage,removeLanguage,getProfile } = this.props;
      let title = 'Do you want to learn '+this.props.language+'?';
      let content = 'You can use the materials of '+this.props.language+".";
      let okText = 'Start';
      if (this.props.isLearning){
        title = 'Do you want to remove '+this.props.language+' from your profile?';
        content = 'You will not be able to use the materials of '+this.props.language+" any more.";
        okText = "Remove";
      }
      confirm({
        title: title,
        content: content,
        okText: okText,
        cancelText: 'Cancel',
        onOk() {
          if (isLearning){
            removeLanguage(language);
            getProfile();
          }
          else{
            addLanguage(language);
          }
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
  removeLanguage: userActions.removeLanguage,
  getProfile: userActions.getProfile,
}

const connectedLanguageCard = connect(mapState, actionCreators)(LanguageCard);
export { connectedLanguageCard as LanguageCard };