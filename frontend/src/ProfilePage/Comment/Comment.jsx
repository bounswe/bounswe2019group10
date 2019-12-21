import React from 'react';

import { Button, Card, Row, Col, Input, Menu, Dropdown, Icon } from 'antd';
import 'antd//dist/antd.css';

class Comment extends React.Component {
    constructor(props) {
        super(props);
        this.handleDeleteButton = this.handleDeleteButton.bind(this);
        this.handleCommentButton = this.handleCommentButton.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);

        this.state = {
            selfPage: this.props.selfPage,
            anyComment: this.props.anyComment,

            isCommenting: false,
            usersComment: ''
        }
    }

    handleDeleteButton(commentId) {
        this.props.deleteComment(commentId)
    }

    handleCommentButton() {
        this.state.isCommenting ? 
        this.setState({ isCommenting: false }) 
        : 
        this.setState({ isCommenting: true });
    }

    handleInputChange(e) {
        const { value } = e.target;
        this.setState({ usersComment: value })
    }

    handleMenuClick(e) {

    }

    render() {
        const { comments } = this.props;

        const menu = (
            <Menu onClick={this.handleMenuClick}>
                <Menu.Item key="1">
                    <Icon type="star" /> 1
                </Menu.Item>
                <Menu.Item key="2">
                    <Icon type="star" /> 2
                </Menu.Item>
                <Menu.Item key="3">
                    <Icon type="star" /> 3
                </Menu.Item>
                <Menu.Item key="4">
                    <Icon type="star" /> 4
                </Menu.Item>
                <Menu.Item key="5">
                    <Icon type="star" /> 5
                </Menu.Item>
            </Menu>
        );

        return (
            this.state.selfPage ?
                this.state.anyComment &&
                <div style={{ background: '#fff', padding: 24, minHeight: 280, width: 600 }}>
                    <Card title="Comments">
                        {
                            comments && comments.map((comment, i) => {
                                return (
                                    <Card title={comment.commentatorName}
                                        extra={<Button type="default" onClick={this.handleDeleteButton(comment.id)}> DELETE </Button>}
                                        key={i}>
                                        {comment.comment}
                                    </Card>
                                )
                            })
                        }
                    </Card>
                </div>
                :
                this.state.anyComment ?
                    <div style={{ background: '#fff', padding: 24, minHeight: 280, width: 600 }}>
                        <Card title="Comments"
                            extra={<Button type="default" onClick={this.handleCommentButton}>
                                {this.state.isCommenting ? "DONE" : "COMMENT"}
                            </Button>}>
                            {
                                this.state.isCommenting ?
                                    <Row>
                                        <Col span={20}>
                                            <Input placeholder="Your comment goes here..." onChange={this.handleInputChange} />
                                        </Col>
                                        <Col span={2}>
                                            <Dropdown overlay={menu}>
                                                <Button>
                                                    Rating <Icon type="down" />
                                                </Button>
                                            </Dropdown>
                                        </Col>
                                    </Row>
                                    :
                                    comments && comments.map((comment, i) => {
                                        return (
                                            <Card title={comment.commentatorName} key={i}> {comment.comment} </Card>
                                        )
                                    })
                            }
                        </Card>
                    </div>
                    :
                    <div style={{ background: '#fff', padding: 24, minHeight: 280, width: 600 }}>
                        <Row>
                            <Col span={20}>
                                <Input placeholder="Your comment goes here..." onChange={this.handleInputChange} />
                            </Col>
                            <Col span={2}>
                                <Dropdown overlay={menu}>
                                    <Button>
                                        Rating <Icon type="down" />
                                    </Button>
                                </Dropdown>
                            </Col>
                        </Row>
                    </div>

        )
    }
}

export { Comment };