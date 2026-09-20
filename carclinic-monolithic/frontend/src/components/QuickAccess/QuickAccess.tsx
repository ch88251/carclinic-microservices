import { Link } from 'react-router-dom';
import type { ReactNode } from 'react';
import './QuickAccess.css';

interface QuickAccessCard {
  key: string;
  title: string;
  desc: string;
  colorMod: string;
  to?: string;
  icon: ReactNode;
}

const CARDS: QuickAccessCard[] = [
  {
    key: 'vehicles',
    title: 'Vehicles',
    desc: 'View and manage the fleet of registered vehicles.',
    colorMod: 'blue',
    to: '/vehicles',
    icon: (
      <path d="M18.92 6.01C18.72 5.42 18.16 5 17.5 5h-11c-.66 0-1.21.42-1.42 1.01L3 12v8c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-1h12v1c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-8l-2.08-5.99zM6.85 7h10.29l1.08 3.11H5.77L6.85 7zM19 17H5v-5h14v5zm-2.5-2c-.83 0-1.5-.67-1.5-1.5S15.67 12 16.5 12s1.5.67 1.5 1.5S17.33 15 16.5 15zm-9 0c-.83 0-1.5-.67-1.5-1.5S6.67 12 7.5 12 9 12.67 9 13.5 8.33 15 7.5 15z" />
    ),
  },
  {
    key: 'appointments',
    title: 'Appointments',
    desc: 'Schedule and track upcoming service appointments.',
    colorMod: 'green',
    icon: (
      <path d="M17 12h-5v5h5v-5zM16 1v2H8V1H6v2H5c-1.11 0-1.99.9-1.99 2L3 19c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2h-1V1h-2zm3 18H5V8h14v11z" />
    ),
  },
  {
    key: 'services',
    title: 'Services',
    desc: 'Record the services performed on a customer’s vehicle.',
    colorMod: 'orange',
    to: '/services',
    icon: (
      <path d="M22.7 19l-9.1-9.1c.9-2.3.4-5-1.5-6.9-2-2-5-2.4-7.4-1.3L9 6 6 9 1.6 4.7C.4 7.1.9 10.1 2.9 12.1c1.9 1.9 4.6 2.4 6.9 1.5l9.1 9.1c.4.4 1 .4 1.4 0l2.3-2.3c.5-.4.5-1.1.1-1.4z" />
    ),
  },
  {
    key: 'staff',
    title: 'Staff',
    desc: 'Manage mechanic and staff profiles and assignments.',
    colorMod: 'purple',
    to: '/staff',
    icon: (
      <path d="M16 11c1.66 0 2.99-1.34 2.99-3S17.66 5 16 5c-1.66 0-3 1.34-3 3s1.34 3 3 3zm-8 0c1.66 0 2.99-1.34 2.99-3S9.66 5 8 5C6.34 5 5 6.34 5 8s1.34 3 3 3zm0 2c-2.33 0-7 1.17-7 3.5V19h14v-2.5c0-2.33-4.67-3.5-7-3.5zm8 0c-.29 0-.62.02-.97.05 1.16.84 1.97 1.97 1.97 3.45V19h6v-2.5c0-2.33-4.67-3.5-7-3.5z" />
    ),
  },
];

function QuickAccess() {
  return (
    <section className="quick-access">
      <h2 className="quick-access__title">Quick Access</h2>
      <div className="quick-access__grid">
        {CARDS.map(({ key, title, desc, colorMod, icon, to }) => {
          const content = (
            <>
              <div className={`qa-card__icon qa-card__icon--${colorMod}`}>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
                  {icon}
                </svg>
              </div>
              <h3 className="qa-card__title">{title}</h3>
              <p className="qa-card__desc">{desc}</p>
              <span className="qa-card__link" aria-hidden="true">Open →</span>
            </>
          );

          return to ? (
            <Link key={key} to={to} className="qa-card">
              {content}
            </Link>
          ) : (
            <a key={key} href="#" className="qa-card">
              {content}
            </a>
          );
        })}
      </div>
    </section>
  );
}

export default QuickAccess;
