USE `java-db-training`;


SET FOREIGN_KEY_CHECKS = 0;


--
-- Table structure for table `user`
--
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` char(80) NOT NULL,
  `first_name` varchar(64) NOT NULL DEFAULT '' ,
  `last_name` varchar(64) NOT NULL DEFAULT '',
  `email` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


--
-- Table structure for table `user_detail`
--
DROP TABLE IF EXISTS `user_detail`;
create table `user_detail`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `desc`  varchar(64) NOT NULL DEFAULT '' ,
    `job`  varchar(64) NOT NULL DEFAULT '' ,
    `user_id` int(11) NOT NULL,
    PRIMARY KEY (`id`,`user_id`),
	FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
   -- ON DELETE NO ACTION ON UPDATE NO ACTION
);

alter table user_detail  CHANGE COLUMN `desc`  `description` varchar(255) default '';





--
-- Table structure for table `role`
--
DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11)  NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




--
-- Table structure for table `users_roles`
--
DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,

  PRIMARY KEY (`id`,`user_id`,`role_id`),

  -- CONSTRAINT `FK_USER_05`
  FOREIGN KEY (`user_id`)
  REFERENCES `user` (`id`)
--  ON DELETE NO ACTION ON UPDATE NO ACTION
  ,
  -- CONSTRAINT `FK_ROLE`
  FOREIGN KEY (`role_id`)
  REFERENCES `role` (`id`)
 -- ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table users_roles add column `commen` varchar(255) default '';


alter table users_roles  CHANGE COLUMN `commen`  `common` varchar(255) default '';


SET FOREIGN_KEY_CHECKS = 1;