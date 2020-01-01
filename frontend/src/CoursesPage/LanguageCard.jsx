import React from 'react';
import { connect } from 'react-redux';
import { Avatar, Card,Icon,Modal } from 'antd';
import { flags,history } from '../_helpers';
import { userActions } from '../_actions';
const { confirm } = Modal;

class LanguageCard extends React.Component {

    constructor(props) {
      super(props);
      this.state = {removed: false};
    }

    componentDidMount() {
    }

    learn(){
      const { isLearning,languageId,language,addLanguage,removeLanguage,getProfile,changeActiveLanguage } = this.props;
      const setState = this.setState.bind(this);
      const removed = this.state.removed;
      let title = 'Do you want to learn '+this.props.language+'?';
      let content = 'You can use the materials of '+this.props.language+".";
      let okText = 'Start';
      if (this.props.isLearning && !removed){
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
          if (isLearning && !removed){
            removeLanguage(language);
            getProfile();
            setState({
              removed: true
            });
          }
          else{
            addLanguage(language);
            changeActiveLanguage({languageName:language,id:languageId});
            history.push('/');
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
              { this.props.isLearning && !this.state.removed ?(
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
  changeActiveLanguage: userActions.changeActiveLanguage
}

const connectedLanguageCard = connect(mapState, actionCreators)(LanguageCard);
export { connectedLanguageCard as LanguageCard };