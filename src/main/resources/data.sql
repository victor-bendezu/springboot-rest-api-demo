-- Categories
INSERT INTO product_category (code, name, active) VALUES ('ELEC', 'Electronics', TRUE);
INSERT INTO product_category (code, name, active) VALUES ('HOME', 'Home & Kitchen', TRUE);
INSERT INTO product_category (code, name, active) VALUES ('TOYS', 'Toys', TRUE);

-- Products
INSERT INTO product (sku, name, price, category_id, active) VALUES ('SKU-1001', 'Wireless Mouse', 29.99, 1, TRUE);
INSERT INTO product (sku, name, price, category_id, active) VALUES ('SKU-1002', 'Mechanical Keyboard', 99.00, 1, TRUE);
INSERT INTO product (sku, name, price, category_id, active) VALUES ('SKU-2001', 'Stainless Steel Pan', 39.50, 2, TRUE);
INSERT INTO product (sku, name, price, category_id, active) VALUES ('SKU-3001', 'Remote Control Car', 24.90, 3, TRUE);