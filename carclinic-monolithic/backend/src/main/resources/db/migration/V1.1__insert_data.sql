insert into owners (id, first_name, last_name, email, phone_number) values
(nextval('hibernate_sequence'), 'John', 'Doe', 'john.doe@example.com', '303-555-1234'),
(nextval('hibernate_sequence'), 'Jane', 'Smith', 'jane.smith@example.com', '303-555-2345'),
(nextval('hibernate_sequence'), 'Emily', 'Johnson', 'emily.johnson@example.com', '303-555-3456'),
(nextval('hibernate_sequence'), 'Michael', 'Williams', 'michael.williams@example.com', '303-555-4567'),
(nextval('hibernate_sequence'), 'Sarah', 'Brown', 'sarah.brown@example.com', '303-555-5678');

insert into staff (id, first_name, last_name, email, phone_number, role) values
(nextval('hibernate_sequence'), 'Alice', 'Davis', 'alice.davis@example.com', '303-555-6789', 'Technician'),
(nextval('hibernate_sequence'), 'Bob', 'Wilson', 'bob.wilson@example.com', '303-555-7890', 'Technician'),
(nextval('hibernate_sequence'), 'Carol', 'Miller', 'carol.miller@example.com', '303-555-8901', 'Service Advisor'),
(nextval('hibernate_sequence'), 'David', 'Moore', 'david.moore@example.com', '303-555-9012', 'Manager'),
(nextval('hibernate_sequence'), 'Eve', 'Taylor', 'eve.taylor@example.com', '303-555-0123', 'Technician');

insert into vehicles (id, vin, make, model, year, color, mileage, last_service_date, next_service_date, owner) values
(nextval('hibernate_sequence'), '1HGCM82633A123001', 'Honda', 'Accord', 2020, 'Blue', 15000, '2023-01-15', '2024-01-15', (select id from owners where email = 'john.doe@example.com')),
(nextval('hibernate_sequence'), '2HGCM82633A123002', 'Toyota', 'Camry', 2019, 'Red', 20000, '2023-02-15', '2024-02-15', (select id from owners where email = 'jane.smith@example.com')),
(nextval('hibernate_sequence'), '3HGCM82633A123003', 'Ford', 'Fusion', 2018, 'Black', 25000, '2023-03-15', '2024-03-15', (select id from owners where email = 'emily.johnson@example.com')),
(nextval('hibernate_sequence'), '4HGCM82633A123004', 'Chevrolet', 'Malibu', 2021, 'White', 10000, '2023-04-15', '2024-04-15', (select id from owners where email = 'michael.williams@example.com')),
(nextval('hibernate_sequence'), '5HGCM82633A123005', 'Nissan', 'Altima', 2022, 'Silver', 5000, '2023-05-15', '2024-05-15', (select id from owners where email = 'sarah.brown@example.com'));

insert into service_types (id, name, description, estimated_time_hours) values
(nextval('hibernate_sequence'), 'Oil Change', 'Standard engine oil and filter replacement', 1),
(nextval('hibernate_sequence'), 'Tire Rotation', 'Rotate all four tires for even wear', 1),
(nextval('hibernate_sequence'), 'Brake Inspection', 'Inspect brake pads, rotors, and calipers', 2),
(nextval('hibernate_sequence'), 'Engine Tune-Up', 'Replace spark plugs, air filter, and check engine components', 3),
(nextval('hibernate_sequence'), 'Transmission Service', 'Flush and replace transmission fluid', 2);

insert into service_appointments (id, vehicle_id, customer_id, staff_id, appointment_date, status, notes) values
(nextval('hibernate_sequence'),
 (select id from vehicles where vin = '1HGCM82633A123001'),
 (select id from owners where email = 'john.doe@example.com'),
 (select id from staff where email = 'alice.davis@example.com'),
 '2024-02-10', 'COMPLETED', 'Routine oil change and tire rotation'),
(nextval('hibernate_sequence'),
 (select id from vehicles where vin = '2HGCM82633A123002'),
 (select id from owners where email = 'jane.smith@example.com'),
 (select id from staff where email = 'bob.wilson@example.com'),
 '2024-03-05', 'COMPLETED', 'Brake inspection - pads replaced'),
(nextval('hibernate_sequence'),
 (select id from vehicles where vin = '3HGCM82633A123003'),
 (select id from owners where email = 'emily.johnson@example.com'),
 (select id from staff where email = 'alice.davis@example.com'),
 '2024-04-20', 'SCHEDULED', 'Engine tune-up scheduled'),
(nextval('hibernate_sequence'),
 (select id from vehicles where vin = '4HGCM82633A123004'),
 (select id from owners where email = 'michael.williams@example.com'),
 null,
 '2024-05-15', 'PENDING', 'Waiting for staff assignment'),
(nextval('hibernate_sequence'),
 (select id from vehicles where vin = '5HGCM82633A123005'),
 (select id from owners where email = 'sarah.brown@example.com'),
 (select id from staff where email = 'carol.miller@example.com'),
 '2024-06-01', 'SCHEDULED', 'Transmission service');

insert into service_details (id, appointment_id, service_type_id, cost) values
(nextval('hibernate_sequence'),
 (select id from service_appointments where notes = 'Routine oil change and tire rotation'),
 (select id from service_types where name = 'Oil Change'),
 49.99),
(nextval('hibernate_sequence'),
 (select id from service_appointments where notes = 'Routine oil change and tire rotation'),
 (select id from service_types where name = 'Tire Rotation'),
 29.99),
(nextval('hibernate_sequence'),
 (select id from service_appointments where notes = 'Brake inspection - pads replaced'),
 (select id from service_types where name = 'Brake Inspection'),
 149.99),
(nextval('hibernate_sequence'),
 (select id from service_appointments where notes = 'Engine tune-up scheduled'),
 (select id from service_types where name = 'Engine Tune-Up'),
 199.99),
(nextval('hibernate_sequence'),
 (select id from service_appointments where notes = 'Transmission service'),
 (select id from service_types where name = 'Transmission Service'),
 179.99);

insert into notifications (id, customer_id, notification_date, message, type, status) values
(nextval('hibernate_sequence'),
 (select id from owners where email = 'john.doe@example.com'),
 '2024-01-01 09:00:00',
 'Your vehicle is due for an oil change. Please schedule a service appointment.',
 'SERVICE_REMINDER', 'SENT'),
(nextval('hibernate_sequence'),
 (select id from owners where email = 'jane.smith@example.com'),
 '2024-02-01 09:00:00',
 'Your brake inspection appointment is confirmed for March 5th.',
 'APPOINTMENT_CONFIRMATION', 'SENT'),
(nextval('hibernate_sequence'),
 (select id from owners where email = 'emily.johnson@example.com'),
 '2024-03-15 09:00:00',
 'Reminder: Your engine tune-up is scheduled for April 20th.',
 'APPOINTMENT_REMINDER', 'SENT'),
(nextval('hibernate_sequence'),
 (select id from owners where email = 'michael.williams@example.com'),
 '2024-04-01 09:00:00',
 'Your vehicle service appointment is pending staff assignment. We will notify you shortly.',
 'SERVICE_UPDATE', 'SENT'),
(nextval('hibernate_sequence'),
 (select id from owners where email = 'sarah.brown@example.com'),
 '2024-05-01 09:00:00',
 'Your transmission service is scheduled for June 1st.',
 'APPOINTMENT_CONFIRMATION', 'SENT');
