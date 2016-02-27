import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import { Grid, Row, Col, Pager, PageItem, PageHeader } from 'react-bootstrap';
import UserSettingsModal from '../../components/ShareMyWeek/UserSettingsModal.jsx';
import WeekTable from '../../components/ShareMyWeek/WeekTable.jsx';
import { saveWeek, getWeekById, getUserById } from '../../actions/serverActions';
import { goPrevious, goNext } from '../../actions/weekAppActions';
import moment from 'moment';

class WeekApp extends Component {

    constructor(props, content) {
        super(props, content);
        this.onPrevious = this.onPrevious.bind(this);
        this.onNext = this.onNext.bind(this);
        this.onUpdateUser = this.onUpdateUser.bind(this);
    }

    componentDidMount() {
        const {params, dispatch, location} = this.props;
        dispatch(getWeekById(params.id));
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
        return weekData.entity.users.find((user) => user.userInfo.email === userData.entity.email);
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

    render() {
        const {weekData, location} = this.props;
        let timestamp = weekData.timestamp;
        if (location.query && location.query.timestamp) {
            timestamp = location.query.timestamp;
        }
        return (
            <div>
                <UserSettingsModal user={this.getWeekUser()} colors={this.getAvailableColors()} onUserUpdate={this.onUpdateUser} />
                <Grid
                      {...this.props}
                      fluid={ true }>
                    <Row>
                        <Col
                             xs={ 3 }
                             md={ 3 }
                             sm={ 3 }
                             lg={ 3 } />
                        <Col
                             xs={ 6 }
                             md={ 6 }
                             sm={ 6 }
                             lg={ 6 }>
                            <PageHeader>
                                <span>ShareMyWeek</span>
                                <small>{ this.getDateRange(timestamp) }</small>
                            </PageHeader>
                        </Col>
                        <Col
                             xs={ 3 }
                             md={ 3 }
                             sm={ 3 }
                             lg={ 3 }>
                            <Pager>
                                <PageItem onSelect={ this.onPrevious } href="#">
                                    <span>Previous</span>
                                </PageItem>
                                <PageItem onSelect={ this.onNext } href="#">
                                    <span>Next</span>
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
                                       className="week-table" />
                        </Col>
                    </Row>
                </Grid>
            </div>);
    }
}

function mapStateToProps(state) {
    const {weekData, userData, colorMap} = state.appData;
    const {location} = state.routing;
    return {weekData, userData, colorMap, location};
}

export default connect(mapStateToProps)(WeekApp);
