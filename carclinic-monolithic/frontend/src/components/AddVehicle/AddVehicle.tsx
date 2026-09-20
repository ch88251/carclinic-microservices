import { useState, type ChangeEvent, type FormEvent } from 'react';
import type { NewVehicle } from '../../api/vehicleapi';
import './AddVehicle.css';

const initialState: NewVehicle = {
  vin: '',
  make: '',
  model: '',
  color: '',
  year: 2025,
  mileage: 0,
  lastServiceDate: '',
  nextServiceDate: '',
};

interface AddVehicleProps {
  onAdd: (vehicle: NewVehicle) => void;
}

const AddVehicle = ({ onAdd }: AddVehicleProps) => {
  const [vehicle, setVehicle] = useState<NewVehicle>(initialState);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setVehicle((prev) => ({ ...prev, [name]: value }));
  };

  const handleNumberChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setVehicle((prev) => ({ ...prev, [name]: Number(value) }));
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    onAdd(vehicle);
    setVehicle(initialState);
  };

  return (
    <div className="add-vehicle">
      <h2 className="add-vehicle__title">Add New Vehicle</h2>
      <form
        data-testid="vehicle-form"
        className="add-vehicle__form"
        onSubmit={handleSubmit}
      >
        <div className="form-field">
          <label htmlFor="vin">VIN</label>
          <input
            data-testid="txt-vin"
            id="vin"
            name="vin"
            type="text"
            value={vehicle.vin}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="make">Make</label>
          <input
            data-testid="make"
            id="make"
            name="make"
            type="text"
            value={vehicle.make}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="model">Model</label>
          <input
            data-testid="model"
            id="model"
            name="model"
            type="text"
            value={vehicle.model}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="color">Color</label>
          <input
            data-testid="color"
            id="color"
            name="color"
            type="text"
            value={vehicle.color}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="year">Year</label>
          <input
            data-testid="year"
            id="year"
            name="year"
            type="number"
            value={vehicle.year}
            onChange={handleNumberChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="mileage">Mileage</label>
          <input
            data-testid="mileage"
            id="mileage"
            name="mileage"
            type="number"
            value={vehicle.mileage}
            onChange={handleNumberChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="lastServiceDate">Last Service Date</label>
          <input
            data-testid="lastServiceDate"
            id="lastServiceDate"
            name="lastServiceDate"
            type="date"
            value={vehicle.lastServiceDate}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="nextServiceDate">Next Service Date</label>
          <input
            data-testid="nextServiceDate"
            id="nextServiceDate"
            name="nextServiceDate"
            type="date"
            value={vehicle.nextServiceDate}
            onChange={handleChange}
            required
          />
        </div>
        <div className="add-vehicle__actions">
          <button
            data-testid="save-vehicle-button"
            type="submit"
            className="btn--save"
          >
            Save Vehicle
          </button>
        </div>
      </form>
    </div>
  );
};

export default AddVehicle;
