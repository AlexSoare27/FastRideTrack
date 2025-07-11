-- MySQL Script generated by MySQL Workbench
-- Fri Jun 27 10:13:17 2025
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema fastridetrack
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema fastridetrack
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fastridetrack` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `fastridetrack` ;

-- -----------------------------------------------------
-- Table `fastridetrack`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastridetrack`.`client` (
  `userID` INT NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `phoneNumber` VARCHAR(20) NOT NULL,
  `paymentMethod` ENUM('CARD', 'CASH') NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE INDEX `username` (`username` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fastridetrack`.`driver`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastridetrack`.`driver` (
  `userID` INT NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `phoneNumber` VARCHAR(20) NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  `vehicleInfo` VARCHAR(255) NOT NULL,
  `vehiclePlate` VARCHAR(20) NOT NULL,
  `affiliation` VARCHAR(255) NOT NULL,
  `available` TINYINT NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE INDEX `username` (`username` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fastridetrack`.`ride_request`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastridetrack`.`ride_request` (
  `requestID` INT NOT NULL AUTO_INCREMENT,
  `clientID` INT NOT NULL,
  `pickupLocation` VARCHAR(255) NOT NULL,
  `destination` VARCHAR(255) NOT NULL,
  `requestTime` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`requestID`),
  INDEX `clientID` (`clientID` ASC) VISIBLE,
  CONSTRAINT `ride_request_ibfk_1`
    FOREIGN KEY (`clientID`)
    REFERENCES `fastridetrack`.`client` (`userID`))
ENGINE = InnoDB
AUTO_INCREMENT = 1238
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fastridetrack`.`taxi_rides`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastridetrack`.`taxi_rides` (
  `rideID` INT NOT NULL AUTO_INCREMENT,
  `requestID` INT NOT NULL,
  `driverID` INT NOT NULL,
  `clientID` INT NOT NULL,
  `rideConfirmationStatus` ENUM('PENDING', 'REJECTED', 'ACCEPTED') NOT NULL,
  `estimatedFare` FLOAT NOT NULL,
  `estimatedTime` FLOAT NOT NULL,
  `paymentMethod` ENUM('CASH', 'CARD') NOT NULL,
  `confirmationTime` DATETIME NOT NULL,
  `destination` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`rideID`),
  INDEX `driverID` (`driverID` ASC) VISIBLE,
  INDEX `clientID` (`clientID` ASC) VISIBLE,
  INDEX `requestID` (`requestID` ASC) VISIBLE,
  CONSTRAINT `taxi_rides_ibfk_1`
    FOREIGN KEY (`clientID`)
    REFERENCES `fastridetrack`.`client` (`userID`),
  CONSTRAINT `taxi_rides_ibfk_2`
    FOREIGN KEY (`driverID`)
    REFERENCES `fastridetrack`.`driver` (`userID`),
  CONSTRAINT `taxi_rides_ibfk_3`
    FOREIGN KEY (`requestID`)
    REFERENCES `fastridetrack`.`ride_request` (`requestID`))
ENGINE = InnoDB
AUTO_INCREMENT = 1236
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fastridetrack`.`rides`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastridetrack`.`rides` (
  `rideID` INT NOT NULL,
  `driverID` INT NOT NULL,
  `clientID` INT NOT NULL,
  `destination` VARCHAR(255) NOT NULL,
  `rideStatus` ENUM('INITIATED', 'CLIENT_LOCATED', 'ONGOING', 'FINISHED') NOT NULL,
  `startTime` DATETIME NOT NULL,
  `endTime` DATETIME NULL DEFAULT NULL,
  `totalPayed` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`rideID`),
  INDEX `driver_idx` (`driverID` ASC),
  INDEX `client_idx` (`clientID` ASC),
  INDEX `dest_idx` (`destination` ASC),
  CONSTRAINT `client`
    FOREIGN KEY (`clientID`)
    REFERENCES `fastridetrack`.`client` (`userID`),
  CONSTRAINT `driver`
    FOREIGN KEY (`driverID`)
    REFERENCES `fastridetrack`.`driver` (`userID`),
  CONSTRAINT `rideID`
    FOREIGN KEY (`rideID`)
    REFERENCES `fastridetrack`.`taxi_rides` (`rideID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
