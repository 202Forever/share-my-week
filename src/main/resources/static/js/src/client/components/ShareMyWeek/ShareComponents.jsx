import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import { Grid, Row, Col, Panel, Input, Button, OverlayTrigger, Tooltip } from 'react-bootstrap';
import ClipboardButton from 'react-clipboard.js';
import { saveWeek } from '../../actions/serverActions';

const seperatorsRegEx = new RegExp([' ', ','].join('|'), "g");

// Simplistic detection, do not use it in production
function fallbackMessage(action) {
    var actionMsg = '';
    var actionKey = (action === 'cut' ? 'X' : 'C');

    if(/iPhone|iPad/i.test(navigator.userAgent)) {
        actionMsg = 'No support :(';
    }
    else if (/Mac/i.test(navigator.userAgent)) {
        actionMsg = 'Press ⌘-' + actionKey + ' to ' + action;
    }
    else {
        actionMsg = 'Press Ctrl-' + actionKey + ' to ' + action;
    }

    return actionMsg;
}

class ShareComponents extends Component {

    constructor(props, content) {
        super(props, content);
        this.state = {
            emails: '',
            copyTooltip: props.copyTooltip
        };
        this.onCopySuccess = this.onCopySuccess.bind(this);
        this.onCopyError = this.onCopyError.bind(this);
        this.onEmailsChange = this.onEmailsChange.bind(this);
        this.onInvite = this.onInvite.bind(this);
        this.resetCopyTooltip = this.resetCopyTooltip.bind(this);
    }

    onCopySuccess(e) {
        const {copyTooltipTrigger} = this.refs;
        this.setState({copyTooltip: 'Copied!'});
        copyTooltipTrigger.show();
    }

    onCopyError(e) {
        const {copyTooltipTrigger} = this.refs;
        this.setState({copyTooltip: fallbackMessage(e.action)});
        copyTooltipTrigger.show();
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

    resetCopyTooltip() {
        this.setState({copyTooltip: ShareComponents.defaultProps.copyTooltip});
    }

    render() {
        const {pathname} = this.props;
        const tooltip = (<Tooltip ref="copyBtnTooltip" id="copyBtnTooltip">{this.state.copyTooltip}</Tooltip>);
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
                            <Input id="public_url"
                                   type="text"
                                   label="Public Week URL"
                                   style={{cursor:"text"}}
                                   buttonAfter={
                                        <OverlayTrigger delay={1000}
                                            ref="copyTooltipTrigger" onExited={this.resetCopyTooltip}
                                            placement="top" overlay={tooltip} trigger="click">
                                            <ClipboardButton className="btn btn-default"
                                                onSuccess={this.onCopySuccess}
                                                onError={this.onCopyError}
                                                data-clipboard-target="#public_url">
                                                <i className="fa fa-clipboard" />
                                            </ClipboardButton>
                                        </OverlayTrigger>
                                    }
                                    readOnly={true}
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

ShareComponents.defaultProps = {
    copyTooltip: 'Copy'
};

function mapStateToProps(state = {}, props) {
    const {routing, appData} = state;
    return {
        weekData: appData.weekData,
        pathname: routing.locationBeforeTransitions.pathname,
        userId: routing.locationBeforeTransitions.query.userId
    };
}

export default connect(mapStateToProps)(ShareComponents);
