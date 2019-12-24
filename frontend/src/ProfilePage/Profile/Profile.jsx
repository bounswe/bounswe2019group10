import React from 'react';

import { Descriptions, Input, Col, Row, Button } from 'antd';
import { Avatar } from 'react-avatar-edit';
import 'antd//dist/antd.css';

class Profile extends React.Component {
    constructor(props) {
        super(props);
        console.log("WHATSUP:", props)

        this.handleChange = this.handleChange.bind(this);
        this.handleEditButton = this.handleEditButton.bind(this);
        this.handleDoneButton = this.handleDoneButton.bind(this);

        this.state = {
            isEditing: false,
            name: this.props.name,
            surname: this.props.surname,
            username: this.props.username,
            mail: this.props.mail,
            bio: this.props.bio,
            nativeLanguage: this.props.nativeLanguage,
        }
    }

    handleChange(e) {
        const { name, value } = e.target;
        this.setState({ [name]: value });
    }

    handleEditButton() {
        this.setState({ isEditing: true });
    }

    handleDoneButton() {
        const newProfile = {
            bio: this.state.bio,
            name: this.state.name,
            surname: this.state.surname,
            username: this.state.username,
            mail: this.state.mail,
            nativeLanguage: this.state.nativeLanguage
        };
        this.props.updateProfile(newProfile);
        this.setState({ isEditing: false });
    }

    render() {
        const { mail, username, bio, name, surname, nativeLanguage } = this.state;

        return (
            <div style={{ background: '#fff', padding: 24 }}>
                <Descriptions title={username ? username : "No username provided yet!"}
                    bordered={true} column={1} style={{ height: "450px" }}>
                    <Descriptions.Item label="Name">
                        {
                            this.state.isEditing ?
                                <Input placeholder={name ? name : "No name provided yet!"}
                                    defaultValue={name} name="name" onChange={this.handleChange} />
                                : name ? name : "No name provided yet!"
                        }
                    </Descriptions.Item>
                    <Descriptions.Item label="Surname">
                        {
                            this.state.isEditing ?
                                <Input placeholder={surname ? surname : "No surname provided yet!"}
                                    defaultValue={surname} name="surname" onChange={this.handleChange} />
                                : surname ? surname : "No surname provided yet!"
                        }
                    </Descriptions.Item>
                    <Descriptions.Item label="User Name">
                        {
                            this.state.isEditing ?
                                <Input placeholder={username ? username : "No username provided yet!"}
                                    defaultValue={username} name="username" onChange={this.handleChange} />
                                : username ? username : "No username provided yet!"
                        }
                    </Descriptions.Item>
                    <Descriptions.Item label="E-mail">
                        {
                            this.state.isEditing ?
                                <Input placeholder={mail ? mail : "No mail provided yet!"}
                                    defaultValue={mail} name="mail" onChange={this.handleChange} />
                                : mail ? mail : "No mail provided yet!"
                        }
                    </Descriptions.Item>
                    <Descriptions.Item label="Bio">
                        {
                            this.state.isEditing ?
                                <Input placeholder={bio ? bio : "No bio provided yet!"}
                                    defaultValue={bio ?? bio} name="bio" onChange={this.handleChange} />
                                : bio ? bio : "No bio provided yet!"
                        }
                    </Descriptions.Item>
                    <Descriptions.Item label="Native Language">
                        {
                            this.state.isEditing ?
                                <Input placeholder={nativeLanguage ? nativeLanguage : "No language provided yet!"}
                                    defaultValue={nativeLanguage ?? nativeLanguage} name="nativeLanguage" onChange={this.handleChange} />
                                : nativeLanguage ? nativeLanguage : "No language provided yet!"
                        }
                    </Descriptions.Item>
                </Descriptions>
                <Row style={{ marginTop: '12px' }}>
                    <Col span={3} offset={19}>
                        <Button type="default"
                            onClick={this.state.isEditing ? this.handleDoneButton : this.handleEditButton}>
                            {this.state.isEditing ? "DONE" : "EDIT PROFILE"}
                        </Button>
                    </Col>
                </Row>
            </div>
        )
    }
}

export { Profile };