
import React, { Component, PropTypes } from 'react';

import { Grid } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import NavBar from '../components/ShareMyWeek/NavBar.jsx';
import WeekApp from '../containers/ShareMyWeek/WeekApp.jsx';
import ShareComponents from '../components/ShareMyWeek/ShareComponents.jsx';


class Week extends Component {

    render() {
        return (
            <div>
                <NavBar staticTop={ true } params={ this.props.params }></NavBar>
                <WeekApp fluid={ true } params={ this.props.params }></WeekApp>
                <ShareComponents params={ this.props.params }></ShareComponents>
                <Grid fluid={ true } params={ this.props.params }>
                    <Row params={ this.props.params }></Row>
                </Grid>
            </div>
            );
    }
}

export default Week;

