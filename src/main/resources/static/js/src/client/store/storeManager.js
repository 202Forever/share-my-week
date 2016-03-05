import { createStore, applyMiddleware, compose } from 'redux';
import { browserHistory } from 'react-router';
import { routerMiddleware } from 'react-router-redux';
import rootReducer from '../reducers';
import myMiddleware from '../middleware/middleware.js';
import promiseMiddleware from '../middleware/promiseMiddleware.js';
import initialState from './initialState.js';

const createStoreWithMiddleware = compose(
    applyMiddleware(myMiddleware, promiseMiddleware, routerMiddleware(browserHistory)),
    // Redux dev extensions
    window.devToolsExtension ? window.devToolsExtension() : f => f
)(createStore);

export default function initStore() {
    const store = createStoreWithMiddleware(rootReducer, initialState);
    if (module.hot) {
        // Enable Webpack hot module replacement for reducers
        module.hot.accept('../reducers', () => {
            const nextRootReducer = require('../reducers/index');
            store.replaceReducer(nextRootReducer);
        });
    }
    return store;
}

