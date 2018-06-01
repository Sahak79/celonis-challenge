INSERT INTO app_role (role_name, description) VALUES ('STANDARD_USER', 'Standard User - Has no admin rights');
INSERT INTO app_role (role_name, description) VALUES ('ADMIN_USER', 'Admin User - Has permission to perform admin tasks');

-- USER
-- non-encoded(sha256) password: celonis
INSERT INTO app_user (first_name, last_name, password, username) VALUES ('Sahak', 'Babayan', 'a58782fbc3ee87230841173cef58353dd814ab486f27b5264b56f04b97409d04', 'celonis');
INSERT INTO app_user (first_name, last_name, password, username) VALUES ('Admin', 'Admin', 'a58782fbc3ee87230841173cef58353dd814ab486f27b5264b56f04b97409d04', 'admin');

INSERT INTO user_role(user_id, role_id) VALUES(1,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,2);

INSERT INTO task_type(`id`, `name`, `description`) VALUES (1, 'Generate sample project task', 'This generates the simple project that should be used to finish challenge 2 and 3.');
INSERT INTO task_type(`id`, `name`, `description`) VALUES (2, 'Count from X to Y', 'This task counts from given X to given Y.');

-- Populate project task table
INSERT INTO project_generation_task(`from_value`, `to_value`, `type_id`, `status`, `creation_date`, `storage_location`) VALUES (1, 10, 2, 1, '2012-09-17', 'disk');
INSERT INTO project_generation_task(`from_value`, `to_value`, `type_id`, `status`, `creation_date`, `storage_location`) VALUES (1, 20, 2, 2, '2012-09-17', 'disk');
INSERT INTO project_generation_task(`from_value`, `to_value`, `type_id`, `status`, `creation_date`, `storage_location`) VALUES (1, 40, 2, 4, '2012-09-17', 'disk');


