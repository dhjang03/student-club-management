import axios, { AxiosInstance } from 'axios';
import { getToken } from '@/utils/auth';
import { toast } from 'react-toastify';
import { clearToken } from '@/utils/auth';
import { useRouter } from 'next/router';

export const api: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
    Accept: 'application/json',
  },
});

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
          // Bad Request
          toast.error(`Bad request. ${message}`);
          break;

        case 401:
          // Unauthorized
          toast.error('You are not authorized. Please log in again.');
          clearToken();
          useRouter().push('/login');
          break;

        case 403:
          // Forbidden
          toast.error('You do not have permission to perform this action.');
          break;

        case 404:
          // Not Found
          toast.error(message);
          break;

        case 500:
          // Internal Server Error
          toast.error('A server error occurred. Please try again later.');
          break;

        default:
          toast.error(`An unexpected error (${status}) occurred.`);
          break;
      }
    } else {
      toast.error('Network error – please check your internet connection.');
    }

    return Promise.reject(error);
  }
);
