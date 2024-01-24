create schema if not exists users;

alter schema users owner to justedlev;

create schema if not exists history;

alter schema history owner to justedlev;

CREATE TABLE IF NOT EXISTS shedlock
(
    name       VARCHAR(64),
    lock_until TIMESTAMP(3) NULL,
    locked_at  TIMESTAMP(3) NULL,
    locked_by  VARCHAR(255),
    PRIMARY KEY (name)
);
