-- Drop tables if they exist
DROP TABLE IF EXISTS lottery CASCADE;
DROP TABLE IF EXISTS user_ticket CASCADE;

CREATE TABLE user_ticket (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20) NOT NULL,
    name VARCHAR(20)  NOT NULL
);


CREATE TABLE lottery (
    id SERIAL PRIMARY KEY,
    ticket VARCHAR(6) UNIQUE NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    amount BIGINT  NOT NULL,
    profile_email VARCHAR(255) REFERENCES user_ticket(email) ON DELETE CASCADE
);

-- Initial data
INSERT INTO user_ticket(email, phone, name ) VALUES('foo@email.com', '012345678', 'Foo');
INSERT INTO user_ticket(email, phone, name ) VALUES('sally@email.com', '12345678', 'Sally');
INSERT INTO lottery(ticket, price, amount, profile_email) VALUES('111111', 120, 1,'foo@email.com');
INSERT INTO lottery(ticket, price, amount, profile_email) VALUES('222222', 100, 2,'foo@email.com');
INSERT INTO lottery(ticket, price, amount, profile_email) VALUES('333333', 100, 2,'sally@email.com');
INSERT INTO lottery(ticket, price, amount, profile_email) VALUES('444444', 100, 2,'sally@email.com');