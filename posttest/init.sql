-- Drop tables if they exist
DROP TABLE IF EXISTS lottery CASCADE;
DROP TABLE IF EXISTS user_ticket CASCADE;

CREATE TABLE lottery
(
    id              SERIAL PRIMARY KEY,
    ticket_id       VARCHAR(6) UNIQUE NOT NULL,
    create_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    update_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE user_ticket
(
    id              SERIAL PRIMARY KEY,
    user_id         VARCHAR(10) NOT NULL,
    ticket_id       VARCHAR(6)  NOT NULL,
    create_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    update_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Initial data
INSERT INTO lottery (ticket_id)
VALUES (123456),
       (234567),
       (345678),
       (456789),
       (567890);

INSERT INTO user_ticket (user_id, ticket_id)
VALUES ('U00001', 123456),
       ('U00002', 234567),
       ('U00002', 567890),
       ('U00003', 345678),
       ('U00004', 456789),
       ('U00005', 567890);