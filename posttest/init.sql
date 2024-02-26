-- Drop tables if they exist
DROP TABLE IF EXISTS lottery CASCADE;
DROP TABLE IF EXISTS user_ticket CASCADE;

CREATE TABLE user_ticket (
                             id SERIAL PRIMARY KEY,
                             userId VARCHAR(10) UNIQUE NOT NULL,
                             phone VARCHAR(20) NOT NULL,
                             name VARCHAR(20)  NOT NULL
);


CREATE TABLE lottery (
                         id SERIAL PRIMARY KEY,
                         ticket VARCHAR(6) UNIQUE NOT NULL,
                         price DOUBLE PRECISION NOT NULL,
                         amount BIGINT  NOT NULL,
                         profile_id VARCHAR(255) REFERENCES user_ticket(userId) ON DELETE CASCADE
);

-- Initial data
INSERT INTO user_ticket(userId, phone, name ) VALUES('1234567890', '0967347583', 'JK');
INSERT INTO user_ticket(userId, phone, name ) VALUES('4264367347', '0978357680', 'ABIGALE');
INSERT INTO lottery(ticket, price, amount, profile_id) VALUES('111111', 80, 1,'1234567890');
INSERT INTO lottery(ticket, price, amount, profile_id) VALUES('222222', 80, 2,'1234567890');
INSERT INTO lottery(ticket, price, amount, profile_id) VALUES('333333', 80, 2,'4264367347');
INSERT INTO lottery(ticket, price, amount, profile_id) VALUES('444444', 80, 2,'4264367347');