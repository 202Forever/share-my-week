import { combineReducers } from 'redux';
import { reducer as formReducer } from 'redux-form';
import { routerReducer } from 'react-router-redux'
import reduceReducers from 'reduce-reducers';
import serverReducer from '../actions/serverActions';
import weekAppReducer from '../actions/weekAppActions';
import colorMapReducer from './colorMapReducer';

function appReducer(state = {}, action) {
    return state;
}

const rootReducer = combineReducers({
    appData: reduceReducers(
        appReducer,
        serverReducer,
        weekAppReducer,
        colorMapReducer
    ),
    form: formReducer,
    routing: routerReducer
});

export default rootReducer;