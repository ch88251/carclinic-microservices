create sequence hibernate_sequence START WITH 100 INCREMENT BY 1;

create table "vehicles"
(
    "id"                 bigint      not null default nextval('hibernate_sequence'),
    "vin"                varchar(32) not null,
    "make"               varchar(64) not null,
    "model"              varchar(64) not null,
    "color"              varchar(32) not null,    
    "year"               integer     not null,
    "mileage"            integer     not null,
    "last_service_date"  date        not null,
    "next_service_date"  date        not null,
    "owner"              bigint,
    "date_created"       timestamp   not null default now(),
    "date_modified"      timestamp   not null default now()
);

alter table "vehicles"
    add constraint "vehicles_pk" primary key ("id");

alter table "vehicles"
    add constraint "vehicles_vin_unique" unique ("vin");

create table "owners"
(
    "id"           bigint       not null default nextval('hibernate_sequence'),
    "first_name"   varchar(128) not null,
    "last_name"    varchar(128) not null,
    "email"        varchar(256) not null,
    "phone_number" varchar(32)  not null,
    "date_created" timestamp    not null default now(),
    "date_modified" timestamp   not null default now()
);

alter table "owners"
    add constraint "owners_pk" primary key ("id");

create table "staff"
(
    "id"           bigint       not null default nextval('hibernate_sequence'),
    "first_name"   varchar(128) not null,
    "last_name"    varchar(128) not null,
    "email"        varchar(256) not null,
    "phone_number" varchar(32)  not null,
    "role"         varchar(64)  not null,
    "date_created" timestamp    not null default now(),
    "date_modified" timestamp   not null default now()
);

alter table "staff"
    add constraint "staff_pk" primary key ("id");

create table "service_types"
(
    "id"                    bigint       not null default nextval('hibernate_sequence'),
    "name"                  varchar(128) not null,
    "description"           text,
    "estimated_time_hours"  integer      not null,
    "date_created"          timestamp    not null default now(),
    "date_modified"         timestamp    not null default now()
);

alter table "service_types"
    add constraint "service_types_pk" primary key ("id");

create table "service_appointments"
(
    "id"               bigint    not null default nextval('hibernate_sequence'),
    "vehicle_id"       bigint    not null,
    "customer_id"      bigint    not null,
    "staff_id"         bigint,
    "appointment_date" date      not null,
    "status"           varchar(32) not null,
    "notes"            text,
    "date_created"     timestamp not null default now(),
    "date_modified"    timestamp not null default now()
);

alter table "service_appointments"
    add constraint "service_appointments_pk" primary key ("id");

alter table "service_appointments"
    add constraint "service_appointments_vehicle_fk" foreign key ("vehicle_id") references "vehicles" ("id");

alter table "service_appointments"
    add constraint "service_appointments_customer_fk" foreign key ("customer_id") references "owners" ("id");

alter table "service_appointments"
    add constraint "service_appointments_staff_fk" foreign key ("staff_id") references "staff" ("id");

create table "service_details"
(
    "id"              bigint         not null default nextval('hibernate_sequence'),
    "appointment_id"  bigint         not null,
    "service_type_id" bigint         not null,
    "cost"            numeric(10, 2) not null,
    "date_created"    timestamp      not null default now(),
    "date_modified"   timestamp      not null default now()
);

alter table "service_details"
    add constraint "service_details_pk" primary key ("id");

alter table "service_details"
    add constraint "service_details_appointment_fk" foreign key ("appointment_id") references "service_appointments" ("id");

alter table "service_details"
    add constraint "service_details_service_type_fk" foreign key ("service_type_id") references "service_types" ("id");

create table "notifications"
(
    "id"                bigint    not null default nextval('hibernate_sequence'),
    "customer_id"       bigint    not null,
    "notification_date" timestamp not null,
    "message"           text      not null,
    "type"              varchar(64) not null,
    "status"            varchar(32) not null,
    "date_created"      timestamp not null default now(),
    "date_modified"     timestamp not null default now()
);

alter table "notifications"
    add constraint "notifications_pk" primary key ("id");

alter table "notifications"
    add constraint "notifications_customer_fk" foreign key ("customer_id") references "owners" ("id");

alter table "vehicles"
    add constraint "vehicles_owner_fk" foreign key ("owner") references "owners" ("id");