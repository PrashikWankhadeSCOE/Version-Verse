DROP DATABASE IF EXISTS `versionverse`;
CREATE DATABASE `versionverse`;

USE `versionverse`;

CREATE TABLE `languages`(
	`lang_id` INT PRIMARY KEY AUTO_INCREMENT,
	`lang` VARCHAR(10) NOT NULL
);

CREATE TABLE `version_info` (
	`lang_id` INT,
    `version` INT,
    `feature` VARCHAR(50),
    FOREIGN KEY(`lang_id`) REFERENCES `languages`(`lang_id`)
);

-- Inserting into the Languages Table

INSERT INTO languages (lang_id,lang)
VALUES (1,'C'),
	   (2,'C++'),
       (3,'Java'),
       (4,'Python');
       
-- Inserting into the version_info table
INSERT INTO version_info (lang_id,version,feature)
VALUES (3,1,'java_ver1.txt'),
       (3,5,'java_ver5.txt'),
       (3,6,'java_ver6.txt'),
       (3,7,'java_ver7.txt'),
       (3,8,'java_ver8.txt'),
       (3,9,'java_ver9.txt'),
       (3,10,'java_ver10.txt'),
       (3,11,'java_ver11.txt'),
       (3,12,'java_ver12.txt'),
       (3,13,'java_ver13.txt'),
       (3,14,'java_ver14.txt'),
       (3,15,'java_ver15.txt'),
       (3,16,'java_ver16.txt'),
       (3,17,'java_ver17.txt');
       
INSERT INTO version_info(lang_id,version,feature)
VALUES (2,98,'c++_98.txt'),
	   (2,3,'c++_3.txt'),
	   (2,11,'c++_11.txt'),
       (2,14,'c++_14.txt'),
       (2,17,'c++_17.txt'),
       (2,20,'c++_20.txt'),
       (2,23,'c++_23.txt');

INSERT INTO version_info(lang_id,version,feature)
VALUES (1,72,'c_72.txt'),
	   (1,90,'c_90.txt'),
       (1,99,'c_99.txt'),
       (1,11,'c_11.txt'),
       (1,18,'c_18.txt');

INSERT INTO version_info(lang_id,version,feature)
VALUES (4,1,'python_1.txt'),
	   (4,2,'python_2.txt'),
       (4,3,'python_3.txt');