import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import { reduxForm } from 'redux-form';
import { Input, Button } from 'react-bootstrap';

import emailValidator from 'email-validator';
import { addWeek } from '../../actions/serverActions';
import { dispatchRoute } from '../../util/helpers';

class CreateWeekForm extends Component {

    render() {
        const {fields: {email}, placeholder, submitting, handleSubmit} = this.props;
        return (
            <form role="form" onSubmit={handleSubmit}>
                <Input {...email}
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
        const {fields: {email}, error, submitting} = this.props;
        if (submitting) {
            return 'Processing request...'
        }
        if (error) {
            return error;
        }
        if (email.touched && email.error) {
            return email.error;
        }
    }

    renderInputStyle() {
        const {fields: {email}, error, submitting} = this.props;
        if (submitting) {
            return null;
        }
        if (error) {
            return 'error';
        }
        if (email.touched && !email.error) {
            return 'success';
        }
    }
}

function validate(values) {
    const errors = {};
    if (!values.email || !emailValidator.validate(values.email)) {
        errors.email = 'Please enter an valid email address';
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
        users : [{email: data.email}]
    });
    dispatch(action);
    action.payload.then((entity) => dispatchRoute(dispatch, entity, 'self'));
    return action.payload;
}

export default reduxForm({
    form: 'createWeekForm',
    fields: ['email'],
    validate
}, mapStateToProps, mapDispatchToProps)(CreateWeekForm);