'use strict';

import React from 'react';
import { Router, Route, IndexRoute } from 'react-router';
import { createHistory } from 'history';
import App from './App.js';
import Week from './Week.js';

export default function() {
    const history = createHistory();
    return (
        <Router history={ history }>
            <Route path="/" component="div">
                <IndexRoute component={ App } />
                <Route path="/" component={ App } />
                <Route path="/week/:id" component={ Week } />
            </Route>
        </Router>
        );
}