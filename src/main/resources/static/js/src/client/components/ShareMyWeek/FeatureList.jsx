import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import FeatureItem from './FeatureItem.jsx';

class FeatureList extends Component {

    constructor(props, content) {
        super(props, content);
        this.handleOnClick = this.handleOnClick.bind(this);
        this.handleOnChange = this.handleOnChange.bind(this);
    }

    handleOnClick(e) {
        e.preventDefault();
        e.stopPropagation();
    }

    handleOnChange() {
    }

    render() {
        const {items} = this.props;
        let itemList = [];
        if (items && items.length > 0) {
            itemList = items.map((item) => {
                return (<FeatureItem item={item}/>);
            })
        } else {
            itemList.push((<FeatureItem />));
        }
        return (
            <ul className="media-list">
                {itemList}
            </ul>
        );
    }
}

function mapStateToProps(state) {
    const {featureListData: {items}} = state;
    return {
        items
    };
}

export default connect(mapStateToProps)(FeatureList);
