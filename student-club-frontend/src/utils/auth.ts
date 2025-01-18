import { jwtDecode } from 'jwt-decode';

interface JWTPayload {
  exp: number;
  roles: string;
  id: number;
}

/**
 * Retrieves the JWT token from localStorage
 * @returns The token string or null if not found
 */
export const getToken = (): string | null => {
  if (typeof window !== 'undefined') {
    return localStorage.getItem('token');
  }
  return null;
};

/**
 * Validates if the provided JWT token is not expired
 * @param token - The JWT token string
 * @returns Boolean indicating if the token is valid and not expired
 */
export const isTokenValid = (token: string | null): boolean => {
  if (!token) return false;

  try {
    const decoded = jwtDecode<JWTPayload>(token);
    const currentTime = Date.now() / 1000; // Convert to seconds
    return decoded.exp > currentTime;
  } catch (error) {
    console.error('Invalid token:', error instanceof Error ? error.message : 'Unknown error');
    return false;
  }
};

/**
 * Extracts the user role from the JWT token
 * @param token - The JWT token string
 * @returns The user role string or null if token is invalid
 */
export const getUserRole = (token: string | null): string[] | null => {
  if (!token) return null;

  try {
    const decoded = jwtDecode<JWTPayload>(token);
    return decoded.roles.split(',');
  } catch (error) {
    console.error('Error decoding token:', error instanceof Error ? error.message : 'Unknown error');
    return null;
  }
};