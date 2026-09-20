import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useState, type ChangeEvent } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  getAppointments,
  deleteAppointment,
  getAppointmentServices,
  type Appointment,
  type AppointmentStatus,
} from '../../api/appointmentapi';
import './AppointmentList.css';

const STATUS_LABELS: Record<AppointmentStatus, string> = {
  SCHEDULED: 'Scheduled',
  IN_PROGRESS: 'In Progress',
  COMPLETED: 'Completed',
  CANCELLED: 'Cancelled',
};

const PAGE_SIZE = 10;

function AppointmentList() {
  const [statusFilter, setStatusFilter] = useState<AppointmentStatus | ''>('');
  const [page, setPage] = useState(0);
  const [toast, setToast] = useState<string | null>(null);
  const [viewingAppointment, setViewingAppointment] = useState<Appointment | null>(null);
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { data, isLoading, isError, isSuccess } = useQuery({
    queryKey: ['appointments'],
    queryFn: getAppointments,
  });

  const {
    data: services = [],
    isLoading: isLoadingServices,
    isError: isServicesError,
  } = useQuery({
    queryKey: ['appointment-services', viewingAppointment?.id],
    queryFn: () => getAppointmentServices(viewingAppointment!.id),
    enabled: !!viewingAppointment,
  });

  const { mutate: cancelMutate } = useMutation({
    mutationFn: deleteAppointment,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['appointments'] });
      setToast('Appointment cancelled.');
      setTimeout(() => setToast(null), 3000);
    },
    onError: (err: unknown) => {
      console.error(err);
    },
  });

  const rows = data ?? [];
  const filtered = statusFilter ? rows.filter((r) => r.status === statusFilter) : rows;
  const pageCount = Math.ceil(filtered.length / PAGE_SIZE);
  const pageRows = filtered.slice(page * PAGE_SIZE, (page + 1) * PAGE_SIZE);

  const handleStatusFilterChange = (e: ChangeEvent<HTMLSelectElement>) => {
    setStatusFilter(e.target.value as AppointmentStatus | '');
    setPage(0);
  };

  return (
    <div className="appointment-list">
      <div className="appointment-list__toolbar">
        <select
          className="appt-filter-select"
          value={statusFilter}
          onChange={handleStatusFilterChange}
          aria-label="Filter by status"
        >
          <option value="">All Statuses</option>
          <option value="SCHEDULED">Scheduled</option>
          <option value="IN_PROGRESS">In Progress</option>
          <option value="COMPLETED">Completed</option>
          <option value="CANCELLED">Cancelled</option>
        </select>
        <button className="btn btn--primary" onClick={() => navigate('/appointments/new')}>
          + Schedule Appointment
        </button>
      </div>

      {isLoading && <span className="appointment-list__status">Loading…</span>}
      {isError  && <span className="appointment-list__status appointment-list__status--error">Error loading appointments.</span>}

      {isSuccess && (
        <>
          <div className="appointment-table-wrapper">
            <table className="appointment-table">
              <thead>
                <tr>
                  <th>Date</th>
                  <th>Customer</th>
                  <th>Vehicle</th>
                  <th>Staff</th>
                  <th>Status</th>
                  <th className="col-actions">Actions</th>
                </tr>
              </thead>
              <tbody>
                {pageRows.map((row) => (
                  <tr key={row.id}>
                    <td className="td-date">{row.appointmentDate}</td>
                    <td>{row.customerName}</td>
                    <td>{row.vehicleDescription}</td>
                    <td>{row.staffName ?? '—'}</td>
                    <td>
                      <span className={`status-badge status-badge--${(row.status ?? '').toLowerCase().replace('_', '-')}`}>
                        {STATUS_LABELS[row.status] ?? row.status}
                      </span>
                    </td>
                    <td className="col-actions">
                      <button
                        className="btn btn--view"
                        onClick={() => setViewingAppointment(row)}
                      >
                        View Services
                      </button>
                      <button
                        className="btn btn--cancel"
                        disabled={row.status !== 'SCHEDULED'}
                        onClick={() => {
                          if (window.confirm('Cancel this appointment?')) {
                            cancelMutate(row.id);
                          }
                        }}
                      >
                        Cancel
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>

            {filtered.length === 0 && (
              <p className="appointment-list__empty">No appointments found.</p>
            )}
          </div>

          {pageCount > 1 && (
            <div className="pagination">
              <button disabled={page === 0} onClick={() => setPage((p) => p - 1)}>
                &lsaquo; Prev
              </button>
              <span>Page {page + 1} of {pageCount}</span>
              <button disabled={page >= pageCount - 1} onClick={() => setPage((p) => p + 1)}>
                Next &rsaquo;
              </button>
            </div>
          )}
        </>
      )}

      {toast && <div className="toast">{toast}</div>}

      {viewingAppointment && (
        <div className="modal-overlay" onClick={() => setViewingAppointment(null)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <div className="service-view">
              <h2 className="service-view__title">
                Services for {viewingAppointment.vehicleDescription}
              </h2>
              <p className="service-view__meta">
                {viewingAppointment.customerName} &middot; {viewingAppointment.appointmentDate} &middot;{' '}
                <span className={`status-badge status-badge--${(viewingAppointment.status ?? '').toLowerCase().replace('_', '-')}`}>
                  {STATUS_LABELS[viewingAppointment.status] ?? viewingAppointment.status}
                </span>
              </p>

              {isLoadingServices && (
                <p className="service-view__status">Loading services…</p>
              )}
              {isServicesError && (
                <p className="service-view__status service-view__status--error">
                  Failed to load services.
                </p>
              )}

              {!isLoadingServices && !isServicesError && (
                <ul className="service-view__list">
                  {services.map((s) => (
                    <li key={s.id} className="service-view__item">
                      <span className="service-view__name">{s.serviceTypeName}</span>
                      {s.estimatedTimeHours ? (
                        <span className="service-view__hours">
                          ~{s.estimatedTimeHours} hr{s.estimatedTimeHours === 1 ? '' : 's'}
                        </span>
                      ) : null}
                    </li>
                  ))}
                  {services.length === 0 && (
                    <li className="service-view__status">No services recorded for this appointment.</li>
                  )}
                </ul>
              )}

              <div className="service-view__actions">
                <button className="btn btn--outline" onClick={() => setViewingAppointment(null)}>
                  Close
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default AppointmentList;
