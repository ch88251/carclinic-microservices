import { useState, type ChangeEvent, type FormEvent } from 'react';
import type { NewStaff } from '../../api/staffapi';
import './AddStaff.css';

const initialState: NewStaff = {
  firstName: '',
  lastName: '',
  email: '',
  phoneNumber: '',
  role: '',
};

interface AddStaffProps {
  onAdd: (staff: NewStaff) => void;
}

const AddStaff = ({ onAdd }: AddStaffProps) => {
  const [staff, setStaff] = useState<NewStaff>(initialState);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setStaff((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    onAdd(staff);
    setStaff(initialState);
  };

  return (
    <div className="add-staff">
      <h2 className="add-staff__title">Add New Staff Member</h2>
      <form className="add-staff__form" onSubmit={handleSubmit}>
        <div className="form-field">
          <label htmlFor="firstName">First Name</label>
          <input
            id="firstName"
            name="firstName"
            type="text"
            value={staff.firstName}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="lastName">Last Name</label>
          <input
            id="lastName"
            name="lastName"
            type="text"
            value={staff.lastName}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="email">Email</label>
          <input
            id="email"
            name="email"
            type="email"
            value={staff.email}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="phoneNumber">Phone Number</label>
          <input
            id="phoneNumber"
            name="phoneNumber"
            type="tel"
            value={staff.phoneNumber}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="role">Role</label>
          <input
            id="role"
            name="role"
            type="text"
            value={staff.role}
            onChange={handleChange}
            required
          />
        </div>
        <div className="add-staff__actions">
          <button type="submit" className="btn--save">Save</button>
        </div>
      </form>
    </div>
  );
};

export default AddStaff;
