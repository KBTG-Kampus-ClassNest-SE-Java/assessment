CREATE TABLE lottery (
    ticket_no VARCHAR(6) PRIMARY KEY,
    price INT CHECK (price > 0) NOT NULL,
    amount INT CHECK (amount > 0) NOT NULL
);

CREATE TABLE user_lottery (
    id INT PRIMARY KEY,
    user_id VARCHAR(10) NOT NULL,
    ticket_no VARCHAR(6) NOT NULL,

    FOREIGN KEY (ticket_no) REFERENCES lottery(ticket_no)
);