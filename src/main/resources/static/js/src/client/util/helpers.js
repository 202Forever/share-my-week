import { routeActions } from 'react-router-redux'
import urlTemplate from 'url-template';
import { push } from 'react-router-redux';

export function dispatchRoute(dispatch, entity) {
    dispatch(push(getEntityPath(entity, 'page')));
}

export function getEntityPath(entity, rel) {
    const uri = urlTemplate.parse(entity._links[rel].href);
    const url = uri.expand(entity);
    const paths = parseEntityUrl(url);
    return '/' + paths.join('/');
}

export function parseEntityUrl(url) {
    const paths = url.split('/');
    return [...paths.slice(3, paths.length)];
}