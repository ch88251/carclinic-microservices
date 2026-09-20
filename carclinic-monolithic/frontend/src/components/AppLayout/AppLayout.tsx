import { Outlet } from 'react-router-dom';
import Navbar from '../Navbar/Navbar';
import Footer from '../Footer/Footer';
import { useAuth } from '../../auth/useAuth';
import './AppLayout.css';

function AppLayout() {
  const { user, login, logout } = useAuth();

  return (
    <div className="app-layout">
      <Navbar user={user} onLogin={login} onLogout={logout} />
      <main className="app-layout-content">
        <Outlet />
      </main>
      <Footer />
    </div>
  );
}

export default AppLayout;
