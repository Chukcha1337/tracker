--liquibase formatted sql

--changeset chuckcha:outbox-table
CREATE TABLE IF NOT EXISTS tasks_schema.outbox_tasks
(
    id                    BIGSERIAL PRIMARY KEY,
    user_id               BIGINT       NOT NULL,
    username              VARCHAR(255) NOT NULL,
    email                 VARCHAR(255) NOT NULL,
    serialized_tasks_json TEXT         NOT NULL,
    created_at            TIMESTAMP DEFAULT now(),
    processed             BOOLEAN   DEFAULT false
);