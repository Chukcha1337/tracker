--liquibase formatted sql

--changeset chuckcha:schema-create
CREATE SCHEMA IF NOT EXISTS tasks_schema;

--changeset chuckcha:users-table-added
CREATE TABLE IF NOT EXISTS tasks_schema.tasks
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(255),
    deadline TIMESTAMP,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

