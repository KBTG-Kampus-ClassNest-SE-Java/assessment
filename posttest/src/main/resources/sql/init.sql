DROP TABLE IF EXISTS lottery;

CREATE TABLE lottery (
    id SERIAL PRIMARY KEY,
    ticket_id VARCHAR(6) NOT NULL,
    price INTEGER NOT NULL,
    amount INTEGER NOT NULL
);
