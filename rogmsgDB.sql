-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema RoGMSG
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema RoGMSG
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `RoGMSG` DEFAULT CHARACTER SET utf8 ;
USE `RoGMSG` ;

-- -----------------------------------------------------
-- Table `RoGMSG`.`MsgGroup`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RoGMSG`.`MsgGroup` ;

CREATE TABLE IF NOT EXISTS `RoGMSG`.`MsgGroup` (
  `groupID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`groupID`),
  UNIQUE INDEX `groupID_UNIQUE` (`groupID` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RoGMSG`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RoGMSG`.`Users` ;

CREATE TABLE IF NOT EXISTS `RoGMSG`.`Users` (
  `userID` INT NOT NULL AUTO_INCREMENT,
  `groupID` INT NOT NULL,
  `username` VARCHAR(30) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  PRIMARY KEY (`userID`),
  UNIQUE INDEX `ID_UNIQUE` (`userID` ASC),
  INDEX `groupID_idx` (`groupID` ASC),
  CONSTRAINT `users_groupID`
    FOREIGN KEY (`groupID`)
    REFERENCES `RoGMSG`.`MsgGroup` (`groupID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RoGMSG`.`Polls`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RoGMSG`.`Polls` ;

CREATE TABLE IF NOT EXISTS `RoGMSG`.`Polls` (
  `pollID` INT NOT NULL AUTO_INCREMENT,
  `groupID` INT NOT NULL,
  `creatorID` INT NOT NULL,
  `exp` DATETIME NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`pollID`),
  UNIQUE INDEX `pollID_UNIQUE` (`pollID` ASC),
  INDEX `creatorID_idx` (`creatorID` ASC),
  INDEX `groupID_idx` (`groupID` ASC),
  CONSTRAINT `polls_creatorID`
    FOREIGN KEY (`creatorID`)
    REFERENCES `RoGMSG`.`Users` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `polls_groupID`
    FOREIGN KEY (`groupID`)
    REFERENCES `RoGMSG`.`MsgGroup` (`groupID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RoGMSG`.`Messages`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RoGMSG`.`Messages` ;

CREATE TABLE IF NOT EXISTS `RoGMSG`.`Messages` (
  `msgID` INT NOT NULL AUTO_INCREMENT,
  `groupID` INT NOT NULL,
  `msg` VARCHAR(240) NULL,
  `imageLoc` BLOB NULL,
  `audioLoc` VARCHAR(45) NULL,
  PRIMARY KEY (`msgID`),
  UNIQUE INDEX `msgID_UNIQUE` (`msgID` ASC),
  UNIQUE INDEX `groupID_UNIQUE` (`groupID` ASC),
  CONSTRAINT `msg_groupID`
    FOREIGN KEY (`groupID`)
    REFERENCES `RoGMSG`.`MsgGroup` (`groupID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RoGMSG`.`Messages_Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RoGMSG`.`Messages_Users` ;

CREATE TABLE IF NOT EXISTS `RoGMSG`.`Messages_Users` (
  `Messages_msgID` INT NOT NULL,
  `user_userID` INT NOT NULL,
  PRIMARY KEY (`Messages_msgID`, `user_userID`),
  INDEX `fk_Messages_has_user_user1_idx` (`user_userID` ASC),
  INDEX `fk_Messages_has_user_Messages1_idx` (`Messages_msgID` ASC),
  CONSTRAINT `fk_Messages_has_user_Messages1`
    FOREIGN KEY (`Messages_msgID`)
    REFERENCES `RoGMSG`.`Messages` (`msgID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Messages_has_user_user1`
    FOREIGN KEY (`user_userID`)
    REFERENCES `RoGMSG`.`Users` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RoGMSG`.`Lists`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RoGMSG`.`Lists` ;

CREATE TABLE IF NOT EXISTS `RoGMSG`.`Lists` (
  `listID` INT NOT NULL AUTO_INCREMENT,
  `groupID` INT NOT NULL,
  `users` INT NOT NULL COMMENT 'A List can be available to only a subset of the group',
  `name` VARCHAR(45) NOT NULL COMMENT 'Name of the List',
  PRIMARY KEY (`listID`),
  UNIQUE INDEX `listID_UNIQUE` (`listID` ASC),
  INDEX `group_idx` (`groupID` ASC),
  INDEX `users_idx1` (`users` ASC),
  CONSTRAINT `lists_groupID`
    FOREIGN KEY (`groupID`)
    REFERENCES `RoGMSG`.`MsgGroup` (`groupID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `lists_usersID`
    FOREIGN KEY (`users`)
    REFERENCES `RoGMSG`.`Users` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RoGMSG`.`Polls_Entries`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RoGMSG`.`Polls_Entries` ;

CREATE TABLE IF NOT EXISTS `RoGMSG`.`Polls_Entries` (
  `entryID` INT NOT NULL AUTO_INCREMENT,
  `pollID` INT NOT NULL,
  `entry` VARCHAR(45) NOT NULL,
  `tally` INT NOT NULL,
  PRIMARY KEY (`entryID`),
  UNIQUE INDEX `entryID_UNIQUE` (`entryID` ASC),
  INDEX `pollsID_idx` (`pollID` ASC),
  CONSTRAINT `pollsID`
    FOREIGN KEY (`pollID`)
    REFERENCES `RoGMSG`.`Polls` (`pollID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RoGMSG`.`Lists_Entries`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RoGMSG`.`Lists_Entries` ;

CREATE TABLE IF NOT EXISTS `RoGMSG`.`Lists_Entries` (
  `entryID` INT NOT NULL AUTO_INCREMENT,
  `listID` INT NOT NULL,
  `entry` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`entryID`),
  UNIQUE INDEX `entryID_UNIQUE` (`entryID` ASC),
  INDEX `listID_idx` (`listID` ASC),
  CONSTRAINT `listID`
    FOREIGN KEY (`listID`)
    REFERENCES `RoGMSG`.`Lists` (`listID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `RoGMSG` ;

-- -----------------------------------------------------
-- Placeholder table for view `RoGMSG`.`view1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RoGMSG`.`view1` (`id` INT);

-- -----------------------------------------------------
-- View `RoGMSG`.`view1`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `RoGMSG`.`view1` ;
DROP TABLE IF EXISTS `RoGMSG`.`view1`;
USE `RoGMSG`;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
