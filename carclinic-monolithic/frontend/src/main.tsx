import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App from './components/App/App';
import KeycloakProvider from './auth/KeycloakProvider';

const rootElement = document.getElementById('root');

if (!rootElement) {
  throw new Error('Root element not found');
}

createRoot(rootElement).render(
  <StrictMode>
    <KeycloakProvider>
      <App />
    </KeycloakProvider>
  </StrictMode>,
);
