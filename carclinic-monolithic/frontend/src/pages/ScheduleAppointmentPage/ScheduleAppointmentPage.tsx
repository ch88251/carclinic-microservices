import { QueryClientProvider, QueryClient, useQuery, useMutation } from '@tanstack/react-query';
import { useState, type ChangeEvent, type FormEvent } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { addAppointment, getOwners, getStaff } from '../../api/appointmentapi';
import { getVehicles } from '../../api/vehicleapi';
import './ScheduleAppointmentPage.css';

const queryClient = new QueryClient();

export default function ScheduleAppointmentPage() {
  return (
    <QueryClientProvider client={queryClient}>
      <ScheduleAppointmentForm />
    </QueryClientProvider>
  );
}

interface ScheduleForm {
  customerId: string;
  vehicleId: string;
  appointmentDate: string;
  staffId: string;
  notes: string;
}

const initialForm: ScheduleForm = {
  customerId: '',
  vehicleId: '',
  appointmentDate: '',
  staffId: '',
  notes: '',
};

function ScheduleAppointmentForm() {
  const [form, setForm] = useState<ScheduleForm>(initialForm);
  const navigate = useNavigate();

  const { data: owners = [] } = useQuery({ queryKey: ['owners'], queryFn: getOwners });
  const { data: vehicles = [] } = useQuery({ queryKey: ['vehicles'], queryFn: getVehicles });
  const { data: staff = [] } = useQuery({ queryKey: ['staff'], queryFn: getStaff });

  const filteredVehicles = form.customerId
    ? vehicles.filter((v) => v.ownerId === Number(form.customerId))
    : [];

  const { mutate, isPending, isError } = useMutation({
    mutationFn: addAppointment,
    onSuccess: () => navigate('/appointments'),
    onError: (err: unknown) => console.error(err),
  });

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setForm((prev) => {
      const next = { ...prev, [name]: value };
      if (name === 'customerId') next.vehicleId = '';
      return next;
    });
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    mutate({
      customerId: Number(form.customerId),
      vehicleId: Number(form.vehicleId),
      staffId: form.staffId ? Number(form.staffId) : null,
      appointmentDate: form.appointmentDate,
      status: 'SCHEDULED',
      notes: form.notes || null,
    });
  };

  return (
    <div className="schedule-page">
      <div className="schedule-page__header">
        <Link to="/appointments" className="back-link">← Appointments</Link>
        <h1 className="schedule-page__title">Schedule Appointment</h1>
      </div>

      <form className="schedule-form" onSubmit={handleSubmit} noValidate>

        <section className="form-section">
          <h2 className="form-section__title">
            <span className="form-section__num">1</span>
            Customer &amp; Vehicle
          </h2>
          <div className="form-row">
            <div className="form-field">
              <label className="form-label" htmlFor="customerId">
                Customer <span className="required">*</span>
              </label>
              <select
                id="customerId"
                name="customerId"
                className="form-select"
                value={form.customerId}
                onChange={handleChange}
                required
              >
                <option value="">Select customer…</option>
                {owners.map((o) => (
                  <option key={o.id} value={o.id}>
                    {o.firstName} {o.lastName}
                  </option>
                ))}
              </select>
            </div>

            <div className="form-field">
              <label className="form-label" htmlFor="vehicleId">
                Vehicle <span className="required">*</span>
              </label>
              <select
                id="vehicleId"
                name="vehicleId"
                className="form-select"
                value={form.vehicleId}
                onChange={handleChange}
                required
                disabled={!form.customerId}
              >
                <option value="">
                  {form.customerId ? 'Select vehicle…' : 'Select a customer first…'}
                </option>
                {filteredVehicles.map((v) => (
                  <option key={v.id} value={v.id}>
                    {v.year} {v.make} {v.model}
                  </option>
                ))}
              </select>
            </div>
          </div>
        </section>

        <section className="form-section">
          <h2 className="form-section__title">
            <span className="form-section__num">2</span>
            Appointment Details
          </h2>
          <div className="form-row">
            <div className="form-field">
              <label className="form-label" htmlFor="appointmentDate">
                Date <span className="required">*</span>
              </label>
              <input
                type="date"
                id="appointmentDate"
                name="appointmentDate"
                className="form-input"
                value={form.appointmentDate}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-field">
              <label className="form-label" htmlFor="staffId">Assigned Staff</label>
              <select
                id="staffId"
                name="staffId"
                className="form-select"
                value={form.staffId}
                onChange={handleChange}
              >
                <option value="">Select staff member…</option>
                {staff.map((s) => (
                  <option key={s.id} value={s.id}>
                    {s.firstName} {s.lastName} – {s.role}
                  </option>
                ))}
              </select>
            </div>
          </div>

          <div className="form-field form-field--full">
            <label className="form-label" htmlFor="notes">Notes</label>
            <textarea
              id="notes"
              name="notes"
              className="form-textarea"
              value={form.notes}
              onChange={handleChange}
              rows={3}
              placeholder="Any additional details about the appointment…"
            />
          </div>
        </section>

        {isError && (
          <p className="form-error">Failed to schedule appointment. Please try again.</p>
        )}

        <div className="form-actions">
          <Link to="/appointments" className="btn btn--outline">Cancel</Link>
          <button type="submit" className="btn btn--primary" disabled={isPending}>
            {isPending ? 'Scheduling…' : 'Schedule Appointment'}
          </button>
        </div>

      </form>
    </div>
  );
}
