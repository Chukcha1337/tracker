--liquibase formatted sql

--changeset chuckcha:user-account
INSERT INTO users_schema.users (username, password, email, role)
VALUES ('user', '{bcrypt}$2a$12$rJMHpzAE24ZT0ksFlYjxPu8FCC9MdLmLhUe1HaWdt7v3c.Dm2mk9q', 'user@mail.ru','USER');