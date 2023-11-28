CREATE TABLE IF NOT EXISTS Shop (
	shopId INT PRIMARY KEY,
    capital DOUBLE NOT NULL,
    income DOUBLE NOT NULL,
    cost DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS Product (
    productId INT PRIMARY KEY,
    name VARCHAR(255),
    price DOUBLE,
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

-- We create our shop
SELECT * FROM Shoe NATURAL JOIN PRODUCT;

-- Insert data into Product table
INSERT INTO Product (productId, name, price, nbItems, shopId)
VALUES
  (1, 'Sneakers', 50.0, 10, 1),
  (2, 'T-Shirt', 20.0, 20, 1),
  (3, 'Necklace', 30.0, 15, 1);

-- Insert data into Shoe table
INSERT INTO Shoe (productId, shoeSize)
VALUES
  (1, 42);

-- Insert data into Clothes table
INSERT INTO Clothes (productId, size)
VALUES
  (2, 5);

-- Insert data into Accessory table
INSERT INTO Accessory (productId)
VALUES (3);


SELECT * FROM Shoe NATURAL JOIN Product;
SELECT * FROM Clothes NATURAL JOIN Product;
SELECT * FROM Accessory NATURAL JOIN Product;