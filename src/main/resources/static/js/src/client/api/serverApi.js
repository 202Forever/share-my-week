import apiClient from './clientApi';

export const apiPath = '/api';

export function createWeek(entity) {
    return apiClient.createEntity('weeks', entity);
}

export function updateWeek(entity, userId) {
    return apiClient.updateEntity(entity, userId);
}

export function fetchWeekById(id) {
    return apiClient.fetchEntityById('weeks', id);
}

export function fetchWeekEvents(entity) {
    return apiClient.fetchEntity(entity, 'events');
}

export function fetchUserById(id) {
    return apiClient.fetchEntityById('users', id);
}

export function createEvent(entity) {
    return apiClient.createEntity('events', entity);
}

export function fetchEvents(query) {
    return apiClient.fetchEntityList('events', query);
}
