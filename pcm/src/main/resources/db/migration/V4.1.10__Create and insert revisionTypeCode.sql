CREATE TABLE `pcm`.`revision_type_code` (`id` bigint(20) NOT NULL AUTO_INCREMENT,`code` tinyint(4) DEFAULT NULL,`display_name` varchar(255) DEFAULT NULL,PRIMARY KEY (`id`) );

INSERT INTO `pcm`.`revision_type_code` (code, display_name) VALUES (0,'Create new entry');
INSERT INTO `pcm`.`revision_type_code` (code, display_name) VALUES (1,'Changed entry');
INSERT INTO `pcm`.`revision_type_code` (code, display_name) VALUES (2,'Delete entry');