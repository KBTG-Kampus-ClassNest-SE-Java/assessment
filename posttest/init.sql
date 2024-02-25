DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
                       user_id INTEGER PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(255) NOT NULL
);
CREATE TABLE lottery (
                         ticket_id SERIAL PRIMARY KEY,
                         ticket_number VARCHAR(255) UNIQUE NOT NULL,
                         amount INT NOT NULL,
                         price INT NOT NULL
);
CREATE TABLE user_ticket (
                             user_ticket_id SERIAL PRIMARY KEY,
                             user_id INTEGER REFERENCES users(user_id),
                             ticket_id INTEGER REFERENCES lottery(ticket_id),
                             amount INT NOT NULL

);

INSERT INTO users (user_id,username, password, phone) VALUES (100000002,'John', 'password','0812345687');
INSERT INTO lottery (ticket_number,amount, price) VALUES ('653215', 1, 50);
INSERT INTO lottery (ticket_number,amount, price) VALUES ('912473', 1, 50);
INSERT INTO lottery (ticket_number,amount, price) VALUES ('112345', 1, 50);
INSERT INTO lottery (ticket_number,amount, price) VALUES ('112341', 1, 50);
INSERT INTO user_ticket (user_id, ticket_id, amount) VALUES (100000002, 1, 1);
INSERT INTO user_ticket (user_id, ticket_id, amount) VALUES (100000002, 2, 1);
INSERT INTO user_ticket (user_id, ticket_id, amount) VALUES (100000002, 3, 1);



