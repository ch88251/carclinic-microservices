import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useState } from 'react';
import AddVehicle from '../AddVehicle/AddVehicle';
import EditVehicle from '../EditVehicle/EditVehicle';
import {
  getVehicles,
  deleteVehicle,
  addVehicle,
  updateVehicle,
  type Vehicle,
  type NewVehicle,
  type VehicleUpdate,
} from '../../api/vehicleapi';
import './VehicleList.css';

const PAGE_SIZE = 10;

function VehicleList() {
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [addDialogOpen, setAddDialogOpen] = useState(false);
  const [editingVehicle, setEditingVehicle] = useState<Vehicle | null>(null);
  const [page, setPage] = useState(0);

  const queryClient = useQueryClient();
  const { data, isError, isLoading, isSuccess } = useQuery({
    queryKey: ['vehicles'],
    queryFn: getVehicles,
  });

  const { mutate } = useMutation({
    mutationFn: deleteVehicle,
    onSuccess: () => {
      setSnackbarOpen(true);
      queryClient.invalidateQueries({ queryKey: ['vehicles'] });
      setTimeout(() => setSnackbarOpen(false), 3000);
    },
    onError: (err: unknown) => {
      console.error(err);
    },
  });

  const { mutate: addMutate } = useMutation({
    mutationFn: addVehicle,
    onSuccess: () => {
      setAddDialogOpen(false);
      queryClient.invalidateQueries({ queryKey: ['vehicles'] });
    },
    onError: (err: unknown) => {
      console.error(err);
    },
  });

  const { mutate: updateMutate } = useMutation({
    mutationFn: ({ id, fields }: { id: number; fields: VehicleUpdate }) => updateVehicle(id, fields),
    onSuccess: () => {
      setEditingVehicle(null);
      queryClient.invalidateQueries({ queryKey: ['vehicles'] });
    },
    onError: (err: unknown) => {
      console.error(err);
    },
  });

  const handleAddVehicle = (vehicle: NewVehicle) => {
    addMutate(vehicle);
  };

  const handleUpdateVehicle = (id: number, fields: VehicleUpdate) => {
    updateMutate({ id, fields });
  };

  const rows = data ?? [];
  const pageCount = Math.ceil(rows.length / PAGE_SIZE);
  const pageRows = rows.slice(page * PAGE_SIZE, (page + 1) * PAGE_SIZE);

  return (
    <div className="vehicle-list">
      <div className="vehicle-list__toolbar">
        <button
          data-testid="add-vehicle-button"
          className="btn btn--add"
          onClick={() => setAddDialogOpen(true)}
        >
          Add Vehicle
        </button>
      </div>

      {addDialogOpen && (
        <div className="modal-overlay" onClick={() => setAddDialogOpen(false)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <AddVehicle onAdd={handleAddVehicle} />
          </div>
        </div>
      )}

      {editingVehicle && (
        <div className="modal-overlay" onClick={() => setEditingVehicle(null)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <EditVehicle vehicle={editingVehicle} onSave={handleUpdateVehicle} />
          </div>
        </div>
      )}

      {isLoading ? (
        <span className="vehicle-list__status">Loading...</span>
      ) : isError ? (
        <span className="vehicle-list__status">Error loading data</span>
      ) : isSuccess ? (
        <div className="vehicle-table-wrapper">
          <table className="vehicle-table">
            <thead>
              <tr>
                <th>VIN</th>
                <th>Make</th>
                <th>Model</th>
                <th>Color</th>
                <th>Year</th>
                <th>Mileage</th>
                <th>Last Service Date</th>
                <th>Next Service Date</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {pageRows.map((row) => (
                <tr key={row.id}>
                  <td>{row.vin}</td>
                  <td>{row.make}</td>
                  <td>{row.model}</td>
                  <td>{row.color}</td>
                  <td>{row.year}</td>
                  <td>{row.mileage}</td>
                  <td>{row.lastServiceDate}</td>
                  <td>{row.nextServiceDate}</td>
                  <td className="actions">
                    <button
                      className="btn btn--icon btn--edit"
                      aria-label="edit"
                      onClick={() => setEditingVehicle(row)}
                    >
                      ✏️
                    </button>
                    <button
                      data-testid="delete-vehicle-button"
                      className="btn btn--icon btn--delete"
                      aria-label="delete"
                      onClick={() => {
                        if (window.confirm('Are you sure you want to delete this vehicle?')) {
                          console.log(`Delete vehicle with ID: ${row.id}`);
                          mutate(row.id);
                        }
                      }}
                    >
                      🗑️
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          {pageCount > 1 && (
            <div className="pagination">
              <button disabled={page === 0} onClick={() => setPage((p) => p - 1)}>
                &lsaquo; Prev
              </button>
              <span>
                Page {page + 1} of {pageCount}
              </span>
              <button disabled={page >= pageCount - 1} onClick={() => setPage((p) => p + 1)}>
                Next &rsaquo;
              </button>
            </div>
          )}
        </div>
      ) : null}

      {snackbarOpen && (
        <div data-testid="snackbar-vehicle-deleted" className="toast">
          Vehicle deleted
        </div>
      )}
    </div>
  );
}

export default VehicleList;
