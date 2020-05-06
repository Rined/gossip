TRUNCATE TABLE user_role CASCADE;
TRUNCATE TABLE usr CASCADE;

INSERT INTO usr(id, active, password, username)
VALUES (1, true, '$2a$10$l7D/7/56/r8PzDybbvbYZe.ffz7coZzcj2wSlHWlhy2XG4s5/01Eq', 'admin'),
       (2, true, '$2a$10$l7D/7/56/r8PzDybbvbYZe.ffz7coZzcj2wSlHWlhy2XG4s5/01Eq', 'not_admin');

INSERT INTO user_role (user_id, roles)
VALUES (1, 'USER'),
       (1, 'ADMIN'),
       (2, 'USER');
