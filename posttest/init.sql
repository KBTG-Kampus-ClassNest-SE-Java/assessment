DROP TABLE IF EXISTS lotteries CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE lotteries (
    id SERIAL PRIMARY KEY,
    price INTEGER NOT NULL,
    amount INTEGER NOT NULL,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE
);

-- Initial data
--INSERT INTO users(id, name) VALUES (1, 'Ninja');  -- Manually set the id to avoid SERIAL auto-increment
--INSERT INTO lotteries(price, amount, user_id) VALUES (80, 1, 1);  -- Use the user_id from the users table
