import { createAction, handleActions } from './reduxActionsSequence';
import { routeActions } from 'react-router-redux'
import { getEntityPath } from '../util/helpers'
import moment from 'moment';

const PREV_WEEK = 'PREV_WEEK';
const NEXT_WEEK = 'NEXT_WEEK';

export const goPrevious = createAction(PREV_WEEK, (dispatch, entity, query) => {
    const location = {
        pathname: getEntityPath(entity, 'page'),
        query: Object.assign({}, query, {timestamp: moment(query.timestamp).day(-7).toISOString()})
    };
    dispatch(routeActions.push(location));
    return location;
});

export const goNext = createAction(NEXT_WEEK, (dispatch, entity, query) => {
    const location = {
        pathname: getEntityPath(entity, 'page'),
        query: Object.assign({}, query, {timestamp: moment(query.timestamp).day(7).toISOString()})
    };
    dispatch(routeActions.push(location));
    return location;
});

export default handleActions({
    [PREV_WEEK]: {
        
    },
    [NEXT_WEEK]: {

    }
});
