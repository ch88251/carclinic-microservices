import apiClient from './httpClient';
import type { Staff } from './staffapi';

export type AppointmentStatus = 'SCHEDULED' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED';

export interface Appointment {
  id: number;
  appointmentDate: string;
  customerName: string;
  vehicleDescription: string;
  staffName: string | null;
  status: AppointmentStatus;
}

export interface AppointmentService {
  id: number;
  serviceTypeName: string;
  estimatedTimeHours: number | null;
}

export interface Owner {
  id: number;
  firstName: string;
  lastName: string;
}

export interface NewAppointment {
  customerId: number;
  vehicleId: number;
  staffId: number | null;
  appointmentDate: string;
  status: AppointmentStatus;
  notes: string | null;
}

export const getAppointments = async (): Promise<Appointment[]> => {
  const response = await apiClient.get<Appointment[]>('/api/appointments');
  return response.data;
};

export const addAppointment = async (appointment: NewAppointment): Promise<Appointment> => {
  const response = await apiClient.post<Appointment>('/api/appointments', appointment, {
    headers: { 'Content-Type': 'application/json' },
  });
  return response.data;
};

export const deleteAppointment = async (id: number): Promise<void> => {
  await apiClient.delete(`/api/appointments/${id}`);
};

export const getAppointmentServices = async (id: number): Promise<AppointmentService[]> => {
  const response = await apiClient.get<AppointmentService[]>(`/api/appointments/${id}/services`);
  return response.data;
};

export const getOwners = async (): Promise<Owner[]> => {
  const response = await apiClient.get<Owner[]>('/api/owners');
  return response.data;
};

export const getStaff = async (): Promise<Staff[]> => {
  const response = await apiClient.get<Staff[]>('/api/staff');
  return response.data;
};
