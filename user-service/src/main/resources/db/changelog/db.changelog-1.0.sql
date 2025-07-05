--liquibase formatted sql

--changeset chuckcha:create-schema
CREATE SCHEMA IF NOT EXISTS users_schema;

--changeset chuckcha:users-table-added
CREATE TABLE IF NOT EXISTS users_schema.users
(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    modified_at TIMESTAMP,
    role VARCHAR(32)
);

--changeset chuckcha:created-admin-account
INSERT INTO users_schema.users (username, password, role, created_at, modified_at)
VALUES ('admin', '{bcrypt}$2a$12$h9N585Udl9yzdFn/pS5TueNzn7.Xc8ab5eI.KzcGlOk5KJAzALNQO', 'ROLE_ADMIN', NOW(), NOW());