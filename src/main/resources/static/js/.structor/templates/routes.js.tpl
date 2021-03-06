'use strict';

import React from 'react';
import { Router, Route, IndexRoute } from 'react-router';
<% _.forEach(pages, function( page, index ){ %>import <%= page.pageName %> from './<%= page.pageName %>.js';<%= '\n' %><% });  %>
export default function(history){
    return(
        <Router history={history}>
            <Route path="/" component="div">
                <IndexRoute component={<%= pages[0].pageName %>} />
                <% _.forEach(pages, function( page, index ){ %>
                <Route path="<%= page.model.pagePath%>" component={<%= page.pageName %>} />
                <% });  %>
            </Route>
        </Router>
    );
}