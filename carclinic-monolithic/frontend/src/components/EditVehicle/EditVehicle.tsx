import { useState, type ChangeEvent, type FormEvent } from 'react';
import type { Vehicle, VehicleUpdate } from '../../api/vehicleapi';
import '../AddVehicle/AddVehicle.css';

interface EditVehicleProps {
  vehicle: Vehicle;
  onSave: (id: number, fields: VehicleUpdate) => void;
}

const EditVehicle = ({ vehicle, onSave }: EditVehicleProps) => {
  const [fields, setFields] = useState<VehicleUpdate>({
    vin: vehicle.vin,
    make: vehicle.make,
    model: vehicle.model,
    color: vehicle.color,
    year: vehicle.year,
    mileage: vehicle.mileage,
    lastServiceDate: vehicle.lastServiceDate,
    nextServiceDate: vehicle.nextServiceDate,
    ownerId: vehicle.ownerId,
  });

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFields((prev) => ({ ...prev, [name]: value }));
  };

  const handleNumberChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFields((prev) => ({ ...prev, [name]: Number(value) }));
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    onSave(vehicle.id, fields);
  };

  return (
    <div className="add-vehicle">
      <h2 className="add-vehicle__title">Edit Vehicle</h2>
      <form
        data-testid="edit-vehicle-form"
        className="add-vehicle__form"
        onSubmit={handleSubmit}
      >
        <div className="form-field">
          <label htmlFor="vin">VIN</label>
          <input
            data-testid="edit-txt-vin"
            id="vin"
            name="vin"
            type="text"
            value={fields.vin}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="make">Make</label>
          <input
            data-testid="edit-make"
            id="make"
            name="make"
            type="text"
            value={fields.make}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="model">Model</label>
          <input
            data-testid="edit-model"
            id="model"
            name="model"
            type="text"
            value={fields.model}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="color">Color</label>
          <input
            data-testid="edit-color"
            id="color"
            name="color"
            type="text"
            value={fields.color}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="year">Year</label>
          <input
            data-testid="edit-year"
            id="year"
            name="year"
            type="number"
            value={fields.year}
            onChange={handleNumberChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="mileage">Mileage</label>
          <input
            data-testid="edit-mileage"
            id="mileage"
            name="mileage"
            type="number"
            value={fields.mileage}
            onChange={handleNumberChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="lastServiceDate">Last Service Date</label>
          <input
            data-testid="edit-lastServiceDate"
            id="lastServiceDate"
            name="lastServiceDate"
            type="date"
            value={fields.lastServiceDate}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="nextServiceDate">Next Service Date</label>
          <input
            data-testid="edit-nextServiceDate"
            id="nextServiceDate"
            name="nextServiceDate"
            type="date"
            value={fields.nextServiceDate}
            onChange={handleChange}
            required
          />
        </div>
        <div className="add-vehicle__actions">
          <button
            data-testid="update-vehicle-button"
            type="submit"
            className="btn--save"
          >
            Update Vehicle
          </button>
        </div>
      </form>
    </div>
  );
};

export default EditVehicle;
