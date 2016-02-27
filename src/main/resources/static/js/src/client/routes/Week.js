
import React, { Component, PropTypes } from 'react';

import NavBar from '../components/ShareMyWeek/NavBar.jsx';
import WeekApp from '../containers/ShareMyWeek/WeekApp.jsx';


class Week extends Component {

    render() {
        return (
            <div>
                <NavBar staticTop={ true } params={ this.props.params }></NavBar>
                <WeekApp fluid={ true } params={ this.props.params }></WeekApp>
            </div>
            );
    }
}

export default Week;

