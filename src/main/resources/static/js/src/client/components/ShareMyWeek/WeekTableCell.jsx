import React, { Component } from 'react';

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
        return (
            <td onClick={this.onCellSelect}>
                <div className={this.state.selected ? 'selected' : ''} style={this.getStyle()}></div>
            </td>
        );
    }
}

export default WeekTableCell;