CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    telegram_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TYPE state_enum AS ENUM ('state1', 'state2', 'state3');

CREATE TABLE dialog_state_context (
    id BIGSERIAL PRIMARY KEY,
    state state_enum NOT NULL,
    context JSONB NOT NULL
);

CREATE TABLE ref_income (
    id BIGSERIAL PRIMARY KEY,
    tname VARCHAR(255) NOT NULL
);

CREATE TABLE user_income (
    id BIGSERIAL PRIMARY KEY,
    id_user BIGINT REFERENCES "user" (id),
    id_income BIGINT REFERENCES ref_income (id),
    sum NUMERIC NOT NULL,
    comment TEXT,
    date TIMESTAMP NOT NULL
);
