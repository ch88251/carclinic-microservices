import { QueryClientProvider, QueryClient, useQuery, useMutation } from '@tanstack/react-query';
import { useState, type ChangeEvent, type FormEvent } from 'react';
import { getOwners } from '../../api/appointmentapi';
import { getVehicles } from '../../api/vehicleapi';
import { getServiceTypes, bookServices } from '../../api/serviceapi';
import './ServicesPage.css';

const queryClient = new QueryClient();

export default function ServicesPage() {
  return (
    <QueryClientProvider client={queryClient}>
      <ServiceBookingForm />
    </QueryClientProvider>
  );
}

const today = () => new Date().toISOString().slice(0, 10);

interface ServiceForm {
  customerId: string;
  vehicleId: string;
  appointmentDate: string;
  notes: string;
}

const initialForm: ServiceForm = {
  customerId: '',
  vehicleId: '',
  appointmentDate: today(),
  notes: '',
};

function ServiceBookingForm() {
  const [form, setForm] = useState<ServiceForm>(initialForm);
  const [selectedServiceIds, setSelectedServiceIds] = useState<number[]>([]);
  const [toast, setToast] = useState<string | null>(null);

  const { data: owners = [] } = useQuery({ queryKey: ['owners'], queryFn: getOwners });
  const { data: vehicles = [] } = useQuery({ queryKey: ['vehicles'], queryFn: getVehicles });
  const { data: serviceTypes = [], isLoading: isLoadingServices, isError: isServicesError } = useQuery({
    queryKey: ['service-types'],
    queryFn: getServiceTypes,
  });

  const filteredVehicles = form.customerId
    ? vehicles.filter((v) => v.ownerId === Number(form.customerId))
    : [];

  const { mutate, isPending, isError } = useMutation({
    mutationFn: bookServices,
    onSuccess: (data) => {
      const count = data.services?.length ?? selectedServiceIds.length;
      setForm(initialForm);
      setSelectedServiceIds([]);
      setToast(`Added ${count} service${count === 1 ? '' : 's'} to ${data.vehicleDescription ?? 'the vehicle'}.`);
      setTimeout(() => setToast(null), 3500);
    },
    onError: (err: unknown) => {
      console.error(err);
    },
  });

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setForm((prev) => {
      const next = { ...prev, [name]: value };
      if (name === 'customerId') next.vehicleId = '';
      return next;
    });
  };

  const toggleService = (id: number) => {
    setSelectedServiceIds((prev) =>
      prev.includes(id) ? prev.filter((existing) => existing !== id) : [...prev, id]
    );
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    mutate({
      vehicleId: Number(form.vehicleId),
      appointmentDate: form.appointmentDate,
      notes: form.notes || null,
      serviceTypeIds: selectedServiceIds,
    });
  };

  const canSubmit = form.vehicleId && selectedServiceIds.length > 0 && !isPending;

  return (
    <div className="services-page">
      <div className="services-page__header">
        <h1 className="services-page__title">Services</h1>
        <p className="services-page__subtitle">
          Select the services performed and apply them to a customer&rsquo;s vehicle.
        </p>
      </div>

      <form className="services-form" onSubmit={handleSubmit} noValidate>

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
        </section>

        <section className="form-section">
          <h2 className="form-section__title">
            <span className="form-section__num">2</span>
            Services Performed
          </h2>

          {isLoadingServices && <p className="services-checklist__status">Loading services…</p>}
          {isServicesError && (
            <p className="services-checklist__status services-checklist__status--error">
              Failed to load services.
            </p>
          )}

          {!isLoadingServices && !isServicesError && (
            <ul className="services-checklist">
              {serviceTypes.map((s) => (
                <li key={s.id} className="services-checklist__item">
                  <label>
                    <input
                      type="checkbox"
                      checked={selectedServiceIds.includes(s.id)}
                      onChange={() => toggleService(s.id)}
                    />
                    <span className="services-checklist__name">{s.name}</span>
                    {s.estimatedTimeHours ? (
                      <span className="services-checklist__meta">
                        ~{s.estimatedTimeHours} hr{s.estimatedTimeHours === 1 ? '' : 's'}
                      </span>
                    ) : null}
                  </label>
                </li>
              ))}
              {serviceTypes.length === 0 && (
                <li className="services-checklist__status">No services available.</li>
              )}
            </ul>
          )}

          <div className="form-field form-field--full">
            <label className="form-label" htmlFor="notes">Notes</label>
            <textarea
              id="notes"
              name="notes"
              className="form-textarea"
              value={form.notes}
              onChange={handleChange}
              rows={3}
              placeholder="Any additional details about the work performed…"
            />
          </div>
        </section>

        {isError && (
          <p className="form-error">Failed to add services. Please try again.</p>
        )}

        <div className="form-actions">
          <button type="submit" className="btn btn--primary" disabled={!canSubmit}>
            {isPending ? 'Adding…' : 'Add Services'}
          </button>
        </div>

      </form>

      {toast && <div className="toast">{toast}</div>}
    </div>
  );
}
