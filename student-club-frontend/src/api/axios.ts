import axios, { AxiosInstance } from 'axios';
import { getToken, clearToken } from '@/utils/auth';
import { toast } from 'react-toastify';
import { useRouter } from 'next/router';

const API_BASE_URL = process.env.API_BASE_URL;

if (!API_BASE_URL) {
  throw new Error('API_BASE_URL is not defined in the environment variables.');
}

/**
 * Create an Axios instance with default configuration.
 * - baseURL: Base URL for all API requests.
 * - headers: Default headers set for content type and accepted response types.
 */
export const api: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
    Accept: 'application/json',
  },
});

/**
 * Request interceptor to add Authorization header to outgoing requests if a token exists.
 */
api.interceptors.request.use(
  (config) => {
    const token = getToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

/**
 * Response interceptor to handle responses and errors.
 */
api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response) {
      const status = error.response.status;
      const message = error.response.data.message;

      switch (status) {
        case 400:
          // Handle 400 Bad Request errors.
          toast.error(`Bad request. ${message}`);
          break;

        case 401:
          // Handle 401 Unauthorized errors.
          toast.error('You are not authorized. Please log in again.');
          clearToken();
          useRouter().push('/login');
          break;

        case 403:
          // Handle 403 Forbidden errors.
          toast.error('You do not have permission to perform this action.');
          break;

        case 404:
          // Handle 404 Not Found errors.
          toast.error(message);
          break;

        case 500:
          // Handle 500 Internal Server Error.
          toast.error('A server error occurred. Please try again later.');
          break;

        default:
          // Handle other unexpected errors.
          toast.error(`An unexpected error (${status}) occurred.`);
          break;
      }
    } else {
      // If the error is due to network issues or no response.
      toast.error('Network error – please check your internet connection.');
    }

    return Promise.reject(error);
  }
);
