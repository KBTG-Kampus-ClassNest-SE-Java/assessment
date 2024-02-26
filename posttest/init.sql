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
INSERT INTO user_ticket(userId, phone, name ) VALUES('1234567890', '012345678', 'Foo');
INSERT INTO user_ticket(userId, phone, name ) VALUES('0987654321', '12345678', 'Sally');
INSERT INTO lottery(ticket, price, amount, profile_id) VALUES('111111', 120, 1,'1234567890');
INSERT INTO lottery(ticket, price, amount, profile_id) VALUES('222222', 100, 2,'1234567890');
INSERT INTO lottery(ticket, price, amount, profile_id) VALUES('333333', 100, 2,'0987654321');
INSERT INTO lottery(ticket, price, amount, profile_id) VALUES('444444', 100, 2,'0987654321');