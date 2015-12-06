CREATE TABLE `roles` (
  `_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `_version` smallint(6),
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`_id`),
  UNIQUE KEY `UK_name_idx` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;