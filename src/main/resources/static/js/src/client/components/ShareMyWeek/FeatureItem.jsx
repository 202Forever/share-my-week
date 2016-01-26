import React, { Component, PropTypes } from 'react';
import _ from 'lodash';

class FeatureItem extends Component {

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
        const {item} = this.props;
        return (
            <li {...this.props} className="media">
                <a href="#" className="pull-left">
                <img src={ item.imageSrc } height={ 64 } width={ 64 } className="media-object" /></a>
                <div className="media-body">
                    <h4 className="media-heading"><span>{ item.header }</span></h4>
                    <p>
                        <span>{ item.desc }</span>
                    </p>
                </div>
            </li>
        );
    }
}

FeatureItem.defaultProps = {
    item: {
        imageSrc: 'http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png',
        header: 'Header',
        desc: 'Lorem ipsum dolor sit amet, noluisse consulatu has te, vim fastidii intellegat democritum ex, eum hinc accumsan ea. Vel summo'
    }
};

FeatureItem.propTypes = {
    item: React.PropTypes.object,
    item: React.PropTypes.shape({
        header: PropTypes.string,
        desc: PropTypes.string
    })
};

export default FeatureItem;