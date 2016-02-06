import apiClient from './clientApi';

export const apiPath = '/api';

export function createWeek(entity) {
    return apiClient().createEntity('weeks', entity);
}