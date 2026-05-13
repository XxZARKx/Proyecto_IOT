create table if not exists users (
  id bigserial primary key,
  full_name varchar(255) not null,
  email varchar(255) not null unique,
  password varchar(255) not null,
  role varchar(40) not null,
  status varchar(40) not null,
  created_at timestamp,
  updated_at timestamp
);

create table if not exists categories (
  id bigserial primary key,
  name varchar(255) not null unique,
  description varchar(1000),
  active boolean not null default true,
  created_at timestamp,
  updated_at timestamp
);

create table if not exists products (
  id bigserial primary key,
  name varchar(255) not null,
  description varchar(1000),
  sku varchar(80) not null unique,
  barcode varchar(120),
  unit_price numeric(12,2) not null,
  current_stock integer not null default 0,
  minimum_stock integer not null,
  maximum_stock integer not null,
  category_id bigint not null references categories(id),
  status varchar(40) not null,
  created_at timestamp,
  updated_at timestamp
);

create table if not exists inventory_movements (
  id bigserial primary key,
  product_id bigint not null references products(id),
  movement_type varchar(40) not null,
  quantity integer not null,
  previous_stock integer not null,
  new_stock integer not null,
  reason varchar(1000),
  user_id bigint references users(id),
  source varchar(40),
  created_at timestamp
);

create table if not exists alerts (
  id bigserial primary key,
  product_id bigint references products(id),
  alert_type varchar(60) not null,
  message varchar(1000) not null,
  status varchar(40) not null,
  created_at timestamp,
  resolved_at timestamp
);

create table if not exists sensor_devices (
  id bigserial primary key,
  code varchar(120) not null unique,
  name varchar(255) not null,
  sensor_type varchar(60) not null,
  location varchar(255),
  status varchar(40) not null,
  created_at timestamp
);

create table if not exists sensor_readings (
  id bigserial primary key,
  sensor_device_id bigint not null references sensor_devices(id),
  product_id bigint not null references products(id),
  quantity_detected integer not null,
  raw_value varchar(255),
  location varchar(255),
  movement_type varchar(40),
  processed boolean not null default false,
  created_at timestamp
);

create table if not exists audit_logs (
  id bigserial primary key,
  user_id bigint references users(id),
  action varchar(120),
  entity varchar(120),
  entity_id bigint,
  description varchar(1000),
  created_at timestamp
);
