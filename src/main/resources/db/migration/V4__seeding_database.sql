INSERT INTO categories (name, slug, created_at)
VALUES 
  ('Electronics', 'electronics', now()),
  ('Furniture', 'furniture', now()),
  ('Clothing', 'clothing', now()),
  ('Beauty Products', 'beauty-products', now()),
  ('Sports', 'sports', now());

INSERT INTO products (name, slug, category_id, created_at)
VALUES 
  ('Smartphone', 'smartphone', (
    SELECT id FROM categories as c
    WHERE c.slug = 'electronics'
    AND c.deleted_at IS NULL
  ), now()),
  ('Dining Table', 'dining-table', (
    SELECT id FROM categories as c
    WHERE c.slug = 'furniture'
    AND c.deleted_at IS NULL
  ), now()),
  ('T-Shirt', 't-shirt', (
    SELECT id FROM categories as c
    WHERE c.slug = 'clothing'
    AND c.deleted_at IS NULL
  ), now()),
  ('Basketball', 'basketball', (
    SELECT id FROM categories as c
    WHERE c.slug = 'sports'
    AND c.deleted_at IS NULL
  ), now()),
  ('Face Cream', 'face-cream', (
    SELECT id FROM categories as c
    WHERE c.slug = 'sports'
    AND c.deleted_at IS NULL
  ), now()),
  ('Jeans', 'jeans', (
    SELECT id FROM categories as c
    WHERE c.slug = 'clothing'
    AND c.deleted_at IS NULL
  ), now());

INSERT INTO variants (name, slug, product_id, description, price, stock, created_at)
VALUES 
  ('iPhone 16', 'iphone-16', (
    SELECT id FROM products as p
    WHERE p.slug = 'smartphone'
    AND p.deleted_at IS NULL
  ), 
  'iPhone baru keluar cuy', 
  16499000, 150, now()),
  ('Google Pixel 9 Pro', 'google-pixel-9-pro', (
    SELECT id FROM products as p
    WHERE p.slug = 'smartphone'
    AND p.deleted_at IS NULL
  ), 
  'Hape gak ramah IMEI', 
  23499000, 150, now()),
  ('IKEA Dining Table - Wooden', 'ikea-dining-table-wooden', (
    SELECT id FROM products as p
    WHERE p.slug = 'dining-table'
    AND p.deleted_at IS NULL
  ), 
  'bukan untuk rakjel', 
  1200000, 50, now()),
  ('Informa Dining Table', 'informa-dining-table', (
    SELECT id FROM products as p
    WHERE p.slug = 'dining-table'
    AND p.deleted_at IS NULL
  ), 
  'ini juga bukan untuk rakjel', 
  1000000, 75, now()),
  ('Olimpic Dining Table', 'olimpic-dining-table', (
    SELECT id FROM products as p
    WHERE p.slug = 'dining-table'
    AND p.deleted_at IS NULL
  ), 
  'ini juga bukan untuk rakjel', 
  200000, 15, now()),
  ('Uniqlo - UT Jujutsu Kaisen', 'uniqlo-ut-jujutsu-kaisen', (
    SELECT id FROM products as p
    WHERE p.slug = 't-shirt'
    AND p.deleted_at IS NULL
  ), 
  'WIBU!!!', 
  200000, 100, now()),
  ('H&M - Relaxed Fit Flannel Shirt', 'h&m-relaxed-fit-flannel-shirt', (
    SELECT id FROM products as p
    WHERE p.slug = 't-shirt'
    AND p.deleted_at IS NULL
  ), 
  'skin default mahasiswa npc', 
  200000, 100, now()),
  ('Jeans - blue jeans', 'jeans-blue-jeans', (
    SELECT id FROM products as p
    WHERE p.slug = 'jeans'
    AND p.deleted_at IS NULL
  ), 
  'jeans ucok', 
  200000, 100, now()),
  ('SKINTIFIC - MSH Niacinamide Brightening Moisturizer', 'skintific-msh-niacinamide-brightening-moisturizer', (
    SELECT id FROM products as p
    WHERE p.slug = 'face-cream'
    AND p.deleted_at IS NULL
  ), 
  'skicare skintific', 
  500000, 50, now()),
  ('whitelab - Mug Barrier Moisturizer', 'whitelab-mug-barrier-moisturizer', (
    SELECT id FROM products as p
    WHERE p.slug = 'face-cream'
    AND p.deleted_at IS NULL
  ), 
  'skincare whitelab', 
  500000, 50, now()),
  ('Standard Orange Basketball', 'standard-orange-basketball', (
    SELECT id FROM products as p
    WHERE p.slug = 'basketball'
    AND p.deleted_at IS NULL
  ), 
  'Standard basketball', 
  500000, 50, now()),
  ('Airless Basketball', 'airless-basketball', (
    SELECT id FROM products as p
    WHERE p.slug = 'basketball'
    AND p.deleted_at IS NULL
  ), 
  '3D-Printed Airless Basketball', 
  1000000, 50, now());