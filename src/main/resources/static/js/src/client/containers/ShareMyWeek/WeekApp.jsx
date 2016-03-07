import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import { Grid, Row, Col, Pager, PageItem, PageHeader } from 'react-bootstrap';
import EventModal from './EventModal.jsx';
import UserSettingsModal from '../../components/ShareMyWeek/UserSettingsModal.jsx';
import WeekTable from '../../components/ShareMyWeek/WeekTable.jsx';
import { saveWeek, addEvent, saveEvent, deleteEvent, getWeekById, getWeekEvents, getUserById } from '../../actions/serverActions';
import { goPrevious, goNext } from '../../actions/weekAppActions';
import { stompClient } from '../../api/clientApi';
import moment from 'moment';

class WeekApp extends Component {

    constructor(props, content) {
        super(props, content);
        this.state = {
            showEventModal: false,
            savingEvent: false,
            selectedDateRange: {
                start: moment(),
                end: moment()
            }
        };
        this.onPrevious = this.onPrevious.bind(this);
        this.onNext = this.onNext.bind(this);
        this.onUpdateUser = this.onUpdateUser.bind(this);
        this.onEventSave = this.onEventSave.bind(this);
        this.onAddEvent = this.onAddEvent.bind(this);
        this.onCellSelect = this.onCellSelect.bind(this);
        this.onEventSelect = this.onEventSelect.bind(this);
        this.onModalHide = this.onModalHide.bind(this);
    }

    componentDidMount() {
        const {params, dispatch, location} = this.props;
        const action = getWeekById(params.id);
        action.payload.then((week) => {
            dispatch(getWeekEvents(week));
            stompClient('events', [{route: '/topic/refreshEvents', callback: () => dispatch(getWeekEvents(week))}]);
        });
        dispatch(action);
        if (location.query && location.query.userId) {
            dispatch(getUserById(location.query.userId));
        }
    }

    getDateRange(date) {
        const sunDate = moment(date).day(0);
        const satDate = moment(date).day(6);
        let dateFormat = '';
        let dateRange = '';
        if (sunDate.year() !== satDate.year()) {
            dateFormat = 'MMMM D YYYY';
            dateRange = sunDate.format(dateFormat) + ' - ' + satDate.format(dateFormat);
        } else if (sunDate.month() !== satDate.month()) {
            dateFormat = 'MMMM D';
            dateRange = sunDate.format(dateFormat) + ' - ' + satDate.format(dateFormat) + ', ' + satDate.format('YYYY');
        } else {
            dateRange = satDate.format('MMMM') + ' ' + sunDate.format('D') + ' - ' + satDate.format('D') + ', ' + satDate.format('YYYY');
        }
        return dateRange;
    }

    getAvailableColors () {
        const {colorMap} = this.props;
        const weekUser = this.getWeekUser();
        const colors = [];
        Object.keys(colorMap).forEach((color) => {
            if (!colorMap[color] || (weekUser && weekUser.color === color)) {
                colors.push(color);
            }
        });
        return colors;
    }

    getWeekUser () {
        const {userData, weekData} = this.props;
        if (userData.entity) {
            return weekData.entity.users.find((user) => user.userInfo.email === userData.entity.email);
        }
        return null;
    }

    onPrevious() {
        const { dispatch, weekData, location } = this.props;
        const query = location.query.timestamp ? location.query : Object.assign({}, location.query, {timestamp: weekData.timestamp});
        dispatch(goPrevious(dispatch, weekData.entity, query));
    }

    onNext() {
        const { dispatch, weekData, location } = this.props;
        const query = location.query.timestamp ? location.query : Object.assign({}, location.query, {timestamp: weekData.timestamp});
        dispatch(goNext(dispatch, weekData.entity, query));
    }

