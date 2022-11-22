-- create database
CREATE DATABASE IF NOT EXISTS carRental;
USE carRental;

-- create tables
-- car
CREATE TABLE IF NOT EXISTS car(
    vehicleNumber int AUTO_INCREMENT,
    frameNumber varchar(50) NOT NULL ,
    model varchar(50),
    manufacturer varchar(50),
    isManual boolean,
    accessories varchar(200),
    CO2discharge double,
    isOnStock boolean,
    PRIMARY KEY (vehicleNumber)
);
-- rental agreements
CREATE TABLE IF NOT EXISTS rentalAgreements(
    rentalId int AUTO_INCREMENT,
    monthsRented int,
    kilometerPerMonth int,
    isOverTraveled boolean,
    frameNumber varchar(50),
    vehicleNumber int,
    PRIMARY KEY (rentalId),
    FOREIGN KEY (vehicleNumber) REFERENCES car(vehicleNumber) ON DELETE CASCADE
);

-- damages
CREATE TABLE IF NOT EXISTS damages(
    damageId int AUTO_INCREMENT,
    problem varchar(200),
    frameNumber varchar(50),
    PRIMARY KEY (damageId),
    vehicleNumber int,
    FOREIGN KEY (vehicleNumber) REFERENCES car(vehicleNumber) ON DELETE CASCADE
);



DROP TABLE car;

alter table car
add column 3MonthsPrice int,
    add column 6MonthsPrice int,
add column 12MonthsPrice int,
add column 24MonthsPrice int,
add column 36MonthsPrice int;
# TRUNCATE car;
# TRUNCATE rentalAgreements;
# TRUNCATE damages;
