import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useState } from 'react';
import AddStaff from '../AddStaff/AddStaff';
import { getStaff, addStaff, deleteStaff, type NewStaff } from '../../api/staffapi';
import './StaffList.css';

const PAGE_SIZE = 10;

function StaffList() {
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [addDialogOpen, setAddDialogOpen] = useState(false);
  const [page, setPage] = useState(0);

  const queryClient = useQueryClient();
  const { data, isError, isLoading, isSuccess } = useQuery({
    queryKey: ['staff'],
    queryFn: getStaff,
  });

  const { mutate: deleteMutate } = useMutation({
    mutationFn: deleteStaff,
    onSuccess: () => {
      setSnackbarOpen(true);
      queryClient.invalidateQueries({ queryKey: ['staff'] });
      setTimeout(() => setSnackbarOpen(false), 3000);
    },
    onError: (err: unknown) => {
      console.error(err);
    },
  });

  const { mutate: addMutate } = useMutation({
    mutationFn: addStaff,
    onSuccess: () => {
      setAddDialogOpen(false);
      queryClient.invalidateQueries({ queryKey: ['staff'] });
    },
    onError: (err: unknown) => {
      console.error(err);
    },
  });

  const handleAddStaff = (staff: NewStaff) => {
    addMutate(staff);
  };

  const rows = data ?? [];
  const pageCount = Math.ceil(rows.length / PAGE_SIZE);
  const pageRows = rows.slice(page * PAGE_SIZE, (page + 1) * PAGE_SIZE);

  return (
    <div className="staff-list">
      <div className="staff-list__toolbar">
        <button
          className="btn btn--add"
          onClick={() => setAddDialogOpen(true)}
        >
          Add Staff
        </button>
      </div>

      {addDialogOpen && (
        <div className="modal-overlay" onClick={() => setAddDialogOpen(false)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <AddStaff onAdd={handleAddStaff} />
          </div>
        </div>
      )}

      {isLoading ? (
        <span className="staff-list__status">Loading...</span>
      ) : isError ? (
        <span className="staff-list__status">Error loading data</span>
      ) : isSuccess ? (
        <div className="staff-table-wrapper">
          <table className="staff-table">
            <thead>
              <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Phone Number</th>
                <th>Role</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {pageRows.map((row) => (
                <tr key={row.id}>
                  <td>{row.firstName}</td>
                  <td>{row.lastName}</td>
                  <td>{row.email}</td>
                  <td>{row.phoneNumber}</td>
                  <td>{row.role}</td>
                  <td className="actions">
                    <button
                      className="btn btn--icon btn--delete"
                      aria-label="delete"
                      onClick={() => {
                        if (window.confirm(`Are you sure you want to remove ${row.firstName} ${row.lastName}?`)) {
                          deleteMutate(row.id);
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
        <div className="toast">Staff member deleted</div>
      )}
    </div>
  );
}

export default StaffList;
