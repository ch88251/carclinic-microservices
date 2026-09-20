import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import AppLayout from '../AppLayout/AppLayout';
import PrivateRoute from '../../auth/PrivateRoute';
import LandingPage from '../../pages/LandingPage/LandingPage';
import VehicleListPage from '../../pages/VehicleListPage/VehicleListPage';
import AppointmentListPage from '../../pages/AppointmentListPage/AppointmentListPage';
import ScheduleAppointmentPage from '../../pages/ScheduleAppointmentPage/ScheduleAppointmentPage';
import StaffListPage from '../../pages/StaffListPage/StaffListPage';
import ServicesPage from '../../pages/ServicesPage/ServicesPage';
import './App.css';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<AppLayout />}>
          <Route path="/" element={<LandingPage />} />
          <Route path="/vehicles" element={<PrivateRoute><VehicleListPage /></PrivateRoute>} />
          <Route path="/appointments" element={<PrivateRoute><AppointmentListPage /></PrivateRoute>} />
          <Route path="/appointments/new" element={<PrivateRoute><ScheduleAppointmentPage /></PrivateRoute>} />
          <Route path="/staff" element={<PrivateRoute><StaffListPage /></PrivateRoute>} />
          <Route path="/services" element={<PrivateRoute><ServicesPage /></PrivateRoute>} />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
