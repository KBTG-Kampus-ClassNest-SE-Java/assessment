DROP TABLE IF EXISTS lottery;
DROP TABLE IF EXISTS user_ticket;

CREATE TABLE lottery (
    ticket_number VARCHAR(6) NOT NULL PRIMARY KEY,
    price DECIMAL NOT NULL,
    amount_available INT NOT NULL,
    last_updated TIMESTAMP WITHOUT TIME ZONE DEFAULT (NOW()),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT (NOW())
);

CREATE TABLE users (
    user_id VARCHAR(100) NOT NULL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

CREATE TABLE user_ticket (
    ticket_id SERIAL PRIMARY KEY,
    ticket_number VARCHAR(6) NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    price_paid DECIMAL NOT NULL,
    is_sold_back_flag VARCHAR(1) DEFAULT 'N',
    purchase_date TIMESTAMP WITHOUT TIME ZONE DEFAULT (NOW()),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    last_updated TIMESTAMP WITHOUT TIME ZONE DEFAULT (NOW()),
    FOREIGN KEY (ticket_number) REFERENCES lottery(ticket_number),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE INDEX idx_ticket_number ON lottery(ticket_number);
CREATE INDEX idx_user_id ON user_ticket(user_id);
