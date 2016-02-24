
import React, { Component, PropTypes } from 'react';

import NavBar from '../components/ShareMyWeek/NavBar.jsx';
import WeekApp from '../containers/ShareMyWeek/WeekApp.jsx';
import UserSettingsModal from '../containers/ShareMyWeek/UserSettingsModal.jsx';


class Week extends Component {

    render() {
        return (
            <div>
                <NavBar staticTop={ true } params={ this.props.params }></NavBar>
                <UserSettingsModal className="static-modal" params={ this.props.params }>
                </UserSettingsModal>
                <WeekApp fluid={ true } params={ this.props.params }></WeekApp>
            </div>
            );
    }
}

export default Week;

