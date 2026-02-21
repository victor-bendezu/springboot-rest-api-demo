DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS product_category;

CREATE TABLE product_category (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  code VARCHAR(20) NOT NULL UNIQUE,
                                  name VARCHAR(120) NOT NULL,
                                  active BOOLEAN NOT NULL
);

CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         sku VARCHAR(30) NOT NULL UNIQUE,
                         name VARCHAR(150) NOT NULL,
                         price DECIMAL(12,2) NOT NULL,
                         category_id BIGINT NOT NULL,
                         active BOOLEAN NOT NULL,
                         CONSTRAINT fk_product_category
                             FOREIGN KEY (category_id) REFERENCES product_category(id)
);