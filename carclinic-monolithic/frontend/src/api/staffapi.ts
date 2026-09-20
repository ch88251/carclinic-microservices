import apiClient from './httpClient';

export interface Staff {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  role: string;
}

export type NewStaff = Omit<Staff, 'id'>;

export const getStaff = async (): Promise<Staff[]> => {
  const response = await apiClient.get<Staff[]>('/api/staff');
  return response.data;
};

export const addStaff = async (staff: NewStaff): Promise<Staff> => {
  const response = await apiClient.post<Staff>('/api/staff', staff, {
    headers: { 'Content-Type': 'application/json' },
  });
  return response.data;
};

export const deleteStaff = async (id: number): Promise<void> => {
  await apiClient.delete(`/api/staff/${id}`);
};
