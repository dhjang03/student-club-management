'use client';

import React, { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/context/AuthContext';
import { isTokenValid } from '@/utils/auth';
import { clearToken } from '@/utils/auth';

interface ProtectedRouteProps {
  children: React.ReactNode;
}

export default function ProtectedRoute({ children }: ProtectedRouteProps) {
  const router = useRouter();
  const { token } = useAuth();

  useEffect(() => {
    if (!token || !isTokenValid(token)) {
      clearToken();
      router.push('/login');
    }
  }, [token, router]);

  if (!token) {
    return <div>Redirecting...</div>;
  }

  return <>{children}</>;
}
