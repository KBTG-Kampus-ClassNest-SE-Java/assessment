DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(255) NOT NULL
);
CREATE TABLE lottery (
                         lottery_id SERIAL PRIMARY KEY,
                         lottery_number VARCHAR(255) UNIQUE NOT NULL,
                         price INT NOT NULL,
                         amount INT NOT NULL
);
CREATE TABLE user_ticket (
                             user_ticket_id SERIAL PRIMARY KEY,
                             user_id INTEGER REFERENCES users(user_id),
                             lottery_id INTEGER REFERENCES lottery(lottery_id),
                             amount INT NOT NULL

);


INSERT INTO users (username, password, phone) VALUES ('John', 'password','0812345687');
INSERT INTO users (username, password, phone) VALUES ('Deph', 'password','0812345602');
INSERT INTO lottery (lottery_number, price, amount) VALUES ('12356', 50, 1);
INSERT INTO lottery (lottery_number, price, amount) VALUES ('8835566', 60, 1);
INSERT INTO lottery (lottery_number, price, amount) VALUES ('99966', 70, 1);
INSERT INTO lottery (lottery_number, price, amount) VALUES ('77235566', 80, 1);
INSERT INTO lottery (lottery_number, price, amount) VALUES ('998855', 90, 1);
INSERT INTO user_ticket (user_id, lottery_id, amount) VALUES (1, 1, 1);
INSERT INTO user_ticket (user_id, lottery_id, amount) VALUES (1, 2, 1);
INSERT INTO user_ticket (user_id, lottery_id, amount) VALUES (1, 3, 1);



