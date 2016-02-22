import React, { Component, PropTypes } from 'react';
import moment from 'moment';

class WeekTableHeader extends Component {

    render() {
        const {timestamp} = this.props;
        const sunDate = moment(timestamp).day(0);
        const monDate = moment(timestamp).day(1);
        const tueDate = moment(timestamp).day(2);
        const wedDate = moment(timestamp).day(3);
        const thurDate = moment(timestamp).day(4);
        const friDate = moment(timestamp).day(5);
        const satDate = moment(timestamp).day(6);
        return (<thead {...this.props}>
                    <tr>
                        <th />
                        <th>
                            <span>{ sunDate.format("D dddd") }</span>
                        </th>
                        <th>
                            <span>{ monDate.format("D dddd") }</span>
                        </th>
                        <th>
                            <span>{ tueDate.format("D dddd") }</span>
                        </th>
                        <th>
                            <span>{ wedDate.format("D dddd") }</span>
                        </th>
                        <th>
                            <span>{ thurDate.format("D dddd") }</span>
                        </th>
                        <th>
                            <span>{ friDate.format("D dddd") }</span>
                        </th>
                        <th>
                            <span>{ satDate.format("D dddd") }</span>
                        </th>
                    </tr>
                </thead>
            );
    }
}
WeekTableHeader.defaultProps = {
    timestamp: new Date().toISOString()
};
WeekTableHeader.propTypes = {
    timestamp: PropTypes.string
};

export default WeekTableHeader;
