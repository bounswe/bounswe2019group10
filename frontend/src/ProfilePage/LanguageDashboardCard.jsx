import React from 'react';
import { connect } from 'react-redux';
import { Avatar, Card, Modal, Button } from 'antd';

const { confirm } = Modal;

import { flags } from '../_helpers';
import { userActions } from '../_actions';

class LanguageDashboardCard extends React.Component {

    constructor(props) {
        super(props);
    }

    learn() {
        const { language, removeLanguage, getProfile } = this.props;
        let title = 'Do you want to remove ' + this.props.language + ' from your profile?';
        let content = 'You will not be able to use the materials of ' + this.props.language + ' any more.';
        let okText = 'Remove';
        
        confirm({
            title: title,
            content: content,
            okText: okText,
            cancelText: 'Cancel',
            onOk() {
                removeLanguage(language);
                getProfile();
            },
            onCancel() {
                console.log('Cancel');
            },
        });
    }

    render() {
        const { language } = this.props;
        return (
            <div style={{ textAlign: 'center' }} hoverable={true} onClick={() => this.learn()}>
                <p>
                    <Avatar size={32} src={flags[language]["src"]} /> 
                    <div style={{marginLeft: "2px"}}>{language} </div>
                </p>
            </div>
            // <Card style={{ textAlign: 'center' }} hoverable={true} onClick={() => this.learn()}>
            //     <p>
            //         <Avatar size={32} src={flags[language]["src"]} /> 
            //         <div style={{marginLeft: "2px"}}>{language} </div>
            //     </p>
            //     <p>
            //       <Button type="primary" onClick={this.handleClickEdit} style={{ marginTop: '12px' }}>REMOVE</Button>
            //     </p>
            // </Card>
        );
    }
}

function mapState(state) {

    return {};
}

const actionCreators = {
    removeLanguage: userActions.removeLanguage,
    getProfile: userActions.getProfile,
}

const connectedLanguageCard = connect(mapState, actionCreators)(LanguageDashboardCard);
export { connectedLanguageCard as LanguageDashboardCard };