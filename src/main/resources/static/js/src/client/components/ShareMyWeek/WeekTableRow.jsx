import React, { Component, PropTypes } from 'react';
import { FormattedTime } from 'react-intl';
import WeekTableCell from './WeekTableCell.jsx';
import moment from 'moment';

class WeekTableRow extends Component {

    render() {
        const {start, end, selectColor, onCellSelect, format, label} = this.props;
        let rowCells = [];
        for (var i = 0; i < 7; i++) {
            rowCells.push(<WeekTableCell key={i} start={moment(start).day(i)} end={moment(end).day(i)} selectColor={selectColor} onCellSelect={onCellSelect} />)
        }
        return (<tr>
                    <td>
                        {label ?
                            label
                            : <span><FormattedTime value={start} {...format} /></span>
                        }
                    </td>
                    {rowCells}
                </tr>
            );
    }
}

WeekTableRow.defaultProps = {
    start: moment().hour(0)
};

export default WeekTableRow;
