import { combineReducers } from 'redux';
import { reducer as formReducer } from 'redux-form';
import reduceReducers from 'reduce-reducers';
import serverReducer from '../actions/serverActions';

function appReducer(state = {}, action) {
    return state;
}

const rootReducer = combineReducers({
    appData: reduceReducers(
        appReducer,
        serverReducer
    ),
    form: formReducer.plugin({
        createWeekForm: (state, action) => {
            if (action.type === 'STOP_SUBMIT') {
                return state;
            }
            return state;
        }
    })
});

export default rootReducer;