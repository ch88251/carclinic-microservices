import { QueryClientProvider, QueryClient } from '@tanstack/react-query';
import AppointmentList from '../../components/AppointmentList/AppointmentList';
import './AppointmentListPage.css';

const queryClient = new QueryClient();

function AppointmentListPage() {
  return (
    <QueryClientProvider client={queryClient}>
      <div className="appointment-list-page">
        <div className="appointment-list-page__header">
          <h1 className="appointment-list-page__title">Appointments</h1>
          <p className="appointment-list-page__subtitle">View and manage all service appointments.</p>
        </div>
        <AppointmentList />
      </div>
    </QueryClientProvider>
  );
}

export default AppointmentListPage;
