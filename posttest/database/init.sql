DROP TABLE IF EXISTS lottery;
DROP TABLE IF EXISTS user_ticket;

CREATE TABLE lottery (
    ticket_id SERIAL PRIMARY KEY,
    ticket_number VARCHAR(6) NOT NULL,
    price DECIMAL NOT NULL,
    amount_available INT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT (NOW())
);

CREATE TABLE user_ticket (
    purchase_id SERIAL PRIMARY KEY,
    user_id VARCHAR(10) NOT NULL,
    ticket_id INT NOT NULL,
    purchase_date TIMESTAMP WITHOUT TIME ZONE DEFAULT (NOW()),
    FOREIGN KEY (ticket_id) REFERENCES lottery(ticket_id)
);

CREATE INDEX idx_ticket_number ON lottery(ticket_number);
CREATE INDEX idx_user_id ON user_ticket(user_id);
