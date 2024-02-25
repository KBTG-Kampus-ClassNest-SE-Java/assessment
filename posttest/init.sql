-- Drop tables if they exist
DROP TABLE IF EXISTS user_ticket CASCADE;
DROP TABLE IF EXISTS lottery CASCADE;

-- Create the lottery table
CREATE TABLE lottery (
    id SERIAL PRIMARY KEY,
    ticket CHAR(6) NOT NULL UNIQUE CHECK (ticket ~ '^[0-9]{6}$'),
    amount BIGINT NOT NULL,
    price DECIMAL NOT NULL,
    CONSTRAINT chk_ticket_size CHECK (LENGTH(ticket) = 6)
);

-- Create the user_ticket table with a foreign key relationship to lottery.id
CREATE TABLE user_ticket (
    id SERIAL PRIMARY KEY,
    userId CHAR(10) NOT NULL CHECK (userId ~ '^[0-9]{10}$'),
    transaction_type VARCHAR NOT NULL,
    transaction_amount BIGINT NOT NULL,
    lottery_id BIGINT NOT NULL, -- Foreign key column
    CONSTRAINT fk_lottery FOREIGN KEY (lottery_id) REFERENCES lottery(id) -- Foreign key relationship
);