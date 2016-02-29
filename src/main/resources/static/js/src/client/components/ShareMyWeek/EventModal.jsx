import React, { Component } from 'react';
import { FormattedDate } from 'react-intl';
import { Modal, Button, Input, FormControls, Grid, Row, Col } from 'react-bootstrap';
import TimeSelect from 'react-time-select';
import moment from 'moment';

class EventModal extends Component {

    render() {
        const {show, start, end, onModalHide} = this.props;
        return (
            <Modal dialogClassName="event-dialog" show={show}>
                <Modal.Header closeButton={true} onHide={onModalHide} />
                <Modal.Body>
                    <Grid fluid={true}>
                        <row>
                            <Col md={6}>
                                <FormControls.Static label="When" value={<FormattedDate value={start} day="numeric" month="long" year="numeric" />} />
                            </Col>
                            <Col md={3}>
                                <TimeSelect label="Start time" value={start.toDate()} start={0} locale="en-US"/>
                            </Col>
                            <Col md={3}>
                                <TimeSelect label="End time" value={end.toDate()} end={2359} locale="en-US" />
                            </Col>
                        </row>
                        <row>
                            <Col md={6} mdPull={6}>
                                <Input type="text" label="What" />
                            </Col>
                            <Col md={6}><span/></Col>
                        </row>
                        <row>
                            <Col md={12}>
                                <Input type="textarea" label="Description" rows="3" />
                            </Col>
                        </row>
                    </Grid>
                </Modal.Body>
                <Modal.Footer>
                    <Button><span>Confirm&nbsp;</span><i className="fa fa-check"/></Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

EventModal.defaultProps = {
    start: moment().hour(0),
    end: moment().hour(0).minute(30)
};

export default EventModal;