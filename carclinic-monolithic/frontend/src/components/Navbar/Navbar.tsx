import { NavLink } from 'react-router-dom';
import type { AuthUser } from '../../auth/AuthContext';
import './Navbar.css';

interface NavLinkItem {
  label: string;
  to?: string;
  href?: string;
}

// Links with a `to` value use NavLink (client-side routing).
// Links with only `href` are placeholders until those pages are built.
const NAV_LINKS: NavLinkItem[] = [
  { label: 'Home',         to: '/'         },
  { label: 'Vehicles',     to: '/vehicles' },
  { label: 'Appointments', to: '/appointments' },
  { label: 'Services',     to: '/services' },
  { label: 'Staff',        to: '/staff'    },
];

interface NavbarProps {
  user: AuthUser | null;
  onLogin: () => void;
  onLogout: () => void;
}

function Navbar({ user, onLogin, onLogout }: NavbarProps) {
  return (
    <header className="navbar">

      <div className="navbar__brand">
        <svg className="navbar__logo" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
          <path d="M22.7 19l-9.1-9.1c.9-2.3.4-5-1.5-6.9-2-2-5-2.4-7.4-1.3L9 6 6 9 1.6 4.7C.4 7.1.9 10.1 2.9 12.1c1.9 1.9 4.6 2.4 6.9 1.5l9.1 9.1c.4.4 1 .4 1.4 0l2.3-2.3c.5-.4.5-1.1.1-1.4z" />
        </svg>
        <span className="navbar__title">Car Clinic</span>
      </div>

      <nav className="navbar__menu" aria-label="Main navigation">
        {NAV_LINKS.map(({ label, to, href }) =>
          to ? (
            <NavLink
              key={label}
              to={to}
              className={({ isActive }) =>
                `navbar__link${isActive ? ' navbar__link--active' : ''}`
              }
              end={to === '/'}
            >
              {label}
            </NavLink>
          ) : (
            <a key={label} href={href} className="navbar__link">
              {label}
            </a>
          )
        )}
      </nav>

      <div className="navbar__actions">
        {user ? (
          <>
            <button className="profile-btn" aria-label={`Signed in as ${user.name}`}>
              <span className="profile-btn__avatar" aria-hidden="true">{user.initials}</span>
              <span className="profile-btn__name">{user.name}</span>
              <svg className="profile-btn__chevron" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
                <path d="M7 10l5 5 5-5z" />
              </svg>
            </button>

            <button className="btn btn--logout" onClick={onLogout}>
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="16" height="16" aria-hidden="true">
                <path d="M17 7l-1.41 1.41L18.17 11H8v2h10.17l-2.58 2.58L17 17l5-5-5-5zM4 5h8V3H4c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h8v-2H4V5z" />
              </svg>
              Logout
            </button>
          </>
        ) : (
          <button className="btn btn--login" onClick={onLogin}>Login</button>
        )}
      </div>

    </header>
  );
}

export default Navbar;
