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
        });
    }
}

const api = (function () {
    var apiLinks;
    return () => {
        if (apiLinks) {
            return new Promise((resolve) => {
                return resolve(apiLinks);
            });
        }
        var promise = Client.get(apiPath).then((response) => response.json());
        return promise.then((json) => {
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