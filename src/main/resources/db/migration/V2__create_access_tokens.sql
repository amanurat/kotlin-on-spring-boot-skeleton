CREATE TABLE `access_tokens` (
  `user_id` bigint(20) NOT NULL,
  `_version` smallint(6),
  `expires_in` datetime NOT NULL,
  `token` binary(16) NOT NULL,
  `refresh_token` binary(16) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `refresh_token_idx` (`refresh_token`),
  UNIQUE KEY `token_idx` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;