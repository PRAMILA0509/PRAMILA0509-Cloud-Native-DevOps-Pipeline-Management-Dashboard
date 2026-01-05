import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api' // Replace with your ngrok URL if necessary
});

// User & Auth
export const loginUser = (credentials) => api.post('/users/login', credentials);
export const getAllUsers = () => api.get('/users');

// Repositories
export const getAllRepos = () => api.get('/repos');
export const registerRepo = (userId, repoData) => api.post(`/repos/register/${userId}`, repoData);
export const triggerDeployment = (repoId) => api.post(`/repos/trigger/${repoId}`);

// Builds (Maps to your SQL Result Grid data)
export const getBuildsForRepo = (repoId) => api.get(`/builds/repo/${repoId}`);
export const getLatestBuildForRepo = (repoId) => api.get(`/builds/repo/${repoId}/latest`);

// Events/Stages (For tracking BUILD, TEST, DEPLOY specifically)
export const getEventsForBuild = (buildId) => api.get(`/events/build/${buildId}`);

export default api;