'use client';

import React, { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/context/AuthContext';
import { getUserRole, isTokenValid, getUserId } from '@/utils/auth';
import { UserRole } from '@/types/dashboard';

interface ProtectedRouteProps {
  children: React.ReactNode;
}

export default function ProtectedRoute({ children }: ProtectedRouteProps) {
  const router = useRouter();
  const { token } = useAuth();

  useEffect(() => {
    if (token && isTokenValid(token)) {
      const roles = getUserRole(token);

      const userId = getUserId(token);
      console.log(userId);
      console.log(roles);

      if (roles.includes(UserRole.STUDENT)) {
        router.push('/student-dashboard');
      } else if (roles.includes(UserRole.STAFF)) {
        router.push('/staff-dashboard');
      }
    } else {
      router.push('/login');
    }
  }, [token, router]);

  if (!token) {
    return <div>Redirecting...</div>;
  }

  return <>{children}</>;
}
