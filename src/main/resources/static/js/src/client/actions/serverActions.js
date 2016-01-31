import { createWeek } from '../api';
import { createAction, handleActions } from './reduxActionsSequence';

const ADD_WEEK = 'ADD_WEEK';

export const addWeek = createAction(ADD_WEEK, (entity) => {
   return createWeek(entity)
       .then(response => {
          return response;
       });
});

/**
 * State substructure: { application }
 */
export default handleActions({

    [ADD_WEEK]: {
        start(state, action) {
            const weekData = Object.assign({}, state.weekData, {
                entity: {},
                fetching: {
                    status: 'loading',
                    errorText: '',
                    error: false
                }
            });
            state = Object.assign({}, state, {weekData});
            return state;
        },
        next(state, action) {
            const weekData = Object.assign({}, state.weekData, {
                entity: action.payload,
                fetching: {
                    status: 'done',
                    errorText: '',
                    error: false
                }
            });
            state = Object.assign({}, state, {weekData});
            return state;
        },
        throw(state, action) {
            const weekData = Object.assign({}, state.weekData, {
                entity: {},
                fetching: {
                    status: 'done',
                    errorText: action.payload.message ? action.payload.message : 'Error: no message',
                    error: true
                }
            });
            state = Object.assign({}, state, {weekData});
            return state;
        }
    }

}, {});

