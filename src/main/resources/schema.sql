CREATE TABLE IF NOT EXISTS participants
(
    id         BIGSERIAL PRIMARY KEY,
    first_name varchar(50),
    last_name  varchar(50),
    age        int,
    city       varchar(50)
);

CREATE TABLE IF NOT EXISTS winner_records
(
    id             bigserial PRIMARY KEY,
    participant_id bigint REFERENCES participants (id),
    sum            int,
    created_at     timestamptz
)