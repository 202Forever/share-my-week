import * as api from '../api';
import { createAction, handleActions } from './reduxActionsSequence';

const ADD_WEEK = 'ADD_WEEK';
const SAVE_WEEK = 'SAVE_WEEK';
const GET_WEEK = 'GET_WEEK';
const GET_WEEK_EVENTS = 'GET_WEEK_EVENTS';
const GET_USER = 'GET_USER';
const ADD_EVENT = 'ADD_EVENT';
const SAVE_EVENT = 'SAVE_EVENT';
const DELETE_EVENT = 'DELETE_EVENT';
const GET_EVENTS = 'GET_EVENTS';

export const addWeek = createAction(ADD_WEEK, (entity) => {
   return api.createWeek(entity);
});

export const saveWeek = createAction(SAVE_WEEK, (entity, userId) => {
   return  api.updateWeek(entity, userId);
});

export const getWeekById = createAction(GET_WEEK, (id) => {
    return api.fetchWeekById(id);
});

export const getWeekEvents = createAction(GET_WEEK_EVENTS, (entity) => {
    return api.fetchWeekEvents(entity);
})

export const getUserById = createAction(GET_USER, (id) => {
    return api.fetchUserById(id);
});

export const addEvent = createAction(ADD_EVENT, (entity) => {
    return api.createEvent(entity);
});

export const saveEvent = createAction(SAVE_EVENT, (entity, userId) => {
    return api.updateEvent(entity);
});

export const removeEvent = createAction(DELETE_EVENT, (entity) => {
   return api.deleteEvent(entity);
});

export const getEvents = createAction(GET_EVENTS, (query) => {
    return api.fetchEvents(query).then((json) => {
        return {
            response : json,
            query
        };
    });
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

    [GET_WEEK_EVENTS] : {
        start(state, action) {
            const weekData = Object.assign({}, state.weekData, {
                eventsData : {
                    fetching: {
                        status: 'loading',
                        errorText: '',
                        error: false
                    }
                }
            });
            state = Object.assign({}, state, {weekData});
            return state;
        },
        next(state, action) {
            const weekData = Object.assign({}, state.weekData, {
                eventsData: {
                    entities: action.payload,
                    fetching: {
                        status: 'done',
                        errorText: '',
                        error: false
                    }
                }
            });
            state = Object.assign({}, state, {weekData});
            return state;
        },
        throw(state, action) {
            const weekData = Object.assign({}, state.weekData, {
                eventsData: {
                    fetching: {
                        status: 'done',
                        errorText: action.payload.message ? action.payload.message : 'Error: no message',
                        error: true
                    }
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
                entities: {},
                query: {},
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
                entities: action.payload.response,
                query: action.payload.query,
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
                entities: {},
                query: {},
                fetching: {
                    status: 'done',
                    errorText: action.payload.message ? action.payload.message : 'Error: no message',
                    error: true
                }
            });
            state = Object.assign({}, state, {eventsData});
            return state;
        }
    },

    [ADD_EVENT]: {
        start(state, action) {
            const weekData = Object.assign({}, state.weekData, {
                eventsData : Object.assign({}, state.weekData.eventsData, {
                    fetching: {
                        status: 'loading',
                        errorText: '',
                        error: false
                    }
                })
            });
            state = Object.assign({}, state, {weekData});
            return state;
        },
        next(state, action) {
            const events = state.weekData.eventsData.entities._embedded ? state.weekData.eventsData.entities._embedded.events : [];
            const weekData = Object.assign({}, state.weekData, {
                eventsData : Object.assign({}, state.weekData.eventsData, {
                    entities : {
                        _embedded: {
                            events: [...events, action.payload]
                        }
                    },
                    fetching: {
                        status: 'done',
                        errorText: '',
                        error: false
                    }
                })
            });
            state = Object.assign({}, state, {weekData});
            return state;
        },
        throw(state, action) {
            const weekData = Object.assign({}, state.weekData, {
                eventsData : Object.assign({}, state.weekData.eventsData, {
                    fetching: {
                        status: 'done',
                        errorText: action.payload.message ? action.payload.message : 'Error: no message',
                        error: true
                    }
                })
            });
            state = Object.assign({}, state, {weekData});
            return state;
        }
    }

}, {});
