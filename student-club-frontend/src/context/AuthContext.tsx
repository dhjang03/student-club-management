'use client';

import { createContext, useContext, useState } from 'react';
import { getToken } from '@/utils/auth';

const AuthContext = createContext<
  | {
      token: string | null;
      setToken: React.Dispatch<React.SetStateAction<string | null>>;
    }
  | undefined
>(undefined);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const existingToken = getToken();
  const [token, setToken] = useState(existingToken);
  return (
    <AuthContext.Provider value={{ token, setToken }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
