CREATE SCHEMA rest;

USE rest;

CREATE TABLE `group` (
  id          INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name        VARCHAR(30) NOT NULL UNIQUE,
  description TEXT        NOT NULL,
  active      BOOLEAN     NOT NULL
);

CREATE TABLE user (
  id        INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username  VARCHAR(30) NOT NULL UNIQUE,
  password  VARCHAR(30) NOT NULL,
  email     VARCHAR(30) NOT NULL UNIQUE,
  group_fk  INT         NOT NULL,
  lastLogin TIMESTAMP,
  active    BOOLEAN     NOT NULL,
  FOREIGN KEY (group_fk) REFERENCES `group` (id)
);