import React from 'react';

import { Button, Card, Row, Col, Input } from 'antd';
import 'antd//dist/antd.css';

class Comment extends React.Component {
    constructor(props) {
        super(props);
        this.handleDeleteButton = this.handleDeleteButton.bind(this);
        this.handleCommentButton = this.handleCommentButton.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);

        this.state = {
            selfPage: this.props,
            isHidden: this.props,

            isCommenting: false,
            usersComment: ''
        }
    }

    handleDeleteButton(commentId) {
        this.props.deleteComment(commentId)
    }

    handleCommentButton() {
        this.setState({isCommenting: true})
    }

    handleInputChange(e) {
        const { value } = e.target;
        this.setState({usersComment: value})
    }

    render() {
        const { comments } = this.props;
        return (
            this.state.selfPage ?
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
                <div style={{ background: '#fff', padding: 24, minHeight: 280, width: 600 }}>
                    <Card title="Comments"
                        extra={<Button type="default" onClick={this.handleCommentButton}>
                            {this.state.isCommenting ? "DONE" : "COMMENT"}
                        </Button>}>
                        {
                            this.state.isCommenting ?
                                <Row>
                                    <Col>
                                        <Input placeholder="Your comment goes here..." onChange={this.handleInputChange}/>
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

        )
    }
}

export { Comment };