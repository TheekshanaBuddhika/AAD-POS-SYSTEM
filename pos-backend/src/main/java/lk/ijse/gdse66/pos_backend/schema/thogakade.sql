CREATE DATABASE IF NOT EXISTS thogakade3;
USE thogakade3;

-- Create the customer table
CREATE TABLE customer (
                          id varchar(6) NOT NULL PRIMARY KEY,
                          name varchar(30),
                          address varchar(30),
                          salary decimal(10,2)
);

-- Create the item table
CREATE TABLE item (
                      code varchar(6) NOT NULL PRIMARY KEY,
                      description varchar(50),
                      unitPrice decimal(8,2),
                      qtyOnHand int
);

-- Create the orders table
CREATE TABLE orders (
                        id varchar(6) NOT NULL PRIMARY KEY,
                        date date,
                        customerId varchar(6) NOT NULL,
                        FOREIGN KEY (customerId) REFERENCES customer(id)
);

-- Create the order_details table
CREATE TABLE order_details (
                               orderId varchar(6),
                               itemCode varchar(6),
                               qty int,
                               unitPrice decimal(8,2),
                               FOREIGN KEY (orderId) REFERENCES orders(id),
                               FOREIGN KEY (itemCode) REFERENCES item(code)
);