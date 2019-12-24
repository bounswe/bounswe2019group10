import React from 'react';

import { Button, Progress, Card } from 'antd';
import 'antd//dist/antd.css';

class Language extends React.Component {
    constructor(props) {
        super(props);
        this.handleRemoveButton = this.handleRemoveButton.bind(this);

        this.state = {
            selfPage: this.props.selfPage,
            isHidden: this.props.isHidden
        }
    }

    handleRemoveButton(language) {
        this.props.removeLanguage(language)
    }

    render() {
        const { memberLanguages } = this.props;
        return (
            this.state.selfPage ?
                <Card title="Languages" bodyStyle={{ overflow: "scroll", height: "500px" }}>
                    {
                        memberLanguages && memberLanguages.map((memberLanguage, i) => {
                            return (
                                <Card title={memberLanguage.language.languageName}
                                    extra={<Button type="default" onClick={() => this.handleRemoveButton(memberLanguage.language.languageName)}>Remove</Button>}
                                    style={{ minHeight: '200px', marginBottom: '24px' }} key={i}>
                                    <p>Level: {memberLanguage.levelName ? memberLanguage.levelName : "No level provided yet!"}</p>
                                    <Progress type="circle" percent={75} />
                                </Card>
                            )
                        })

                    }
                </Card>
                :
                this.state.isHidden ?
                    <Card title="Languages" bodyStyle={{ overflow: "scroll", height: "500px" }}>
                        {
                            memberLanguages && memberLanguages.map((memberLanguage, i) => {
                                return (
                                    <Card title={memberLanguage.language.languageName}
                                        style={{ minHeight: '100px', marginBottom: '24px' }} key={i}>
                                        <p>Level: {memberLanguage.levelName ? memberLanguage.levelName : "No level provided yet!"}</p>
                                    </Card>
                                )
                            })
                        }
                    </Card>
                    :
                    <Card title="Languages" bodyStyle={{ overflow: "scroll", height: "500px" }}>
                        {
                            memberLanguages && memberLanguages.map((memberLanguage, i) => {
                                return (
                                    <Card title={memberLanguage.language.languageName}
                                        style={{ minHeight: '200px', marginBottom: '24px' }} key={i}>
                                        <p>Level: {memberLanguage.levelName ? memberLanguage.levelName : "No level provided yet!"}</p>
                                        <Progress type="circle" percent={75} />
                                    </Card>
                                )
                            })
                        }
                    </Card>
        )
    }
}

export { Language };