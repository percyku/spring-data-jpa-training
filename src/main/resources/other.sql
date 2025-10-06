
-- select * from user;

INSERT INTO `user` (`username`,`password`,`first_name`, `last_name`, `email`)
VALUES
('studnet1','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','student1', 'test', 'studnet1@gmail.com'),
('instructor1','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','teacher', 'test', 'teacher1@gmail.com');




-- select * from role;

INSERT INTO `role` (name)
VALUES
('ROLE_STUDENT'),('ROLE_TEACHER'),('ROLE_DEFAULT');



INSERT INTO `users_roles` (user_id,role_id)
VALUES
(1, 1),
(2, 2);





SELECT user.*,user_detail.description,user_detail.job,role.name,users_roles.common FROM user
left join user_detail on
user.id = user_detail.user_id
left join users_roles on
user.id = users_roles.user_id
left join role on
role.id = users_roles.role_id;