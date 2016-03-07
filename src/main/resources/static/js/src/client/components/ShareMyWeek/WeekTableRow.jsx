import React, { Component, PropTypes } from 'react';
import { FormattedTime } from 'react-intl';
import WeekTableCell from './WeekTableCell.jsx';
import moment from 'moment';

function getEvents(eventsData, date) {
    const {map} = eventsData;
    if (!map) return undefined;
    return map[date];
}

class WeekTableRow extends Component {

    render() {
        const {start, end, selectColor, onCellSelect, onEventSelect, format, label, focused, weekData, colorMap} = this.props;
        const {eventsData} = weekData;
        let rowCells = [];
        for (var i = 0; i < 7; i++) {
            const startTime = moment(start).day(i);
            const endTime = moment(end).day(i);
            rowCells.push(<WeekTableCell key={i} events={getEvents(eventsData, startTime.toISOString())}
                                         start={startTime} end={endTime} focused={focused}
                                         selectColor={selectColor} colorMap={colorMap}
                                         onCellSelect={onCellSelect} onEventSelect={onEventSelect} />)
        }
        return (<tr>
                    <td>
                        {label ?
                            label
                            : <span><FormattedTime value={start} {...format} hour="numeric" /></span>
                        }
                    </td>
                    {rowCells}
                </tr>
            );
    }
}

WeekTableRow.defaultProps = {
    start: moment().hour(0).minute(0).second(0),
    end: moment().hour(0).minute(30).second(0)
};

export default WeekTableRow;
