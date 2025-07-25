--liquibase formatted sql

--changeset chuckcha:outbox-table
CREATE TABLE IF NOT EXISTS users_schema.outbox_users
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    username VARCHAR(64) NOT NULL,
    email VARCHAR(64) NOT NULL UNIQUE,
    created_at TIMESTAMP,
    processed BOOLEAN
);