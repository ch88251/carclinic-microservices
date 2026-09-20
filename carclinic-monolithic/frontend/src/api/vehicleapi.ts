import apiClient from './httpClient';

export interface Vehicle {
  id: number;
  vin: string;
  make: string;
  model: string;
  color: string;
  year: number;
  mileage: number;
  lastServiceDate: string;
  nextServiceDate: string;
  ownerId: number;
}

export type NewVehicle = Omit<Vehicle, 'id' | 'ownerId'>;
export type VehicleUpdate = Omit<Vehicle, 'id'>;

export const getVehicles = async (): Promise<Vehicle[]> => {
  const response = await apiClient.get<Vehicle[]>('/api/vehicles');
  return response.data;
};

export const deleteVehicle = async (id: number): Promise<void> => {
  await apiClient.delete(`/api/vehicles/${id}`);
};

export const addVehicle = async (vehicle: NewVehicle): Promise<Vehicle> => {
  const response = await apiClient.post<Vehicle>('/api/vehicles', vehicle, {
    headers: { 'Content-Type': 'application/json' },
  });
  return response.data;
};

export const updateVehicle = async (id: number, vehicle: VehicleUpdate): Promise<Vehicle> => {
  const response = await apiClient.put<Vehicle>(`/api/vehicles/${id}`, vehicle, {
    headers: {
      'Content-Type': 'application/json',
    },
  });
  return response.data;
};
