DROP TABLE IF EXISTS lottery;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_ticket;

CREATE TABLE lottery (
    id SERIAL PRIMARY KEY,
    ticket_id VARCHAR(6) UNIQUE NOT NULL,
    price INTEGER NOT NULL,
    amount INTEGER NOT NULL
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(10) UNIQUE NOT NULL,
    total_spent INTEGER DEFAULT 0,
    total_lottery INTEGER DEFAULT 0
);

CREATE TABLE user_ticket (
     id SERIAL PRIMARY KEY,
     user_id VARCHAR(10) REFERENCES users(user_id),
     lottery_id INTEGER REFERENCES lottery(id)
);

INSERT INTO users (user_id) VALUES
    ('0000000000'),
    ('0000000001'),
    ('0000000002'),
    ('0000000003'),
    ('0000000004'),
    ('0000000005'),
    ('0000000006'),
    ('0000000007'),
    ('0000000008'),
    ('0000000009'),
    ('0000000010');
