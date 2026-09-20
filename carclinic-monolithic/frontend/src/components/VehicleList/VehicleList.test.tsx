import { render, screen } from '@testing-library/react';
import VehicleList from './VehicleList';
import { describe, expect, it, vi } from 'vitest';

// Create a mock function for useQuery
const useQueryMock = vi.fn();

// Mock the module and use the mock function
vi.mock('@tanstack/react-query', () => ({
  useQuery: (...args: unknown[]) => useQueryMock(...args),
  useMutation: () => ({ mutate: vi.fn() }),
  useQueryClient: () => ({ invalidateQueries: vi.fn() }),
}));

describe('VehicleList', () => {

  it('renders Add Vehicle button', () => {
    useQueryMock.mockReturnValue({ data: [], isLoading: false, isError: false, isSuccess: true });
    render(<VehicleList />);
    expect(screen.getByText(/Add Vehicle/i)).toBeInTheDocument();
  });

  it('renders table rows when data is present', () => {
    useQueryMock.mockReturnValue({
      data: [
        {
          id: 1,
          vin: '1HGCM82633A123456',
          make: 'Honda',
          model: 'Accord',
          color: 'Blue',
          year: 2020,
          mileage: 15000,
          lastServiceDate: '2023-01-15',
          nextServiceDate: '2023-07-15',
        },
      ],
      isLoading: false,
      isError: false,
      isSuccess: true,
    });

    render(<VehicleList />);
    expect(screen.getByText('Honda')).toBeInTheDocument();
    expect(screen.getByText('Accord')).toBeInTheDocument();
  });
});
