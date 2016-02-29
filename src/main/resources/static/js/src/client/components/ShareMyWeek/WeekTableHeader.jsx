import React, { Component, PropTypes } from 'react';
import { FormattedDate } from 'react-intl';
import moment from 'moment';

class WeekTableHeader extends Component {

    render() {
        const {timestamp} = this.props;
        let headers= [];
        for (var i = 0; i < 7; i++) {
            headers.push(
                <th key={i}>
                    <FormattedDate key={i} value={moment(timestamp).day(i).toDate()} day="numeric" weekday="long" />
                </th>
            );
        }
        return (<thead {...this.props}>
                    <tr>
                        <th />
                        {headers}
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
