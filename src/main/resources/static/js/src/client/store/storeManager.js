import { createStore, applyMiddleware, compose } from 'redux';
import { browserHistory } from 'react-router';
import { syncHistory } from 'react-router-redux';
import rootReducer from '../reducers';
import myMiddleware from '../middleware/middleware.js';
import promiseMiddleware from '../middleware/promiseMiddleware.js';
import initialState from './initialState.js';

const reduxRouterMiddleware = syncHistory(browserHistory);
const createStoreWithMiddleware = compose(
    applyMiddleware(myMiddleware, promiseMiddleware, reduxRouterMiddleware),
    // Redux dev extensions
    window.devToolsExtension ? window.devToolsExtension() : f => f
)(createStore);

export default function initStore() {
    const store = createStoreWithMiddleware(rootReducer, initialState);
    // Required for replaying actions from devtools to work
    reduxRouterMiddleware.listenForReplays(store);
    if (module.hot) {
        // Enable Webpack hot module replacement for reducers
        module.hot.accept('../reducers', () => {
            const nextRootReducer = require('../reducers/index');
            store.replaceReducer(nextRootReducer);
        });
    }
    return store;
}

