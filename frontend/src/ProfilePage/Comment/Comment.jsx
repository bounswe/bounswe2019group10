import React from 'react';

import { Button, Card, Row, Col, Input, Menu, Dropdown, Icon } from 'antd';
import 'antd//dist/antd.css';

class Comment extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const { comments } = this.props;
        const height = 200;
        return (
            <div style={{ background: '#fff', padding: 24, minHeight: height }}>
                <Card title="Comments" bodyStyle={{ overflow: "scroll", height: "500px" }}>
                    {
                        comments && comments.map((comment, i) => {
                            return (
                                <Card title={comment.commentatorName}
                                    extra={<Button>{<Icon type="star" />}{comment.rating}</Button>}
                                    key={i} style={{ marginBottom: '24px' }}>
                                    {comment.comment}
                                </Card>
                            )
                        })
                    }
                </Card>
            </div>
        )
    }
}

export { Comment };