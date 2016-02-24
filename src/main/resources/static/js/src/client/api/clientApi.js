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

class ApiClient {

    constructor () {
        this.resolves = [];
    }

    api() {
        if (!this.client) {
            this.client = Client.get(apiPath).then((json) => {
                this.links = json._links;
                this.resolves.forEach(resolve => resolve(this.links));
                this.resolves = [];
            });
        }
        if (this.links) {
            return Promise.resolve(this.links);
        } else {
            return new Promise((resolve) => this.resolves.push(resolve));
        }
    }

    createEntity(rel, entity) {
        return this.api().then((apiLinks) => Client.post(apiLinks[rel].href, entity), this.onError);
    }

    fetchEntity(entity) {
        return this.api().then(() => Client.get(entity._links.self), this.onError);
    }

    fetchEntityById(rel, id) {
        return this.api().then((apiLinks) => Client.get(apiLinks[rel].href + '/' + id), this.onError);
    }

    fetchEntityList(rel) {
        return this.api().then((apiLinks) => Client.get(apiLinks[rel].href), this.onError);
    }

    updateEntity(entity) {
        return this.api().then(() => Client.put(entity._links.self, entity), this.onError);
    }

    removeEntity(entity) {
        return this.api().then(() => Client.delete(entity._links.self, entity), this.onError);
    }

    onError(message) {
        console.error(message);
    }
}

let apiClient = new ApiClient();
export default apiClient;