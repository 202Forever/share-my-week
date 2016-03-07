import React, { Component } from 'react';

function getOwnerColor(colorMap, owner) {
    return Object.keys(colorMap).find((color) => {
        const user = colorMap[color];
        if (user) {
            return user.userInfo.email === owner.email;
        }
        return false;
    });
}

class Event extends Component {

    render () {
        const {style, event, onClick} = this.props;
        return (<div className="tag" style={style} onClick={() => onClick(event)}>{event.title}</div>);
    }
}

Event.defaultProps = {
    style: {}
};

class WeekTableCell extends Component {

    constructor(props, content) {
        super(props, content);
        this.state = {selected: false};
        this.onCellSelect = this.onCellSelect.bind(this);
    }

    componentWillReceiveProps (props) {
        if (!props.focused && this.state.selected) {
            this.setState({selected: false});
        }
    }

    onCellSelect() {
        const {onCellSelect, start, end} = this.props;
        onCellSelect(start, end);
        this.setState({selected: true});
    }

    getStyle() {
        const {selectColor} = this.props;
        if (this.state.selected && selectColor) {
            return {"backgroundColor": selectColor};
        }
        return {};
    }

    render() {
        const {events, colorMap, onEventSelect} = this.props;
        const tags = events.map((event, index) => {
            return (<Event key={index} event={event} onClick={onEventSelect} style={{
                width: event.priority === 1 ? '100%' : ((100 / events.length) + '%'),
                height: (new Date(event.dateTimeRange.end) - new Date(event.dateTimeRange.start)) / 1800000 * 100 + '%',
                left: 100 - (100 / (index + 1)) + '%',
                color: event.priority === 1 ? 'black' : null,
                backgroundColor: event.priority === 1 ? 'white' : getOwnerColor(colorMap, event.owner),
                zIndex: event.priority === 1 ? 2 : 1
            }} />);
        });
        return (
            <td>
                <div onClick={this.onCellSelect} className={this.state.selected ? 'selected' : ''} style={this.getStyle()} />
                {tags}
            </td>
        );
    }
}

WeekTableCell.defaultProps = {
    events: []
};

export default WeekTableCell;