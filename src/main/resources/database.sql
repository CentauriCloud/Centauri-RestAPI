CREATE SCHEMA rest;

USE rest;

CREATE TABLE user (
  id         INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username   VARCHAR(30) NOT NULL,
  password   VARCHAR(30) NOT NULL,
  email      VARCHAR(30) NOT NULL,
  user_group VARCHAR(30) NOT NULL,
  lastLogin  TIMESTAMP                        DEFAULT NULL,
  active     BOOLEAN     NOT NULL
);