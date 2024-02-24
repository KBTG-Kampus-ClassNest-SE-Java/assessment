DROP TABLE IF EXISTS lottery CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS user_ticket CASCADE;

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE lottery (
    lottery_id SERIAL PRIMARY KEY,
    price INTEGER NOT NULL,
    amount INTEGER NOT NULL,


CREATE TABLE user_ticket (
    ticket_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(user_id),
    lottery_id INTEGER NOT NULL REFERENCES lottery(lottery_id),
);

-- Initial data
--INSERT INTO users(id, name) VALUES (1, 'Ninja');  -- Manually set the id to avoid SERIAL auto-increment
--INSERT INTO lotteries(price, amount, user_id) VALUES (80, 1, 1);  -- Use the user_id from the users table
