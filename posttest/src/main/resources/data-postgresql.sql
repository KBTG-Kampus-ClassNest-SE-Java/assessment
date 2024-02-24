INSERT INTO lottery (ticket, price ,amount)
VALUES
    ('123456', 80, 3),
    ('654321', 100, 0);

INSERT INTO user_ticket (id, user_id, ticket_id, amount)
VALUES
    (1, 'username', '123456', 1);