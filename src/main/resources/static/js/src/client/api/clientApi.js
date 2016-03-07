import { apiPath } from './serverApi';
import urlTemplate from 'url-template';
import SockJS from 'sockjs-client';

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
        const uri = urlTemplate.parse(path);
        let url = uri.expand(body || {});
        try {url = new URL(url);} catch (e) {}
        body = method === 'get' ? null : body;
        return fetch(url, {
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

export function stompClient(rel, registrations) {
    var socket = SockJS('/socket/' + rel);
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, () => {
        registrations.forEach((registration) => {
            stompClient.subscribe(registration.route, registration.callback);
        });
    });
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

    fetchEntity(entity, rel) {
        return this.api().then(() => Client.get(entity._links[rel].href), this.onError);
    }

    fetchEntityById(rel, id) {
        return this.api().then((apiLinks) => Client.get(apiLinks[rel].href, {id}), this.onError);
    }

    fetchEntityList(rel, query) {
        return this.api().then((apiLinks) => Client.get(apiLinks[rel].href, query), this.onError);
    }

    updateEntity(entity, userId) {
        const href = this.appendUserIdParam(entity._links.self.href, userId);
        return this.api().then(() => Client.put(href, entity), this.onError);
    }

    removeEntity(entity, userId) {
        const href = this.appendUserIdParam(entity._links.self.href, userId);
        return this.api().then(() => Client.delete(href, entity), this.onError);
    }

    //It is bad practice, until user session is supported
    appendUserIdParam(href, userId) {
        return href + (href.indexOf('?{') > -1 ? '&userId=': '?userId=') + userId;
    }

    onError(message) {
        console.error(message);
    }
}

let apiClient = new ApiClient();
export default apiClient;