/*
NOTE: It is not necessary to create the tables manually. 
Just run the application and JPA will create them automatically. 
Just make sure you have a database created and configured in the application.
*/


CREATE DATABASE crudcars 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;


INSERT INTO tb_user (full_name, contract_number, email, birth_date, phone, password) VALUES 

('Maria Brown','5572384','maria@gmail.com','1986-07-14','11999999999', '123456789'),

('Bob Green','7524935','bob@gmail.com','1994-04-29','11988888888', '123456789'),

('Rock Grey','9357558','rock@gmail.com','1979-09-07','11977777777', '123456789');



INSERT INTO tb_brand (name) VALUES 

('Toyota'),

('Honda'),

('Mercedes-Benz'),

('Ford'),

('Chevrolet'),

('Hyundai'),

('Fiat'),

('Nissan'),

('Citroën'),

('Peugeot'),

('BMW'),

('Renault'),

('Volkswagen'),

('Audi'),

('Jeep');



INSERT INTO tb_model (name, price, year, status, bodywork, brand_id) VALUES 

('A3 Sedan',153990,'2020','New','Sedan',14),

('A4',229990,'2021','New','Sedan',14),

('Onix',58590,'2021','New','Hatchback',5),

('Cruze Sport6',80190,'2021','Used','Hatchback',5),

('RS7',839990,'2021','New','Coupé',14),

('Ranger',115190,'2021','Used','Pickup Truck',4),

('Uno',45890,'2021','New','Hatchback',7),

('Mustang',331750,'2020','New','Coupé',4),

('Voyage',40990,'2018','Used','Sedan',13),

('Strada',50590,'2019','Used','Pickup Truck',7),

('Cruze',101190,'2021','New','Sedan',5),

('Renegade',72999,'2019','New','SUV',15);
