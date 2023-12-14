CREATE SCHEMA IF NOT EXISTS store;

USE store;

-- Drop tables if they exist
DROP TABLE IF EXISTS Accessory;
DROP TABLE IF EXISTS Clothes;
DROP TABLE IF EXISTS Shoe;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Shop;

CREATE TABLE IF NOT EXISTS Shop (
	shopId INT PRIMARY KEY,
    capital DOUBLE NOT NULL,
    income DOUBLE NOT NULL,
    cost DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS Product (
    productId INT PRIMARY KEY,
    name VARCHAR(255),
    realPrice DOUBLE,
    currentPrice DOUBLE,
    nbItems INT,
    shopId INT,
    FOREIGN KEY (shopId) REFERENCES Shop(shopId)
);

CREATE TABLE IF NOT EXISTS Shoe (
    productId INT PRIMARY KEY,
    shoeSize INT,
    FOREIGN KEY (productId) REFERENCES Product(productId)
);

CREATE TABLE IF NOT EXISTS Clothes (
    productId INT PRIMARY KEY,
    size INT,
    FOREIGN KEY (productId) REFERENCES Product(productId)
);

CREATE TABLE IF NOT EXISTS Accessory (
    productId INT PRIMARY KEY,
    FOREIGN KEY (productId) REFERENCES Product(productId)
);

INSERT INTO Shop VALUES (1, 0, 0, 0);

-- Insert data into Product table
INSERT INTO Product (productId, name, realPrice, currentPrice, nbItems, shopId)
VALUES
  (1, 'Dress1', 30.0, 30.0, 10, 1),
  (2, 'Dress2', 40.0, 40.0, 20, 1),
  (3, 'Shoes1', 20.0, 20.0, 20, 1),
  (4, 'Accessory1', 10.0, 10.0, 20, 1);

-- Insert data into Clothes table
INSERT INTO Clothes (productId, size)
VALUES
  (1, 40),
  (2, 38);

-- Insert data into Shoe table
INSERT INTO Shoe (productId, shoeSize)
VALUES
  (3, 40);

-- Insert data into Accessory table
INSERT INTO Accessory (productId)
VALUES (4);
