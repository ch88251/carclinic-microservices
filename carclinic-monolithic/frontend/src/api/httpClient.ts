import axios from 'axios';
import { getAccessToken } from '../auth/keycloak';

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
});

apiClient.interceptors.request.use(async (config) => {
  const token = await getAccessToken();

  if (token) {
    config.headers.Authorization = 'Bearer ' + token;
  }

  return config;
});

export default apiClient;
