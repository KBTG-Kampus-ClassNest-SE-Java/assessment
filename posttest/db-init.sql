-- Drop tables if they exist
DROP TABLE IF EXISTS lottery CASCADE;

CREATE TABLE lottery
(
    id              SERIAL PRIMARY KEY,
    ticket_number   VARCHAR(6) NOT NULL,
    price           INTEGER NOT NULL,
    amount          INTEGER NOT NULL

);

INSERT INTO lottery(ticket_number, price, amount)
VALUES ('000001', 80, 1);

INSERT INTO lottery(ticket_number, price, amount)
VALUES ('000002', 80, 1);

INSERT INTO lottery(ticket_number, price, amount)
VALUES ('000003', 80, 1);