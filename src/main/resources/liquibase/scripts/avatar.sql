-- liquibase formatted sql

-- changeset mzhitenev:1
CREATE TABLE avatar
(

    id        BIGSERIAL PRIMARY KEY,
    file_path  varchar(255),
    file_size  bigint,
    media_type varchar(255),
    data      bytea

);