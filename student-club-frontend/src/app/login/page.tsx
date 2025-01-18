'use client';

import { useState, FormEvent } from 'react';
import { api } from '@/api/axios';
import { LoginRequest, LoginResponse } from '@/types/dashboard';
import { useRedirectBasedOnToken } from '@/hooks/useRedirectBasedOnToken';


export default function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { redirect } = useRedirectBasedOnToken();


  const loginHandler = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const data: LoginRequest = { username, password };

    try {
      const response = await api.post<LoginResponse>('/api/auth/login', data);
      const token = response.data.token;

      localStorage.setItem('token', token);
      console.log('Token: ', token);
      redirect();
    } catch (error) {
      console.error('Login error:', error);
      alert('Login failed');
    }
  };

  return (
    <div className="flex min-h-full flex-1 flex-col justify-top px-6 py-12 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-sm">
        <h2 className="mt-10 text-center text-2xl font-bold tracking-tight text-gray-900">
          Sign in to your account
        </h2>
      </div>

      <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
        <form onSubmit={loginHandler} className="space-y-6">
          <div>
            <label htmlFor="username" className="block text-sm font-medium text-gray-900">
              Username
            </label>
            <div className="mt-2">
              <input
                id="username"
                name="username"
                type="text"
                required
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                autoComplete="username"
                className="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 
                           outline outline-1 -outline-offset-1 outline-gray-300 
                           placeholder:text-gray-400 focus:outline focus:outline-2 
                           focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm"
              />
            </div>
          </div>

          <div>
            <label htmlFor="password" className="block text-sm font-medium text-gray-900">
              Password
            </label>
            <div className="mt-2">
              <input
                id="password"
                name="password"
                type="password"
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                autoComplete="current-password"
                className="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 
                           outline outline-1 -outline-offset-1 outline-gray-300 
                           placeholder:text-gray-400 focus:outline focus:outline-2 
                           focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm"
              />
            </div>
          </div>

          <div>
            <button
              type="submit"
              className="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 
                         text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 
                         focus-visible:outline focus-visible:outline-2 
                         focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
            >
              Sign in
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
