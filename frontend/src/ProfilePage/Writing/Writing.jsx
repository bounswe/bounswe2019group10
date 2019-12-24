import React from 'react';

import { Button, Card, Modal, Row, Col, Input, Menu, Dropdown, Icon } from 'antd';
import 'antd//dist/antd.css';

class Writing extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            visible: false,
            modalKey: null
        };
    }

    showModal = (e, key) => {
        this.setState({
            visible: true,
            modalKey: key 
        });
    };

    handleOk = e => {
        console.log(e);
        this.setState({
            visible: false,
        });
    };

    render() {
        const { writings } = this.props;
        const height = 200;

        return (
            <div style={{ background: '#fff', padding: 24, minHeight: height }}>
                <Card title="Writings" bodyStyle={{ overflow: "scroll", height: "500px" }}>
                    {
                        writings && writings.map((writing, i) => {
                            return (
                                <Card title={writing.writingName}
                                    extra={<p>Score:{writing.score}</p>}
                                    style={{ marginBottom: "24px" }} key={i}>
                                    <Button type="link" key={i} onClick={(e) => this.showModal(e, i)}>
                                        See Your Answer
                                    </Button>
                                    <Modal
                                        title={writing.writingName}
                                        visible={this.state.visible && this.state.modalKey === i}
                                        onOk={this.handleOk}
                                        cancelButtonProps={{ disabled: true }}
                                        style={{maxWidth: "600px"}}
                                    >
                                        {writing.answerText ? writing.answerText : 
                                        <img alt="Can not upload image!" src={writing.imageUrl} />}
                                    </Modal>
                                </Card>
                            )
                        })
                    }
                </Card>
            </div>
        )
    }
}

export { Writing };