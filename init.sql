-- Drop tables if they exist
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS lottery CASCADE;

CREATE TABLE users (
                         id SERIAL PRIMARY KEY,
                         username VARCHAR(255) UNIQUE NOT NULL,
                         password VARCHAR(255) NOT NULL
);


CREATE TABLE lottery (
                        id SERIAL PRIMARY KEY,
                        lottery_number VARCHAR(255) UNIQUE NOT NULL,
                        price INT NOT NULL,
                        amount INT NOT NULL,
                        user_id INTEGER REFERENCES users(id) ON DELETE CASCADE
);

-- Initial data
INSERT INTO users( username,password) VALUES( 'John', 'password');
INSERT INTO lottery( lottery_number,price,amount,user_id) VALUES( '12356', '50', '1',1);

