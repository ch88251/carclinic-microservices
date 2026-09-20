import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: import.meta.env.VITE_KEYCLOAK_URL,
  realm: import.meta.env.VITE_KEYCLOAK_REALM,
  clientId: import.meta.env.VITE_KEYCLOAK_CLIENT_ID,
});

let initPromise: Promise<boolean> | undefined;

export const initializeKeycloak = async (): Promise<boolean> => {
  if (!initPromise) {
    initPromise = keycloak.init({
      onLoad: 'check-sso',
      pkceMethod: 'S256',
      checkLoginIframe: false,
    }).catch((error: unknown) => {
      initPromise = undefined;
      throw error;
    });
  }

  return initPromise;
};

export const getAccessToken = async (): Promise<string | null> => {
  if (!keycloak.authenticated) {
    return null;
  }

  try {
    await keycloak.updateToken(30);
    return keycloak.token ?? null;
  } catch {
    keycloak.clearToken();
    return null;
  }
};

export default keycloak;
