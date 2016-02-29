import React, { Component, PropTypes } from 'react';
import { Modal, Button, Input, Grid, Row, Col } from 'react-bootstrap';
import { connect } from 'react-redux';

class Username extends Component {

    render() {
        const {username, onConfirmName} = this.props;
        if (username) {
            return (
                <row>
                    <Col><h2>{username}!</h2></Col>
                </row>
            );
        } else {
            return (
                <row>
                    <Col md={1} sm={1} xs={1}/>
                    <Col md={6} sm={6} xs={8}>
                        <Input
                            ref="inputText"
                            buttonAfter={
                                <Button onClick={() => {
                                    const {inputText} = this.refs;
                                    onConfirmName(inputText.getValue())
                                }} type="button">
                                    <i className="fa fa-play fa-lg" />
                                </Button>
                            }
                            placeholder="Enter your name"
                            type="text"/>
                    </Col>
                    <Col md={4} sm={4} xsHidden={true}/>
                </row>
            );
        }
    }
}

class Color extends Component {

    render() {
        const {color, selected, onColorSelect} = this.props;
        return (
            <Col md={1} sm={1} xs={1}>
                <i className="fa fa-square color-tag button" selected={selected} onClick={() => onColorSelect(color)} style={{color}}/>
            </Col>
        )
    }
}

class UserSettingsModal extends Component {

    constructor(props, content) {
        super(props, content);
        this.state = {
            show: true,
            update: false,
            name: '',
            color: ''
        };
        this.onModalHide = this.onModalHide.bind(this);
        this.onConfirmName = this.onConfirmName.bind(this);
        this.onColorSelect = this.onColorSelect.bind(this);
    }

    shouldComponentUpdate(nextProps, nextState) {
        if (nextState.update) {
            const {onUserUpdate} = this.props;
            onUserUpdate(this.getSettings(nextState));
            this.setState({update: false});
            return false;
        }
        return true;
    }

    onModalHide() {
        this.setState({show : false});
    }

    onConfirmName(username) {
        this.setState({name: username});
    }

    onColorSelect(color) {
        this.setState({color, update: true});
    }

    getSettings(nextState) {
        const state = nextState ? nextState : this.state;
        const {user} = this.props;
        return {
            name : state.name || user.name,
            color: state.color || user.color
        };
    }

    render() {
        const {user, colors} = this.props;
        if (user) {
            const show = this.state.show;
            const settings = this.getSettings();
            let body;
            if (settings.name) {
                const colorSelection = colors.map((color) => {
                    return (<Color key={color} color={color} selected={settings.color === color} onColorSelect={this.onColorSelect}/>);
                });
                body = (
                    <Modal.Body>
                        <Grid fluid={true}>
                            <row>
                                <Col>Select your color tag:</Col>
                            </row>
                            <row>{colorSelection}</row>
                        </Grid>
                    </Modal.Body>
                );
            }
            if (!user.name || !user.color) {
                return (
                    <Modal dialogClassName="user-settings-dialog" show={show}>
                        <Modal.Header closeButton={true} onHide={this.onModalHide}>
                            <Grid fluid={true}>
                                <row>
                                    <Col><h2 className="modal-title">Welcome</h2></Col>
                                </row>
                                <Username username={settings.name} onConfirmName={this.onConfirmName}/>
                            </Grid>
                        </Modal.Header>
                        {body}
                    </Modal>
                );
            }
        }
        return (<span/>);
    }
}

export default UserSettingsModal;
