CREATE TABLE `users` (
  `_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `_version` smallint(6),
  `_type` varchar(255) NOT NULL,
  `uid` varchar(16) NOT NULL,
  `login_id` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `activation_temp_data` text DEFAULT NULL,
  `activation_code` varchar(4) DEFAULT NULL,
  `activation_expires_in` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`_id`),
  UNIQUE KEY `login_id_idx` (`_type`,`login_id`,`deleted_at`),
  UNIQUE KEY `UK_uid_idx` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;