import React, { Component } from 'react';
import { connect } from 'react-redux';
import { FormattedDate } from 'react-intl';
import { Modal, Button, Input, FormControls, Grid, Row, Col, Thumbnail } from 'react-bootstrap';
import { getEvents } from '../../actions/serverActions';
import TimeSelect from 'react-time-select';
import moment from 'moment';

class EventModal extends Component {

    constructor(props, content) {
        super(props, content);
        this.onSearch = this.onSearch.bind(this);
    }

    onSearch() {
        const {dispatch, start, end} = this.props;
        const {locationInput, keywordsInput} = this.refs;
        const locations = locationInput.value ? locationInput.value.split(',') : [];
        dispatch(getEvents({
            city: locations[0],
            country: locations[1],
            keywords: keywordsInput.value,
            start: start.toISOString(),
            end: end.toISOString()
        }));
    }

    render() {
        const {show, start, end, onModalHide, eventsData} = this.props;
        let events;
        if (eventsData.fetching.status === 'done') {
            events = [];
            if (eventsData.entities && eventsData.entities._embedded) {
                for (var i = 0; i < eventsData.entities._embedded.events.length && i < 3; i++) {
                    let event = eventsData.entities._embedded.events[i];
                    events.push(
                        <Col key={i} md={4} sm={4} xs={12}>
                            <Thumbnail src={event.image ? event.image.url : '/images/eventbrite-logo.png'} responsive={true}>
                                <FormattedDate value={event.dateTimeRange.start} weekday="short" month="short" day="numeric" hour="numeric" minute="2-digit" />
                                - <FormattedDate value={event.dateTimeRange.end} weekday="short" month="short" day="numeric" hour="numeric" minute="2-digit" />
                                <h5 className="title" title={event.title}>{event.title}</h5>
                                <div><a target="_blank" href={event.eventUrl}>More details</a></div>
                            </Thumbnail>
                        </Col>
                    );
                }
            } else {
                events = <Col md={12} sm={12} xs={12}><i>No results</i></Col>;
            }
        }
        return (
            <Modal dialogClassName="event-dialog" bsSize="lg" show={show}>
                <Modal.Header closeButton={true} onHide={onModalHide} />
                <Modal.Body>
                    <Grid fluid={true}>
                        <row>
                            <Col md={6} sm={6} xs={6}>
                                <FormControls.Static label="When" value={<FormattedDate value={start} day="numeric" month="long" year="numeric" />} />
                            </Col>
                            <Col md={3} sm={3} xs={6}>
                                <TimeSelect label="Start time" value={start.toDate()} start={0} locale="en-US"/>
                            </Col>
                            <Col md={3} sm={3} xs={6}>
                                <TimeSelect label="End time" value={end.toDate()} end={2359} locale="en-US" />
                            </Col>
                        </row>
                        <row>
                            <Col md={6} mdPull={6} sm={6} smPull={6} xs={9}>
                                <Input type="text" label="What" />
                            </Col>
                            <Col md={6} sm={6} xs={3}><span/></Col>
                        </row>
                        <row>
                            <Col md={12} sm={12} xs={12}>
                                <Input type="textarea" label="Description" rows="3" />
                            </Col>
                        </row>
                    </Grid>
                    <Grid fluid={true}>
                        <row>
                            <Col md={12} sm={12} xs={12}>
                                <Input label="Events" wrapperClassName="wrapper">
                                    <Row>
                                        <Col md={4} sm={4} xs={12}>
                                            <input type="text" className="form-control" placeholder="City, Country" ref="locationInput" />
                                        </Col>
                                        <Col md={8} sm={8} xs={12}>
                                            <div className="input-group">
                                                <input type="text" className="form-control" placeholder="Keywords" ref="keywordsInput" />
                                                <span className="input-group-btn">
                                                    <button className="btn btn-default" type="button" onClick={this.onSearch}>
                                                        {eventsData.fetching.status === 'loading' ?
                                                            <i className="fa fa-cog fa-spin fa-lg" />
                                                            : <i className="fa fa-search" />
                                                        }
                                                    </button>
                                                </span>
                                            </div>
                                        </Col>
                                    </Row>
                                </Input>
                            </Col>
                        </row>
                        <row>
                            {events && events.length ? <p className="center-block">Select a event to prepopulate the event form.</p> : <span/>}
                        </row>
                        <row>
                            {events}
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

function mapStateToProps(state, props) {
    const {eventsData} = state.appData;
    return {eventsData};
}

export default connect(mapStateToProps)(EventModal);