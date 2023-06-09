-- MySQL Script generated by MySQL Workbench
-- Thu May  4 09:48:41 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema weingutmerlot
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema weingutmerlot
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `weingutmerlot` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `weingutmerlot` ;

-- -----------------------------------------------------
-- Table `weingutmerlot`.`agragebiete`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weingutmerlot`.`agragebiete` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `size` INT NOT NULL COMMENT 'In hectar',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `weingutmerlot`.`agragebietsektionen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weingutmerlot`.`agragebietsektionen` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `agragebiet_id` INT NOT NULL,
  `weinrebe_id` INT NOT NULL,
  `size` INT NOT NULL COMMENT 'In hectar',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_agragebietsektionen_agragebiet1`
    FOREIGN KEY (`agragebiet_id`)
    REFERENCES `weingutmerlot`.`agragebiete` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_agragebietsektionen_weinrebe1`
    FOREIGN KEY (`weinrebe_id`)
    REFERENCES `weingutmerlot`.`weinreben` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_agragebietsektionen_agragebiet1_idx` ON `weingutmerlot`.`agragebietsektionen` (`agragebiet_id` ASC) VISIBLE;

CREATE INDEX `fk_agragebietsektionen_weinrebe1_idx` ON `weingutmerlot`.`agragebietsektionen` (`weinrebe_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `weingutmerlot`.`chargen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weingutmerlot`.`chargen` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `weintyp_id` INT NOT NULL,
  `jahrgang` INT NOT NULL DEFAULT (YEAR(CURDATE())),
  `lagerungsort` VARCHAR(255) NULL,
  `menge_in_liter` DECIMAL(40,3) NOT NULL,
  `istFertig` BIT(1) NOT NULL DEFAULT 0,
  `istVerworfen` BIT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_wein_weintyp1`
    FOREIGN KEY (`weintyp_id`)
    REFERENCES `weingutmerlot`.`weine` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_wein_weintyp1_idx` ON `weingutmerlot`.`chargen` (`weintyp_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `weingutmerlot`.`gaerungsprozesse`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weingutmerlot`.`gaerungsprozesse` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `weine_id` INT NOT NULL,
  `zuckergehalt` INT NOT NULL,
  `temperatur` DECIMAL(4,2) NOT NULL,
  `dauer` INT NULL COMMENT 'Berechnet',
  `lagerungsbehaelter` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_gaerungsprozesse_weine1`
    FOREIGN KEY (`weine_id`)
    REFERENCES `weingutmerlot`.`weine` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_gaerungsprozesse_weine1_idx` ON `weingutmerlot`.`gaerungsprozesse` (`weine_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `weingutmerlot`.`gaerungsprozessschritte`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weingutmerlot`.`gaerungsprozessschritte` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `gaerungsprozess_id` INT NOT NULL,
  `nach_zeit` INT NOT NULL,
  `schritt` INT NOT NULL,
  `soll_zucker` DECIMAL(20,10) NOT NULL,
  `soll_temperatur` DECIMAL(4,2) NOT NULL,
  `soll_alkohol` DECIMAL(13,10) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_gaerungsprozessschritte_gaerungsprozess1`
    FOREIGN KEY (`gaerungsprozess_id`)
    REFERENCES `weingutmerlot`.`gaerungsprozesse` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_gaerungsprozessschritte_gaerungsprozess1_idx` ON `weingutmerlot`.`gaerungsprozessschritte` (`gaerungsprozess_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `weingutmerlot`.`gaerungsprozessschritte_has_hefen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weingutmerlot`.`gaerungsprozessschritte_has_hefen` (
  `gaerungsprozessschritte_id` INT NOT NULL,
  `hefen_id` INT NOT NULL,
  `menge` DECIMAL(20,10) NOT NULL DEFAULT 0,
  PRIMARY KEY (`gaerungsprozessschritte_id`, `hefen_id`),
  CONSTRAINT `fk_gaerungsprozessschritte_has_hefen_gaerungsprozessschritte1`
    FOREIGN KEY (`gaerungsprozessschritte_id`)
    REFERENCES `weingutmerlot`.`gaerungsprozessschritte` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_gaerungsprozessschritte_has_hefen_hefen1`
    FOREIGN KEY (`hefen_id`)
    REFERENCES `weingutmerlot`.`hefen` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_gaerungsprozessschritte_has_hefen_hefen1_idx` ON `weingutmerlot`.`gaerungsprozessschritte_has_hefen` (`hefen_id` ASC) VISIBLE;

CREATE INDEX `fk_gaerungsprozessschritte_has_hefen_gaerungsprozessschritt_idx` ON `weingutmerlot`.`gaerungsprozessschritte_has_hefen` (`gaerungsprozessschritte_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `weingutmerlot`.`hefen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weingutmerlot`.`hefen` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `art` VARCHAR(255) NOT NULL,
  `temperatur` DECIMAL(4,2) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `weingutmerlot`.`ueberpruefungen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weingutmerlot`.`ueberpruefungen` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `chargen_id` INT NOT NULL,
  `gaerungsprozessschritte_id` INT NOT NULL,
  `ist_zucker` DECIMAL(20,10) NOT NULL,
  `ist_temperatur` DECIMAL(4,2) NOT NULL,
  `ist_alkohol` DECIMAL(13,10) NOT NULL,
  `anpassung_zucker` DECIMAL(20,10) NOT NULL DEFAULT 0,
  `anpassung_temperatur` DECIMAL(4,2) NOT NULL DEFAULT 0,
  `naechster_schritt` BIT(1) NOT NULL DEFAULT 1 COMMENT 'BOOLEAN',
  `datum` DATETIME NOT NULL,
  `next_date` DATE NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_ueberpruefungen_chargen1`
    FOREIGN KEY (`chargen_id`)
    REFERENCES `weingutmerlot`.`chargen` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ueberpruefungen_gaerungsprozessschritte1`
    FOREIGN KEY (`gaerungsprozessschritte_id`)
    REFERENCES `weingutmerlot`.`gaerungsprozessschritte` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_ueberpruefungen_chargen1_idx` ON `weingutmerlot`.`ueberpruefungen` (`chargen_id` ASC) VISIBLE;

CREATE INDEX `fk_ueberpruefungen_gaerungsprozessschritte1_idx` ON `weingutmerlot`.`ueberpruefungen` (`gaerungsprozessschritte_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `weingutmerlot`.`ueberpruefungen_has_hefen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weingutmerlot`.`ueberpruefungen_has_hefen` (
  `ueberpruefungen_id` INT NOT NULL,
  `hefen_id` INT NOT NULL,
  `anpassung` DECIMAL(20,10) NOT NULL,
  PRIMARY KEY (`ueberpruefungen_id`, `hefen_id`),
  CONSTRAINT `fk_ueberpruefungen_has_hefen_ueberpruefungen1`
    FOREIGN KEY (`ueberpruefungen_id`)
    REFERENCES `weingutmerlot`.`ueberpruefungen` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ueberpruefungen_has_hefen_hefen1`
    FOREIGN KEY (`hefen_id`)
    REFERENCES `weingutmerlot`.`hefen` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_ueberpruefungen_has_hefen_hefen1_idx` ON `weingutmerlot`.`ueberpruefungen_has_hefen` (`hefen_id` ASC) VISIBLE;

CREATE INDEX `fk_ueberpruefungen_has_hefen_ueberpruefungen1_idx` ON `weingutmerlot`.`ueberpruefungen_has_hefen` (`ueberpruefungen_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `weingutmerlot`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weingutmerlot`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NULL,
  `password` VARCHAR(255) NOT NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `weingutmerlot`.`weinarten`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weingutmerlot`.`weinarten` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `art` ENUM("Rotwein", "Weißwein", "Roséwein", "Schaumwein", "Perlwein", "Dessertwein", "Likörwein") NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `weingutmerlot`.`weinarten_has_weinreben`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weingutmerlot`.`weinarten_has_weinreben` (
  `weinarten_id` INT NOT NULL,
  `weinreben_id` INT NOT NULL,
  `menge` DECIMAL(13,10) NOT NULL COMMENT 'Menge In Prozent von Chargengröße',
  PRIMARY KEY (`weinarten_id`, `weinreben_id`),
  CONSTRAINT `fk_weinarten_has_weinreben_weinarten1`
    FOREIGN KEY (`weinarten_id`)
    REFERENCES `weingutmerlot`.`weinarten` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_weinarten_has_weinreben_weinreben1`
    FOREIGN KEY (`weinreben_id`)
    REFERENCES `weingutmerlot`.`weinreben` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_weinarten_has_weinreben_weinreben1_idx` ON `weingutmerlot`.`weinarten_has_weinreben` (`weinreben_id` ASC) VISIBLE;

CREATE INDEX `fk_weinarten_has_weinreben_weinarten1_idx` ON `weingutmerlot`.`weinarten_has_weinreben` (`weinarten_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `weingutmerlot`.`weine`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weingutmerlot`.`weine` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `weinart_id` INT NOT NULL,
  `suessegrad` ENUM("TROCKEN", "HALBTROCKEN", "LIEBLICH", "SUESS", "FEINHERB") NOT NULL,
  `alkoholgehalt` INT NOT NULL,
  `beschreibung` MEDIUMTEXT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_weine_weinart1`
    FOREIGN KEY (`weinart_id`)
    REFERENCES `weingutmerlot`.`weinarten` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_weine_weinart1_idx` ON `weingutmerlot`.`weine` (`weinart_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `weingutmerlot`.`weine_has_zutaten`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weingutmerlot`.`weine_has_zutaten` (
  `weine_id` INT NOT NULL,
  `zutaten_id` INT NOT NULL,
  `menge` INT NOT NULL DEFAULT 0 COMMENT 'Menge In Prozent von Chargengröße',
  `menge_einheit` VARCHAR(10) NOT NULL DEFAULT 'g' COMMENT 'Wird immer auf einen Liter gerechnet',
  PRIMARY KEY (`weine_id`, `zutaten_id`),
  CONSTRAINT `fk_weine_has_zutaten_wein1`
    FOREIGN KEY (`weine_id`)
    REFERENCES `weingutmerlot`.`weine` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_weine_has_zutaten_zutaten1`
    FOREIGN KEY (`zutaten_id`)
    REFERENCES `weingutmerlot`.`zutaten` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_weine_has_zutaten_zutaten1_idx` ON `weingutmerlot`.`weine_has_zutaten` (`zutaten_id` ASC) VISIBLE;

CREATE INDEX `fk_weine_has_zutaten_wein1_idx` ON `weingutmerlot`.`weine_has_zutaten` (`weine_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `weingutmerlot`.`weinreben`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weingutmerlot`.`weinreben` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `weingutmerlot`.`zutaten`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weingutmerlot`.`zutaten` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
