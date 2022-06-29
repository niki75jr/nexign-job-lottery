CREATE TABLE IF NOT EXISTS participants
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    age        INT         NOT NULL,
    city       VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS winner_records
(
    id             BIGSERIAL PRIMARY KEY,
    participant_id BIGINT REFERENCES participants (id) NOT NULL,
    sum            INT                                 NOT NULL,
    created_at     TIMESTAMPTZ                         NOT NULL
)