-- Drop tables if they exist
DROP TABLE IF EXISTS lottery CASCADE;
DROP TABLE IF EXISTS user_ticket CASCADE;

CREATE TABLE lottery(
    ticket_id VARCHAR(6) PRIMARY KEY,
    price int NOT NULL,
    amount int NOT NULL
);

CREATE TABLE user_ticket (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(10) NOT NULL,
    ticket_id VARCHAR(6) NOT NULL
);