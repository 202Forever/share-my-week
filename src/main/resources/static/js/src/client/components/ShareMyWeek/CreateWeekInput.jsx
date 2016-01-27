import React, { Component, PropTypes } from 'react';
import { Input, Button } from 'react-bootstrap';

class CreateWeekInput extends Component {

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
        const innerButton = <Button bsStyle="success">Go</Button>;
        return (<form
                      {...this.props}
                      role="form">
                    <Input
                           hasFeedback={ true }
                           placeholder="joe@yourmail.com sally@yourmail.com devin@yourmail.com"
                           type="text" 
                           buttonAfter={innerButton} />
                </form>
            );
    }
}

export default CreateWeekInput;
