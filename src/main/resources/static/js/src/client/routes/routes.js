'use strict';

import React from 'react';
import { Router, Route, IndexRoute } from 'react-router';
import App from './App.js';
import Week from './Week.js';

export default function(history) {
    return (
        <Router history={history}>
            <Route path="/" component="div">
                <IndexRoute component={ App } />
                <Route path="/" component={ App } />
                <Route path="/weeks/:id" component={ Week } />
            </Route>
        </Router>
        );
}