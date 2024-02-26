CREATE TABLE users (
    user_id INTEGER PRIMARY KEY,
    display_name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE lottery (
    id SERIAL PRIMARY KEY,
    ticket VARCHAR(6) NOT NULL,
    price DECIMAL(6, 2) NOT NULL,
    amount INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE user_ticket (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users (user_id),
    lottery_id INTEGER REFERENCES lottery (id),
    created_at TIMESTAMP NOT NULL
);

INSERT INTO
    users (user_id, display_name, created_at)
VALUES
    (1000000001, 'Alice', NOW ()),
    (1000000002, 'Bob', NOW ());

INSERT INTO
    lottery (ticket, price, amount, created_at)
VALUES
    ('000001', 80, 1, NOW ()),
    ('000002', 80, 2, NOW ()),
    ('123456', 80, 3, NOW ());

INSERT INTO
    user_ticket (user_id, lottery_id, created_at)
VALUES
    (1000000001, 1, NOW ());