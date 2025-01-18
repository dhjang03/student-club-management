'user client'

import { useRouter } from 'next/navigation';
import { getToken, getUserRole, isTokenValid } from '@/utils/auth';
import { UserRole } from '@/types/dashboard';

export function useRedirectBasedOnToken() {
  const router = useRouter();

  const redirect = () => {
    const token = getToken();
    const roles = getUserRole(token);
    console.log('Redirect - Token: ', token);
    console.log('Redirect - Role: ', roles);
    console.log('Redirect - Valid: ', isTokenValid(token));

    if (token && isTokenValid(token) && roles) {
      console.log("redirecting to dashboard");
      if (roles.includes(UserRole.STUDENT)) {
        router.push('/student-dashboard');
      } else if (roles.includes(UserRole.STAFF)) {
        router.push('/staff-dashboard');
      } else {
        router.push('/login');
      }
    } else {
      router.push('/login');
    }
  };

  return { redirect };
}
