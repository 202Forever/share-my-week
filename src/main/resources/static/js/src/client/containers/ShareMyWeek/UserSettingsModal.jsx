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
        const {color} = this.props;
        return (
            <Col md={1}>
                <i className="fa fa-square color-tag button" style={{color}}/>
            </Col>
        )
    }
}

class UserSettingsModal extends Component {

    constructor(props, content) {
        super(props, content);
        this.state = {
            show: true,
            username: '',
            color: ''
        };
        this.onModalHide = this.onModalHide.bind(this);
        this.onConfirmName = this.onConfirmName.bind(this);
    }

    onModalHide() {
        this.setState({show : false});
    }

    onConfirmName(username) {
        this.setState({username});
    }

    render() {
        const {userData} = this.props;
        const user = userData.entity;
        if (user) {
            let body;
            if (this.state.username) {
                body = (
                    <Modal.Body>
                        <Grid fluid={true}>
                            <row>
                                <Col>Select your color tag:</Col>
                            </row>
                            <row>
                                <Color color="yellow"/>
                                <Color color="orange"/>
                                <Color color="pink"/>
                                <Color color="red"/>
                                <Color color="green"/>
                                <Color color="blue"/>
                                <Color color="purple"/>
                                <Color color="brown"/>
                            </row>
                        </Grid>
                    </Modal.Body>
                );
            }
            return (
                <Modal className="user-settings-dialog" show={this.state.show && (!user.color || !user.name)}>
                    <Modal.Header closeButton={true} onHide={this.onModalHide}>
                        <Grid fluid={true}>
                            <row>
                                <Col><h2 className="modal-title">Welcome</h2></Col>
                            </row>
                            <Username username={this.state.username} onConfirmName={this.onConfirmName} />
                        </Grid>
                    </Modal.Header>
                    {body}
                </Modal>
            );
        }
        return (<span/>);
    }
}

function mapStateToProps(state) {
    var {userData} = state.appData;
    return {userData};
}

export default connect(mapStateToProps)(UserSettingsModal);
