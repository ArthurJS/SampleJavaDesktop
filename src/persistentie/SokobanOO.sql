-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema sokoban
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sokoban
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sokoban` DEFAULT CHARACTER SET utf8 ;
USE `sokoban` ;

-- -----------------------------------------------------
-- Table `sokoban`.`spel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sokoban`.`spel` (
  `Spelnaam` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`Spelnaam`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sokoban`.`spelbord`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sokoban`.`spelbord` (
  `Mapcode` VARCHAR(100) NOT NULL,
  `spel_Spelnaam` VARCHAR(30) NOT NULL,
  `VolgordeID` INT(11) NOT NULL,
  PRIMARY KEY (`spel_Spelnaam`, `VolgordeID`),
  INDEX `fk_Spelbord_spel1_idx` (`spel_Spelnaam` ASC),
  CONSTRAINT `fk_Spelbord_spel1`
    FOREIGN KEY (`spel_Spelnaam`)
    REFERENCES `sokoban`.`spel` (`Spelnaam`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sokoban`.`veld`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sokoban`.`veld` (
  `heeftDoel` TINYINT(1) NULL,
  `XCoord` INT(11) NOT NULL,
  `YCoord` INT(11) NOT NULL,
  `Spelbord_spel_Spelnaam` VARCHAR(30) NOT NULL,
  `Spelbord_VolgordeID` INT(11) NOT NULL,
  INDEX `fk_veld_Spelbord1_idx` (`XCoord` ASC, `YCoord` ASC, `Spelbord_spel_Spelnaam` ASC, `Spelbord_VolgordeID` ASC),
  CONSTRAINT `fk_veld_Spelbord1`
    FOREIGN KEY (`Spelbord_spel_Spelnaam` , `Spelbord_VolgordeID`)
    REFERENCES `sokoban`.`spelbord` (`spel_Spelnaam` , `VolgordeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sokoban`.`kist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sokoban`.`kist` (
  `Veld_XCoord` INT(11) NOT NULL,
  `Veld_YCoord` INT(11) NOT NULL,
  `Veld_spelbord_spel_spelnaam` VARCHAR(30) NOT NULL,
  `Veld_spelbord_spel_volgordeID` INT(11) NOT NULL,
   INDEX `FK` (`Veld_XCoord` ASC, `Veld_YCoord` ASC, `Veld_spelbord_spel_spelnaam` ASC, `Veld_spelbord_spel_volgordeID` ASC),
  CONSTRAINT `fk_Kist_Veld1`
    FOREIGN KEY (`Veld_XCoord` , `Veld_YCoord` , `Veld_spelbord_spel_spelnaam` , `Veld_spelbord_spel_volgordeID`)
    REFERENCES `sokoban`.`veld` (`XCoord` , `YCoord` , `Spelbord_spel_Spelnaam` , `Spelbord_VolgordeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sokoban`.`mannetje`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sokoban`.`mannetje` (
  `Veld_XCoord` INT(11) NOT NULL,
  `Veld_YCoord` INT(11) NOT NULL,
  `Veld_spelbord_spel_spelnaam` VARCHAR(30) NOT NULL,
  `Veld_spelbord_spel_volgordeID` INT(11) NOT NULL,
  INDEX `FK` (`Veld_XCoord` ASC, `Veld_YCoord` ASC, `Veld_spelbord_spel_spelnaam` ASC, `Veld_spelbord_spel_volgordeID` ASC),
  CONSTRAINT `fk_Mannetje_Veld1`
    FOREIGN KEY (`Veld_XCoord` , `Veld_YCoord` , `Veld_spelbord_spel_spelnaam` , `Veld_spelbord_spel_volgordeID`)
    REFERENCES `sokoban`.`veld` (`XCoord` , `YCoord` , `Spelbord_spel_Spelnaam` , `Spelbord_VolgordeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sokoban`.`muur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sokoban`.`muur` (
  `Veld_XCoord` INT(11) NOT NULL,
  `Veld_YCoord` INT(11) NOT NULL,
  `Veld_spelbord_spel_spelnaam` VARCHAR(30) NOT NULL,
  `Veld_spelbord_spel_volgordeID` INT(11) NOT NULL,
   INDEX `FK` (`Veld_XCoord` ASC, `Veld_YCoord` ASC, `Veld_spelbord_spel_spelnaam` ASC, `Veld_spelbord_spel_volgordeID` ASC),
  CONSTRAINT `fk_Muur_Veld1`
    FOREIGN KEY (`Veld_XCoord` , `Veld_YCoord` , `Veld_spelbord_spel_spelnaam` , `Veld_spelbord_spel_volgordeID`)
    REFERENCES `sokoban`.`veld` (`XCoord` , `YCoord` , `Spelbord_spel_Spelnaam` , `Spelbord_VolgordeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sokoban`.`speler`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sokoban`.`speler` (
  `voornaam` VARCHAR(30) NULL DEFAULT NULL,
  `achternaam` VARCHAR(45) NULL DEFAULT NULL,
  `gebruikersnaam` VARCHAR(30) NOT NULL,
  `wachtwoord` VARCHAR(30) NOT NULL,
  `admin` TINYINT(1) NOT NULL,
  PRIMARY KEY (`gebruikersnaam`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
