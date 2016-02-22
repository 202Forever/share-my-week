import React, { Component, PropTypes } from 'react';

class WeekHourRow extends Component {

    constructor(props, content) {
        super(props, content);
    }

    render() {
        const {hour} = this.props;
        return (<tr {...this.props}>
                    <td>
                        <span>{ hour }</span>
                    </td>
                    <td />
                    <td />
                    <td />
                    <td />
                    <td />
                    <td />
                    <td />
                </tr>
            );
    }
}
WeekHourRow.defaultProps = {
    hour: '12 AM'
};
WeekHourRow.propTypes = {
    hour: PropTypes.string
};

export default WeekHourRow;
