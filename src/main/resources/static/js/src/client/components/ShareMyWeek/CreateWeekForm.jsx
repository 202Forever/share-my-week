import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import { reduxForm } from 'redux-form';
import { Input, Button } from 'react-bootstrap';

import emailValidator from 'email-validator';
import { addWeek } from '../../actions/serverActions';
import { dispatchRoute } from '../../util/helpers';

class CreateWeekForm extends Component {

    render() {
        const {fields: {emails}, placeholder, submitting, handleSubmit} = this.props;
        return (
            <form role="form" onSubmit={handleSubmit}>
                <Input {...emails}
                    buttonAfter={
                        <Button
                        type="submit"
                        bsStyle="success"
                        disabled={submitting}>
                            {submitting ?
                                <i className="fa fa-cog fa-spin fa-lg" /> :
                                <span>Go</span>}
                        </Button>
                    }
                    disabled={submitting}
                    placeholder={placeholder}
                    help={this.renderHelpTooltip()}
                    bsStyle={this.renderInputStyle()}
                    type="text" />
            </form>
        );
    }

    renderHelpTooltip() {
        const {fields: {emails}, error, submitting} = this.props;
        if (submitting) {
            return 'Processing request...'
        }
        if (error) {
            return error;
        }
        if (emails.touched && emails.error) {
            return emails.error;
        }
    }

    renderInputStyle() {
        const {fields: {emails}, error, submitting} = this.props;
        if (submitting) {
            return null;
        }
        if (error) {
            return 'error';
        }
        if (emails.touched && !emails.error) {
            return 'success';
        }
    }
}

const seperatorsRegEx = new RegExp([' ', ','].join('|'), "g");

function validate(values) {
    const errors = {};
    if (!values.emails) {
        errors.emails = 'Please enter an valid email address';
    } else if(values.emails.split(seperatorsRegEx).find(function (email) {
            email = email.trim();
            return email && !emailValidator.validate(email);
        })) {
        errors.emails = 'One or more email addresses are invalid';
    }
    return errors;
}

function mapStateToProps(state) {
    return state.form.createWeekForm;
}

function mapDispatchToProps(dispatch) {
    return {
        onSubmit: (data) => {return addWeekAndDispatch(data, dispatch)}
    };
}

function addWeekAndDispatch(data, dispatch) {
    var action = addWeek({
        users : data.emails.split(seperatorsRegEx).map(function (email) {
            return {email};
        })
    });
    dispatch(action);
    action.payload.then((entity) => dispatchRoute(dispatch, entity, 'self'));
    return action.payload;
}

export default reduxForm({
    form: 'createWeekForm',
    fields: ['emails'],
    validate
}, mapStateToProps, mapDispatchToProps)(CreateWeekForm);