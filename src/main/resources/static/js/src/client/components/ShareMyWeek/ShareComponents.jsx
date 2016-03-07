import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import { Grid, Row, Col, Panel, Input, Button } from 'react-bootstrap';
import ClipboardButton from 'react-clipboard.js';
import { saveWeek } from '../../actions/serverActions';

const seperatorsRegEx = new RegExp([' ', ','].join('|'), "g");

class ShareComponents extends Component {

    constructor(props, content) {
        super(props, content);
        this.state = {
            emails: ''
        };
        this.onEmailsChange = this.onEmailsChange.bind(this);
        this.onInvite = this.onInvite.bind(this);
    }

    onEmailsChange(e) {
        this.setState({emails: e.target.value});
    }

    onInvite() {
        const {dispatch, userId, weekData} = this.props;
        const users = this.state.emails.split(seperatorsRegEx).map((email) => {
           return {userInfo: {email}}
        });
        const entity = Object.assign({}, weekData.entity, {
            users: [...weekData.entity.users,...users]
        });
        const action = saveWeek(entity, userId);
        action.payload.then(() => this.setState({emails: ''}));
        dispatch(action);
    }

    render() {
        const {pathname} = this.props;
        return (<Grid
                      {...this.props}
                      fluid={ true }>
                    <Row>
                        <Col sm={4} md={6} xsHidden={true} />
                        <Col
                             xs={ 12 }
                             md={ 3 }
                             sm={ 4 }>
                        <Panel>
                            <Input
                                   type="text"
                                   label="Public Week URL"
                                   disabled={ true }
                                   style={{cursor:"text"}}
                                   buttonAfter={<ClipboardButton className="btn btn-default" data-clipboard-text={location.origin + pathname}><i className="fa fa-clipboard" /></ClipboardButton>}
                                   value={location.origin + pathname} />
                        </Panel>
                        </Col>
                        <Col
                             xs={ 12 }
                             md={ 3 }
                             sm={ 4 }>
                        <Panel>
                            <Input
                                   type="text"
                                   label="Emails"
                                   ref="emailsInput"
                                   placeholder="joe@yourmail.com devin@yourmail.com kate@yourmail.com"
                                   value={this.state.emails}
                                   onChange={this.onEmailsChange}
                                   buttonAfter={<Button onClick={this.onInvite}><i className="fa fa-envelope" /></Button>} />
                        </Panel>
                        </Col>
                    </Row>
                </Grid>
            );
    }
}

function mapStateToProps(state = {}, props) {
    const {routing, appData} = state;
    return {
        weekData: appData.weekData,
        pathname: routing.locationBeforeTransitions.pathname,
        userId: routing.locationBeforeTransitions.query.userId
    };
}

export default connect(mapStateToProps)(ShareComponents);
