DROP TABLE IF EXISTS user_ticket;
DROP TABLE IF EXISTS lottery;

-- Table structure for table `lottery`

CREATE TABLE lottery (
                         ticket_id varchar(6) PRIMARY KEY,
                         amount int NOT NULL,
                         price int NOT NULL
);

-- Table structure for table `user_ticket`
CREATE TABLE user_ticket (
                             id SERIAL PRIMARY KEY, -- Use a reliable auto-incrementing ID
                             user_id varchar(10) NOT NULL,
                             ticket_id varchar(6) NOT NULL,
                             CONSTRAINT user_ticket_id_fk FOREIGN KEY (ticket_id) REFERENCES lottery (ticket_id)
);