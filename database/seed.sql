-- Password demo para todos: Password123
-- El backend tambien crea datos semilla automaticamente con BCrypt.
insert into categories(name, description, active, created_at, updated_at)
values
  ('Abarrotes', 'Productos de alta rotacion', true, now(), now()),
  ('Bebidas', 'Bebidas embotelladas y cajas', true, now(), now()),
  ('Limpieza', 'Productos de limpieza del hogar', true, now(), now())
on conflict (name) do nothing;

insert into products(name, description, sku, barcode, unit_price, current_stock, minimum_stock, maximum_stock, category_id, status, created_at, updated_at)
select 'Arroz superior 5kg', 'Producto demo', 'PROD-0001', '775000001', 18.90, 8, 10, 90, id, 'ACTIVO', now(), now() from categories where name = 'Abarrotes'
on conflict (sku) do nothing;

insert into products(name, description, sku, barcode, unit_price, current_stock, minimum_stock, maximum_stock, category_id, status, created_at, updated_at)
select 'Aceite vegetal 1L', 'Producto demo', 'PROD-0002', '775000002', 9.50, 0, 12, 80, id, 'ACTIVO', now(), now() from categories where name = 'Abarrotes'
on conflict (sku) do nothing;

insert into products(name, description, sku, barcode, unit_price, current_stock, minimum_stock, maximum_stock, category_id, status, created_at, updated_at)
select 'Agua mineral 625ml', 'Producto demo', 'PROD-0003', '775000003', 1.80, 45, 20, 150, id, 'ACTIVO', now(), now() from categories where name = 'Bebidas'
on conflict (sku) do nothing;
