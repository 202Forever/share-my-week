import { createWeek, updateWeek, fetchWeekById, fetchUserById, fetchEvents } from '../api';
import { createAction, handleActions } from './reduxActionsSequence';

const ADD_WEEK = 'ADD_WEEK';
const SAVE_WEEK = 'SAVE_WEEK';
const GET_WEEK = 'GET_WEEK';
const GET_USER = 'GET_USER';
const GET_EVENTS = 'GET_EVENTS';

export const addWeek = createAction(ADD_WEEK, (entity) => {
   return createWeek(entity);
});

export const saveWeek = createAction(SAVE_WEEK, (entity, userId) => {
   return  updateWeek(entity, userId);
});

export const getWeekById = createAction(GET_WEEK, (id) => {
    return fetchWeekById(id);
});

export const getUserById = createAction(GET_USER, (id) => {
    return fetchUserById(id);
});

export const getEvents = createAction(GET_EVENTS, (query) => {
    return fetchEvents(query);
});

/**
 * Action reducers
 */
export default handleActions({

    [ADD_WEEK]: {
        start(state, action) {
            const weekData = Object.assign({}, state.weekData, {
                entity: {
                    users: []
                },
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
                entity: {
                    users: []
                },
                fetching: {
                    status: 'done',
                    errorText: action.payload.message ? action.payload.message : 'Error: no message',
                    error: true
                }
            });
            state = Object.assign({}, state, {weekData});
            return state;
        }
    },

    [SAVE_WEEK]: {
        start(state, action) {
            const weekData = Object.assign({}, state.weekData, {
                entity: {
                    users: []
                },
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
                entity: {
                    users: []
                },
                fetching: {
                    status: 'done',
                    errorText: action.payload.message ? action.payload.message : 'Error: no message',
                    error: true
                }
            });
            state = Object.assign({}, state, {weekData});
            return state;
        }
    },

    [GET_WEEK]: {
        start(state, action) {
            const weekData = Object.assign({}, state.weekData, {
                entity: {
                    users: []
                },
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
                entity: {
                    users: []
                },
                fetching: {
                    status: 'done',
                    errorText: action.payload.message ? action.payload.message : 'Error: no message',
                    error: true
                }
            });
            state = Object.assign({}, state, {weekData});
            return state;
        }
    },

    [GET_USER] : {
        start(state, action) {
            const userData = Object.assign({}, state.userData, {
                entity: null,
                fetching: {
                    status: 'loading',
                    errorText: '',
                    error: false
                }
            });
            state = Object.assign({}, state, {userData});
            return state;
        },
        next(state, action) {
            const userData = Object.assign({}, state.userData, {
                entity: action.payload,
                fetching: {
                    status: 'done',
                    errorText: '',
                    error: false
                }
            });
            state = Object.assign({}, state, {userData});
            return state;
        },
        throw(state, action) {
            const userData = Object.assign({}, state.userData, {
                entity: null,
                fetching: {
                    status: 'done',
                    errorText: action.payload.message ? action.payload.message : 'Error: no message',
                    error: true
                }
            });
            state = Object.assign({}, state, {userData});
            return state;
        }
    },

    [GET_EVENTS]: {
        start(state, action) {
            const eventsData = Object.assign({}, state.eventsData, {
                entities: null,
                fetching: {
                    status: 'loading',
                    errorText: '',
                    error: false
                }
            });
            state = Object.assign({}, state, {eventsData});
            return state;
        },
        next(state, action) {
            const eventsData = Object.assign({}, state.eventsData, {
                entities: action.payload,
                fetching: {
                    status: 'done',
                    errorText: '',
                    error: false
                }
            });
            state = Object.assign({}, state, {eventsData});
            return state;
        },
        throw(state, action) {
            const eventsData = Object.assign({}, state.eventsData, {
                entities: null,
                fetching: {
                    status: 'done',
                    errorText: action.payload.message ? action.payload.message : 'Error: no message',
                    error: true
                }
            });
            state = Object.assign({}, state, {eventsData});
            return state;
        }
    }

}, {});
