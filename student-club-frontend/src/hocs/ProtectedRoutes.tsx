'use client';

import React, { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/context/AuthContext';
import { getUserRole, isTokenValid } from '@/utils/auth';
import { UserRole } from '@/types/dashboard';
import { clearToken } from '@/utils/auth';

interface ProtectedRouteProps {
  children: React.ReactNode;
}

export default function ProtectedRoute({ children }: ProtectedRouteProps) {
  const router = useRouter();
  const { token } = useAuth();

  // useEffect(() => {
  //   if (token && isTokenValid(token)) {
  //     const roles = getUserRole(token);
  //     if (roles.includes(UserRole.STUDENT)) {
  //       router.push('/student-dashboard');
  //     } else if (roles.includes(UserRole.STAFF)) {
  //       router.push('/staff-dashboard');
  //     }
  //   } else {
  //     router.push('/login');
  //   }
  // }, [token, router]);

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