    onUpdateUser(settings) {
        const { dispatch, weekData, location} = this.props;
        const entity = Object.assign({}, weekData.entity, {
            users: weekData.entity.users.map(function (user) {
                return Object.assign({}, user, settings);
            })
        });
        if (location.query && location.query.userId) {
            dispatch(saveWeek(entity, location.query.userId));
        }
    }

    onAddEvent(event) {
        const {dispatch, params, location} = this.props;
        this.setState({savingEvent: true});
        const action = addEvent(Object.assign({}, event, {
            weekId: params.id,
            ownerId: location.query.userId
        }));
        action.payload.then(() => {
            this.setState({
                savingEvent: false,
                showEventModal: false
            });
        });
        dispatch(action);
    }

    onEventSave(event) {
        const {dispatch, location} = this.props;
        this.setState({savingEvent: true});
        const action = saveEvent(Object.assign({}, this.state.selectedEvent, event), location.query.userId);
        action.payload.then(() => {
            this.setState({
                savingEvent: false,
                showEventModal: false
            });
        });
        dispatch(action);
    }

    onCellSelect(start, end) {
        this.setState({
            showEventModal: true,
            selectedDateRange: {
                start,
                end
            }
        });
    }

    onEventSelect(event) {
        this.setState({
            showEventModal: true,
            selectedEvent: event,
            selectedDateRange: {
                start: moment(event.dateTimeRange.start),
                end: moment(event.dateTimeRange.end)
            }
        })
    }

    onModalHide() {
        this.setState({
            showEventModal: false,
            selectedEvent: null
        });
    }

    render() {
        const {weekData, params, location} = this.props;
        const weekUser = this.getWeekUser();
        let timestamp = weekData.timestamp;
        if (location.query && location.query.timestamp) {
            timestamp = location.query.timestamp;
        }
        return (
            <div>
                <UserSettingsModal user={weekUser} colors={this.getAvailableColors()} onUserUpdate={this.onUpdateUser} />
                <EventModal show={this.state.showEventModal} onModalHide={this.onModalHide} onConfirm={this.onAddEvent} onSave={this.onEventSave}
                            saving={this.state.savingEvent} selectedEvent={this.state.selectedEvent}
                            start={this.state.selectedDateRange.start.toDate()} end={this.state.selectedDateRange.end.toDate()} />
                <Grid
                      {...this.props}
                      fluid={ true }>
                    <Row>
                        <Col
                             xs={ 12 }
                             md={ 12 }
                             sm={ 12 }
                             lg={ 12 }>
                            <Pager>
                                <PageItem previous onSelect={ this.onPrevious } href="#">
                                    <span>&larr; Previous</span>
                                </PageItem>
                                <li className="week-header">
                                    <h1 className="text-primary">
                                        <span>ShareMyWeek&nbsp;</span>
                                        <small>{this.getDateRange(timestamp)}</small>
                                    </h1>
                                </li>
                                <PageItem next onSelect={ this.onNext } href="#">
                                    <span>Next &rarr;</span>
                                </PageItem>
                            </Pager>
                        </Col>
                    </Row>
                    <Row>
                        <Col
                             xs={ 12 }
                             md={ 12 }
                             sm={ 12 }
                             lg={ 12 }>
                            <WeekTable {...this.props}
                                       timestamp = { timestamp }
                                       striped={ false }
                                       bordered={ true }
                                       condensed={ false }
                                       hover={ true }
                                       onCellSelect={this.onCellSelect}
                                       onEventSelect={this.onEventSelect}
                                       selectColor={weekUser ? weekUser.color: null}
                                       focused={this.state.showEventModal}
                                       className="week-table" />
                        </Col>
                    </Row>
                </Grid>
            </div>);
    }
}

function mapStateToProps(state) {
    const {weekData, userData, colorMap} = state.appData;
    const {locationBeforeTransitions} = state.routing;
    return {weekData, userData, colorMap, location: locationBeforeTransitions || {}}
}

export default connect(mapStateToProps)(WeekApp);
