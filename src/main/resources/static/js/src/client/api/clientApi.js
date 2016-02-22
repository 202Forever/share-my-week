import { apiPath } from './serverApi';
import urlTemplate from 'url-template';

export class Client {

    static post(path, body) {
        return this.request(path, body, 'post');
    }

    static get(path, body) {
        return this.request(path, body, 'get');
    }

    static put(path, body) {
        return this.request(path, body, 'put');
    }

    static delete(path, body) {
        return this.request(path, body, 'delete');
    }

    static request(path, body, method) {
        let uri = urlTemplate.parse(path);
        return fetch(uri.expand(body || {}), {
            method: method,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: body ? JSON.stringify(body) : null
        })
        .then(response => {
            if (response.status >= 200 && response.status < 300) {
                return response;
            } else {
                var error = new Error(response.statusText);
                error.response = response;
                throw error;
            }
        })
        .then(response => response.json())
        .catch(error => error.response.json().then(json => Promise.reject({_error: json.message})));
    }
}

const api = (function () {
    var apiLinks;
    return () => {
        if (apiLinks) {
            return new Promise(resolve => resolve(apiLinks));
        }
        return Client.get(apiPath).then((json) => {
            apiLinks = json._links;
            return apiLinks;
        });
    };
})();

class ApiClient {

    createEntity(rel, entity) {
        return api().then((apiLinks) => Client.post(apiLinks[rel].href, entity));
    }

    fetchEntity(entity) {
        return api().then(() => Client.get(entity._links.self));
    }

    fetchEntityById(rel, id) {
        return api().then((apiLinks) => Client.get(apiLinks[rel].href + '/' + id));
    }

    fetchEntityList(rel) {
        return api().then((apiLinks) => Client.get(apiLinks[rel].href));
    }

    updateEntity(entity) {
        return api().then(() => Client.put(entity._links.self, entity));
    }

    removeEntity(entity) {
        return api().then(() => Client.delete(entity._links.self, entity));
    }
}

let client;
export default function apiClient() {
    if (client) {
        return client;
    }
    client = new ApiClient();
    return client;
}