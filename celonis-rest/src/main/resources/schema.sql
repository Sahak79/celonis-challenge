DROP TABLE IF EXISTS `project_generation_task`, `app_role`, `app_user`, `user_role`, `task_type`;

CREATE TABLE `task_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `project_generation_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_value` int(10) NOT NULL DEFAULT 0,
  `to_value` int(10) NOT NULL DEFAULT 0,
  `status` int NOT NULL,
  `type_id` int NOT NULL,
  `creation_date` DATE DEFAULT NULL,
  `storage_location` varchar(255) DEFAULT NULL,
  CONSTRAINT FKa45632871frorjhkek7p18n9y FOREIGN KEY (`type_id`) REFERENCES `task_type` (`id`),
  PRIMARY KEY (`id`)
);

CREATE TABLE `app_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `app_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  CONSTRAINT FK859n2jvi8ivhui0rl0esws6o FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`),
  CONSTRAINT FKa68196081fvovjhkek5m97n3y FOREIGN KEY (`role_id`) REFERENCES `app_role` (`id`)
);