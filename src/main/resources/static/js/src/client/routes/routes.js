'use strict';

import React from 'react';
import { Router, Route, IndexRoute, browserHistory } from 'react-router';
import App from './App.js';
import Week from './Week.js';

export default function() {
    return (
        <Router history={ browserHistory }>
            <Route path="/" component="div">
                <IndexRoute component={ App } />
                <Route path="/" component={ App } />
                <Route path="/weeks/:id" component={ Week } />
            </Route>
        </Router>
        );
}