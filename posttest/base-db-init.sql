DROP TABLE IF EXISTS ROLE CASCADE;
DROP TABLE IF EXISTS PERMISSION CASCADE;
CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE ROLE
(
    role_id  SERIAL     PRIMARY KEY,
    role     VARCHAR(32) UNIQUE  NOT NULL
);

CREATE TABLE PERMISSION
(
    permission_id SERIAL     PRIMARY KEY,
    permission    VARCHAR(32) UNIQUE  NOT NULL
);

CREATE TABLE USERS
(
    user_id   CHAR(10)     PRIMARY KEY,
    username VARCHAR(32) UNIQUE  NOT NULL,
    password  VARCHAR(128) NOT NULL
);

CREATE TABLE USER_ROLE
(
    user_role_id   SERIAL     PRIMARY KEY,
    user_id CHAR(10)   NOT NULL,
    role_id  INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USERS (user_id),
    FOREIGN KEY (role_id) REFERENCES ROLE (role_id)
);


CREATE TABLE USER_PERMISSION
(
    user_permission_id SERIAL PRIMARY KEY,
    user_id CHAR(10)   NOT NULL,
    permission_id  INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USERS (user_id),
    FOREIGN KEY (permission_id) REFERENCES PERMISSION (permission_id)
);

-- Determine Role value
INSERT INTO ROLE(role) VALUES ('ADMIN');
INSERT INTO ROLE(role) VALUES ('MEMBER');

-- Determine Permission value
INSERT INTO PERMISSION(permission) VALUES ('CREATE_LOTTERY');
INSERT INTO PERMISSION(permission) VALUES ('READ_LOTTERY');
INSERT INTO PERMISSION(permission) VALUES ('UPDATE_LOTTERY');
INSERT INTO PERMISSION(permission) VALUES ('DELETE_LOTTERY');

-- Determine Admin user value.
INSERT INTO USERS(user_id, username, password) VALUES ('0000000001', 'admin', crypt('password', gen_salt('bf')));
INSERT INTO USER_ROLE(user_id, role_id) VALUES ('0000000001', 1);
INSERT INTO USER_PERMISSION(user_id, permission_id) VALUES ('0000000001', 1);
INSERT INTO USER_PERMISSION(user_id, permission_id) VALUES ('0000000001', 2);
INSERT INTO USER_PERMISSION(user_id, permission_id) VALUES ('0000000001', 3);
INSERT INTO USER_PERMISSION(user_id, permission_id) VALUES ('0000000001', 4);

-- Determine first user in system value.
INSERT INTO USERS(user_id, username, password) VALUES ('0000000002', 'helloworld', crypt('helloworld', gen_salt('bf')));
INSERT INTO USER_ROLE(user_id, role_id) VALUES ('0000000002', 2);
INSERT INTO USER_PERMISSION(user_id, permission_id) VALUES ('0000000002', 2);