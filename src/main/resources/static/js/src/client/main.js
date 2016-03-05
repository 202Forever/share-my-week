import 'babel-polyfill';
import './assets/css/react-widgets.css';
import './assets/css/bootstrap.css';
import './assets/css/font-awesome.css';
import './assets/css/app.css';
import '../../../less/app.less';
import './assets/js/bootstrap.js';

import Moment from 'moment';
import momentLocalizer from 'react-widgets/lib/localizers/moment';
momentLocalizer(Moment);
import numberLocalizer from 'react-widgets/lib/localizers/simple-number';
numberLocalizer();

import React from 'react';
import { IntlProvider } from 'react-intl';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { browserHistory } from 'react-router';
import { syncHistoryWithStore } from 'react-router-redux'
import getRoutes from './routes/routes.js';
import storeManager from './store/storeManager.js';

const store = storeManager();
const history = syncHistoryWithStore(browserHistory, store)
const routes = getRoutes(history);

ReactDOM.render(
    <IntlProvider locale="en-US">
        <Provider store={store}>
            {routes}
        </Provider>
    </IntlProvider>,
    document.getElementById('content')
);
