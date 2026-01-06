import axios from 'axios';
const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});

// User & Auth
export const loginUser = (credentials) => api.post('/users/login', credentials);
export const registerUser = (userData) => api.post('/users', userData);
export const getAllUsers = () => api.get('/users');

// Repositories
export const getAllRepos = () => api.get('/repos');
export const registerRepo = (userId, repoData) => api.post(`/repos/register/${userId}`, repoData);
export const triggerDeployment = (repoId) => api.post(`/repos/trigger/${repoId}`);

// Builds & Events
export const getBuildsForRepo = (repoId) => api.get(`/builds/repo/${repoId}`);
export const getLatestBuildForRepo = (repoId) => api.get(`/builds/repo/${repoId}/latest`);
export const getEventsForBuild = (buildId) => api.get(`/events/build/${buildId}`);

export default api;