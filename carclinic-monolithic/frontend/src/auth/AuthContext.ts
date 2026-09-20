import { createContext } from 'react';
import type Keycloak from 'keycloak-js';

export interface AuthUser {
  name: string;
  initials: string;
}

export interface AuthContextValue {
  keycloak: Keycloak;
  initialized: boolean;
  authenticated: boolean;
  user: AuthUser | null;
  login: () => void;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextValue | null>(null);
