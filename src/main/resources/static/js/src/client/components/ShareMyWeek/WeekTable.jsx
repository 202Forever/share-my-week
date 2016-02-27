import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import { Table } from 'react-bootstrap';
import WeekTableHeader from './WeekTableHeader.jsx';
import WeekHourRow from './WeekHourRow.jsx';
import WeekHalfHourRow from './WeekHalfHourRow.jsx';

class WeekTable extends Component {

    render() {
        const {hours, weekData, timestamp} = this.props;
        let content = [];
        for (var key in hours) {
            content.push(<WeekHourRow key={ key + '_full' } hour={ hours[key] } />);
            content.push(<WeekHalfHourRow key = { key + '_half' } />);
        }
        return (<div>
                    <Table {...this.props} condensed hover={false}>
                        <WeekTableHeader timestamp={ timestamp } />
                        <tbody>{ content }</tbody>
                    </Table>
                </div>);
    }
}

WeekTable.defaultProps = {
    hours : {
        '12am': "12 AM",
        '1am': "1 AM",
        '2am': "2 AM",
        '3am': "3 AM",
        '4am': "4 AM",
        '5am': "5 AM",
        '6am': "6 AM",
        '7am': "7 AM",
        '8am': "8 AM",
        '9am': "9 AM",
        '10am': "10 AM",
        '11am': "11 AM",
        '12pm': "12 PM",
        '1pm': "1 PM",
        '2pm': "2 PM",
        '3pm': "3 PM",
        '4pm': "4 PM",
        '5pm': "5 PM",
        '6pm': "6 PM",
        '7pm': "7 PM",
        '8pm': "8 PM",
        '9pm': "9 PM",
        '10pm': "10 PM",
        '11pm': "11 PM",
    }
};

export default WeekTable;
