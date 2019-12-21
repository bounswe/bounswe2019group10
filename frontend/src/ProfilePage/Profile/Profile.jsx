import React from 'react';

import { Descriptions, Input, Col, Row, Button } from 'antd';
import { Avatar } from 'react-avatar-edit';
import 'antd//dist/antd.css';

class Profile extends React.Component {
    constructor(props) {
        super(props);

        this.handleChange = this.handleChange.bind(this);
        this.handleEditButton = this.handleEditButton.bind(this);
        this.handleDoneButton = this.handleDoneButton.bind(this);
        this.onCrop = this.onCrop.bind(this)
        this.onClose = this.onClose.bind(this)

        this.state = {
            //true if the user is looking at his own profile page (not another user)
            selfPage: this.props.selfPage,
            isHidden: this.props.isHidden,

            isEditing: false,
            name: this.props.name,
            surname: this.props.surname,
            username: this.props.username,
            mail: this.props.mail,
            bio: this.props.bio,
            nativeLanguage: this.props.nativeLanguage,

            preview: null,
            src: ''
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

    handleHideButton() {

    }

    onClose() {
        this.setState({ preview: null })
    }

    onCrop(preview) {
        this.setState({ preview })
    }

    render() {
        const { mail, username, bio, name, surname, nativeLanguage } = this.state;

        return (
            this.state.selfPage ?
                <div style={{ background: '#fff', padding: 24 }}>
                    <Descriptions title={username ? username : "No username provided yet!"} bordered={true} column={1}>
                        {/* <Descriptions.Item label="Picture">
                            <Col span={10} offset={1}>
                                <Avatar
                                    width={200}
                                    height={200}
                                    imageWidth={200}
                                    cropRadius={100}
                                    minCropRadius={50}
                                    onCrop={this.onCrop}
                                    onClose={this.onClose}
                                    src=''
                                />
                            </Col>
                            <Col span={2} offset={9}>
                                <Button type="default"
                                    style={{ marginTop: '5px' }}>
                                    EDIT
                                 </Button>
                            </Col>
                        </Descriptions.Item> */}
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
                                        defaultValue={bio ? bio : "bio"} name="bio" onChange={this.handleChange} />
                                    : bio ? bio : "No bio provided yet!"
                            }
                        </Descriptions.Item>
                        <Descriptions.Item label="Native Language">
                            {
                                this.state.isEditing ?
                                    <Input placeholder={nativeLanguage ? nativeLanguage : "No language provided yet!"}
                                        defaultValue={nativeLanguage ? nativeLanguage : "nativeLanguage"} onChange={this.handleChange} />
                                    : nativeLanguage ? nativeLanguage : "No language provided yet!"
                            }
                        </Descriptions.Item>
                    </Descriptions>
                    <Row style={{ marginTop: '12px' }}>
                        <Col span={3} offset={14}>
                            <Button type="default"
                                onClick={this.state.isEditing ? this.handleDoneButton : this.handleEditButton}>
                                {this.state.isEditing ? "DONE" : "EDIT PROFILE"}
                            </Button>
                        </Col>
                        <Col span={3} offset={2}>
                            <Button type="default" onClick={this.handleHideButton}>HIDE PROFILE</Button>
                        </Col>
                    </Row>
                </div>
                :
                this.state.isHidden ?
                    <div style={{ background: '#fff', padding: 24 }}>
                        THIS PROFILE IS HIDDEN AND THIS PAGE WILL BE EDITED LATER ! PEACE !
                    </div>
                    :
                    <div style={{ background: '#fff', padding: 24 }}>
                        <Descriptions title={username ? username : "No username provided yet!"}
                            style={{ background: 'yellow' }}>
                            <Descriptions.Item label="Picture">
                                <Col span={10} offset={1}>
                                    <Avatar
                                        width={200}
                                        height={200}
                                        imageWidth={200}
                                        cropRadius={100}
                                        minCropRadius={50}
                                        onCrop={this.onCrop}
                                        onClose={this.onClose}
                                        src=''
                                    />
                                </Col>
                            </Descriptions.Item>
                            <Descriptions.Item label="Name"> {name ? name : "No name provided yet!"} </Descriptions.Item>
                            <Descriptions.Item label="Surname"> {surname ? surname : "No surname provided yet!"} </Descriptions.Item>
                            <Descriptions.Item label="User Name"> {username ? username : "No username provided yet!"} </Descriptions.Item>
                            <Descriptions.Item label="E-mail"> {mail ? mail : "No mail provided yet!"} </Descriptions.Item>
                            <Descriptions.Item label="Bio"> {bio ? bio : "No bio provided yet!"} </Descriptions.Item>
                            <Descriptions.Item label="Native Language"> {nativeLanguage ? nativeLanguage : "No language provided yet!"} </Descriptions.Item>
                        </Descriptions>
                    </div>
        )
    }
}

export { Profile };