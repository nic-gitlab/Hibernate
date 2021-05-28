DROP TABLE IF EXISTS cars;

CREATE TABLE cars (
id SERIAL PRIMARY KEY,
vin VARCHAR(20) UNIQUE NOT NULL,
year INTEGER NOT NULL,
make VARCHAR(30) NOT NULL,
asking_price INTEGER NOT NULL,
model VARCHAR(50) NOT NULL
);