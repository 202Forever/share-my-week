import React, { Component, PropTypes } from 'react';
import { Table } from 'react-bootstrap';
import WeekTableHeader from './WeekTableHeader.jsx';
import WeekTableRow from './WeekTableRow.jsx';
import moment from 'moment';

class WeekTable extends Component {

    render() {
        const {hours, timestamp} = this.props;
        let content = [];
        Object.keys(hours).forEach((hour) => {
            const value = hours[hour];
            const start = moment(timestamp).hour(value).minute(0);
            const mid = moment(timestamp).hour(value).minute(30);
            const end = moment(timestamp).hour(value + 1).minute(0);
            content.push(<WeekTableRow key={ hour + '_start' } start={start} end={mid} {...this.props} format={{hour : '2-digit'}} />);
            content.push(<WeekTableRow key={ hour + '_half' } start={mid} end={end} {...this.props} label={<span/>} />);
        });
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
        '12am': 0,
        '1am': 1,
        '2am': 2,
        '3am': 3,
        '4am': 4,
        '5am': 5,
        '6am': 6,
        '7am': 7,
        '8am': 8,
        '9am': 9,
        '10am': 10,
        '11am': 11,
        '12pm': 12,
        '1pm': 13,
        '2pm': 14,
        '3pm': 15,
        '4pm': 16,
        '5pm': 17,
        '6pm': 18,
        '7pm': 19,
        '8pm': 20,
        '9pm': 21,
        '10pm': 22,
        '11pm': 23
    }
};

export default WeekTable;
