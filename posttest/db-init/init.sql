-- Drop tables if they exist
DROP TABLE IF EXISTS lottery CASCADE;
DROP TABLE IF EXISTS user_ticket CASCADE;

-- Create lottery table
CREATE TABLE lottery (
    ticket_id VARCHAR(6) PRIMARY KEY,
    price INTEGER NOT NULL,
    amount INTEGER NOT NULL
);

-- Create user_ticket table
CREATE TABLE user_ticket (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(10) NOT NULL,
    ticket_id VARCHAR(6) NOT NULL,
    FOREIGN KEY (ticket_id) REFERENCES lottery(ticket_id)
);

-- Initial data
INSERT INTO lottery(ticket_id, price, amount) VALUES('000001', 80, 1), ('000002', 80, 1);

