CREATE SCHEMA IF NOT EXISTS venn;

CREATE TABLE venn.fund_ledger (
    customer_id     TEXT            NOT NULL,
    id              TEXT            NOT NULL,
    amount          NUMERIC(18,2)   NOT NULL CHECK (amount > 0),
    event_time      TIMESTAMPTZ     NOT NULL,

    day_bucket      DATE            NOT NULL,
    week_bucket     DATE            NOT NULL,

    inserted_at     TIMESTAMPTZ     NOT NULL DEFAULT now(),

    PRIMARY KEY (customer_id, id)
);

CREATE INDEX idx_ledger_customer_day
    ON venn.fund_ledger (customer_id, day_bucket);

CREATE INDEX idx_ledger_customer_week
    ON venn.fund_ledger (customer_id, week_bucket);
