CREATE SEQUENCE public.lottery_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE public.user_ticket_user_id_seq
    START WITH 1000000000
    INCREMENT BY 1
    MINVALUE 1000000000
    MAXVALUE 9999999999
    CACHE 1;

CREATE TABLE public.lottery
(
    id INTEGER NOT NULL,
    amount INTEGER,
    price NUMERIC(38,2),
    ticket_number CHARACTER VARYING(6) COLLATE pg_catalog."default" UNIQUE,
    user_id INTEGER,
    CONSTRAINT user_id_length CHECK (user_id >= 1000000000 AND user_id <= 9999999999),
    CONSTRAINT lottery_pkey PRIMARY KEY (id)
);

CREATE TABLE public.user_ticket
(
    user_id INTEGER NOT NULL,
    total_price NUMERIC(10,2),
    CONSTRAINT user_ticket_pkey PRIMARY KEY (user_id)
);

INSERT INTO lottery (id, amount, price, ticket_number, user_id)
VALUES (101, 1, 300, '112233', null), (102, 2, 150, '090911', null);

INSERT INTO public.user_ticket (user_id, total_price)
VALUES (1111111111, 00.00), (1222222222, 00.00), (1333333555, 00.00);
