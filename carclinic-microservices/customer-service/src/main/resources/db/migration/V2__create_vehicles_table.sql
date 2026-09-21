CREATE SEQUENCE vehicles_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE vehicles (
    id BIGINT NOT NULL,
    vin VARCHAR(255) NOT NULL,
    make VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL,
    year INTEGER NOT NULL,
    mileage INTEGER NOT NULL,
    last_service_date DATE,
    next_service_date DATE,
    customer_id BIGINT,
    CONSTRAINT pk_vehicles PRIMARY KEY (id),
    CONSTRAINT fk_vehicles_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
);
