import React, { Component } from 'react';
import { connect } from 'react-redux';
import { FormattedDate } from 'react-intl';
import { Modal, Button, Input, FormControls, Grid, Row, Col, Thumbnail } from 'react-bootstrap';
import { getEvents } from '../../actions/serverActions';
import TimeSelect from 'react-time-select';
import moment from 'moment';

class Events extends Component {

    render() {
        const {events} = this.props;
        let cols = [];
        for (var i = 0; i < events.length && i < 3; i++) {
            let event = events[i];
            cols.push(
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
        return (<row>{cols}</row>);
    }
}

class SearchStatus extends Component {

    render() {
        const {status} = this.props;
        let message = null;
        if (status === 'done') {
            message = <p>Select a event to prepopulate the event form.</p>;
        } else if (status === 'loading') {
            message = <p>Searching...</p>;
        } else {
            message = <p><i>{status}</i></p>;
        }
        return (
            <row>
                <Col md={12} sm={12} xs={12}>{message}</Col>
            </row>
        );
    }
}

class EventSearch extends Component {

    render() {
        const {events, fetching, newSearch, onSearch} = this.props;
        let status = fetching.status;
        if (fetching.status !== 'loading' && newSearch) {
            status = '';
        } if (fetching.status === 'done' && !events.length) {
            status = 'No Results';
        }
        return (<Grid fluid={true}>
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
                                                    <button className="btn btn-default"
                                                            type="button"
                                                            onClick={() => {
                                                                const {locationInput, keywordsInput} = this.refs;
                                                                onSearch(locationInput.value, keywordsInput.value)
                                                            }}
                                                            disabled={fetching.status === 'loading'}>
                                                            {fetching.status === 'loading' ?
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
            <SearchStatus status={status} />
            {status !== 'done' ? null : <Events events = {events} />}
        </Grid>);
    }
}

class EventModal extends Component {

    constructor(props, content) {
        super(props, content);
        this.state = {
            visible: false,
            start: props.start,
            end: props.end,
            priority: 0
        };
        this.onModalHide = this.onModalHide.bind(this);
        this.onTitleChange = this.onTitleChange.bind(this);
        this.onDescriptionChange = this.onDescriptionChange.bind(this);
        this.onStartTimeChange = this.onStartTimeChange.bind(this);
        this.onEndTimeChange = this.onEndTimeChange.bind(this);
        this.onTypeSelect = this.onTypeSelect.bind(this);
        this.onSearch = this.onSearch.bind(this);
        this.onConfirm = this.onConfirm.bind(this);
        this.onSave = this.onSave.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        const {show, query, fetching, events, selectedEvent} = nextProps;
        let nextState = {};
        if (fetching.status !== 'loading') {
            let {start, end} = nextProps;
            const newSearch = moment(query.start).day() !== moment(start).day() || moment(query.end).day() !== moment(end).day();
            if (!newSearch && events.length) {
                if (this.props.start.getTime() === nextProps.start.getTime()) {
                    start = this.state.start;
                }
                if (this.props.start.getTime() === nextProps.start.getTime()) {
                    end = this.state.end;
                }
            }
            nextState = Object.assign({}, nextState, {
                newSearch,
                visible: show,
                start,
                end
            });
        }
        if (selectedEvent && show != this.state.visible) {
            nextState = Object.assign({}, nextState, {
                title: selectedEvent.title,
                description: selectedEvent.description,
                priority: selectedEvent.priority,
                start: new Date(selectedEvent.dateTimeRange.start),
                end: new Date(selectedEvent.dateTimeRange.end)
            });
        }
        this.setState(nextState);
    }

    onModalHide() {
        const {onModalHide, saving} = this.props;
        if (!saving) {
            this.setState(EventModal.defaultProps);
            onModalHide();
        }
    }

    onTitleChange(e) {
        this.setState({title: e.target.value});
    }

    onDescriptionChange(e) {
        this.setState({description: e.target.value});
    }

    onStartTimeChange(start) {
        this.setState({start});
    }

    onEndTimeChange(end) {
        this.setState({end});
    }

    onTypeSelect(e) {
        this.setState({priority: e.target.value});
    }

    onSearch(location, keywords) {
        const {dispatch} = this.props;
        const locations = location ? location.split(',') : [];
        dispatch(getEvents({
            city: locations[0],
            country: locations[1],
            keywords,
            start: this.state.start.toISOString(),
            end: this.state.end.toISOString()
        }));
    }

    onSave() {
        const {onSave} = this.props;
        onSave(this.getEventData());
    }

    onConfirm() {
        const {onConfirm} = this.props;
        onConfirm(this.getEventData());
    }

    getEventData() {
        return {
            title: this.state.title,
            description: this.state.description,
            priority: this.state.priority,
            dateTimeRange: {
                start: this.state.start.toISOString(),
                end: this.state.end.toISOString()
            }
        };
    }

    render() {
        const {saving, selectedEvent} = this.props;
        return (
            <Modal dialogClassName="event-dialog" bsSize="lg" show={this.state.visible}>
                <Modal.Header closeButton={true} onHide={this.onModalHide} />
                <Modal.Body>
                    <Grid fluid={true}>
                        <row>
                            <Col md={6} sm={6} xs={6}>
                                <FormControls.Static label="When" value={<FormattedDate value={this.state.start} day="numeric" month="long" year="numeric" />} />
                            </Col>
                            <Col md={3} sm={3} xs={6}>
                                <TimeSelect label="Start time" value={this.state.start} start={0} locale="en-US" onChange={this.onStartTimeChange}/>
                            </Col>
                            <Col md={3} sm={3} xs={6}>
                                <TimeSelect label="End time" value={this.state.end} end={2359} locale="en-US" onChange={this.onEndTimeChange} />
                            </Col>
                        </row>
                        <row>&nbsp;</row>
                        <row>
                            <Col md={6} sm={6} xs={9}>
                                <Input type="text" label="What" value={this.state.title} onChange={this.onTitleChange} />
                            </Col>
                            <Col md={6} sm={6} xs={9}>
                                <Input label="Type">
                                    <div>
                                        <label className="radio-inline">
                                            <input type="radio" checked={this.state.priority != 1} onChange={this.onTypeSelect} value={0} />Availability
                                        </label>
                                        <label className="radio-inline">
                                            <input type="radio" checked={this.state.priority == 1} onChange={this.onTypeSelect} value={1} />Event
                                        </label>
                                    </div>
                                </Input>
                            </Col>
                        </row>
                        <row>
                            <Col md={12} sm={12} xs={12}>
                                <Input type="textarea" label="Description" rows="3" value={this.state.description} onChange={this.onDescriptionChange} />
                            </Col>
                        </row>
                    </Grid>
                    {this.state.priority != 1 ? <span /> : <EventSearch newSearch={this.state.newSearch} {...this.props} onSearch={this.onSearch} />}
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={selectedEvent ? this.onSave : this.onConfirm} disabled={!this.state.title || saving}>
                            <span>
                                {(() => {
                                    if (saving) {
                                        return 'Wait... ';
                                    }
                                    if (selectedEvent) {
                                        return 'Save ';
                                    }
                                    return 'Confirm '
                                })()}
                            </span>
                            <i className={saving ? "fa fa-cog fa-spin fa-lg" : (this.state.title ? "fa fa-check": "")} />
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

EventModal.defaultProps = {
    saving: false,
    visible: false,
    events: [],
    title: '',
    description: '',
    priority: 0,
    start: moment().hour(0).minute(0).second(0).millisecond(0).toDate(),
    end: moment().hour(0).minute(30).second(0).millisecond(0).toDate()
};

function mapStateToProps(state) {
    const {entities, query, fetching} = state.appData.eventsData;
    return {
        events: entities._embedded ? entities._embedded.events : undefined,
        query,
        fetching
    };
}

export default connect(mapStateToProps)(EventModal);