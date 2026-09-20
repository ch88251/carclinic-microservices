import apiClient from './httpClient';

export interface ServiceType {
  id: number;
  name: string;
  estimatedTimeHours: number | null;
}

export interface ServiceBookingRequest {
  vehicleId: number;
  appointmentDate: string;
  notes: string | null;
  serviceTypeIds: number[];
}

export interface ServiceBookingResponse {
  vehicleDescription?: string;
  services?: unknown[];
}

export const getServiceTypes = async (): Promise<ServiceType[]> => {
  const response = await apiClient.get<ServiceType[]>('/api/service-types');
  return response.data;
};

export const bookServices = async (booking: ServiceBookingRequest): Promise<ServiceBookingResponse> => {
  const response = await apiClient.post<ServiceBookingResponse>('/api/service-bookings', booking, {
    headers: { 'Content-Type': 'application/json' },
  });
  return response.data;
};
