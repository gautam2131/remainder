CREATE DATABASE IF NOT EXISTS notification_db;
USE notification_db;

DROP TABLE IF EXISTS document;
DROP TABLE IF EXISTS customer;

CREATE TABLE IF NOT EXISTS customer (
     id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     email VARCHAR(255) UNIQUE
 );


CREATE TABLE IF NOT EXISTS document (
    id INT AUTO_INCREMENT PRIMARY KEY,
    document_name VARCHAR(255) NOT NULL,
    expiry_date DATE,
    customer_id INT ,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);


INSERT INTO customer (name, email)
VALUES ('Gautam Balaji', 'gautambalaji2131@gmail.com');
SET @customer_id = LAST_INSERT_ID();

INSERT INTO document (document_name, expiry_date, customer_id)
VALUES ('Document 1', CURDATE(), @customer_id),
       ('Document 2', '2025-06-17', @customer_id);


---mock data---
INSERT INTO customer (name, email)
VALUES ('Gopi nath', 'gopinath.sonaiyan@flyerssoft.com');
SET @customer_id = LAST_INSERT_ID();

INSERT INTO document (document_name, expiry_date, customer_id)
VALUES ('Document 3', CURDATE(), @customer_id),
       ('Document 4', '2025-06-12', @customer_id);

INSERT INTO customer (name, email)
VALUES ('Balaji Kumar', 'balaji.kumar@flyerssoft.com');
SET @customer_id = LAST_INSERT_ID();

INSERT INTO document (document_name, expiry_date, customer_id)
VALUES ('Document 5', CURDATE(), @customer_id),
       ('Document 6', '2025-06-10', @customer_id);