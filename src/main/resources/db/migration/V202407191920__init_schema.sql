CREATE SCHEMA IF NOT EXISTS ${flyway:defaultSchema};
GRANT USAGE ON SCHEMA ${flyway:defaultSchema} to ${service_user};

----------------------------------------------------------------------------------------------------
CREATE TABLE ${flyway:defaultSchema}.telegram_user(
    id BIGSERIAL NOT NULL,
    telegram_id NUMERIC NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_telegram_user PRIMARY KEY (id)
);

COMMENT ON TABLE ${flyway:defaultSchema}.telegram_user IS 'Данные о пользователе';

COMMENT ON COLUMN ${flyway:defaultSchema}.telegram_user.id IS 'Идентификатор записи';
COMMENT ON COLUMN ${flyway:defaultSchema}.telegram_user.telegram_id IS 'Идентификатор пользователя в телеграм';
COMMENT ON COLUMN ${flyway:defaultSchema}.telegram_user.name IS 'Имя пользователя';

GRANT SELECT, INSERT, UPDATE ON ${flyway:defaultSchema}.telegram_user TO ${service_user};
GRANT USAGE ON SEQUENCE ${flyway:defaultSchema}.telegram_user_id_seq TO ${service_user};

----------------------------------------------------------------------------------------------------
CREATE TABLE ${flyway:defaultSchema}.dialog_state_context(
    id BIGSERIAL NOT NULL,
    state VARCHAR(255) NOT NULL,
    context JSONB NOT NULL,
    CONSTRAINT pk_dialog_state_context PRIMARY KEY (id)
);

COMMENT ON TABLE ${flyway:defaultSchema}.dialog_state_context IS 'Данные о диалоге пользователя с ботом';

COMMENT ON COLUMN ${flyway:defaultSchema}.dialog_state_context.id IS 'Идентификатор записи';
COMMENT ON COLUMN ${flyway:defaultSchema}.dialog_state_context.state IS 'Состояние диалога пользователя с ботом';
COMMENT ON COLUMN ${flyway:defaultSchema}.dialog_state_context.context IS 'Контекст диалога пользователя с ботом';

GRANT SELECT, INSERT, UPDATE ON ${flyway:defaultSchema}.dialog_state_context TO ${service_user};
GRANT USAGE ON SEQUENCE ${flyway:defaultSchema}.dialog_state_context_id_seq TO ${service_user};

----------------------------------------------------------------------------------------------------
CREATE TABLE ${flyway:defaultSchema}.ref_income(
    id BIGSERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_ref_income PRIMARY KEY (id)
);

COMMENT ON TABLE ${flyway:defaultSchema}.ref_income IS 'Справочник доходов';

COMMENT ON COLUMN ${flyway:defaultSchema}.ref_income.id IS 'Идентификатор записи';
COMMENT ON COLUMN ${flyway:defaultSchema}.ref_income.name IS 'Наименование';

GRANT SELECT ON ${flyway:defaultSchema}.ref_income TO ${service_user};
GRANT USAGE ON SEQUENCE ${flyway:defaultSchema}.ref_income_id_seq TO ${service_user};

----------------------------------------------------------------------------------------------------
CREATE TABLE ${flyway:defaultSchema}.ref_expense(
    id BIGSERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_ref_expense PRIMARY KEY (id)
);

COMMENT ON TABLE ${flyway:defaultSchema}.ref_expense IS 'Справочник трат';

COMMENT ON COLUMN ${flyway:defaultSchema}.ref_expense.id IS 'Идентификатор записи';
COMMENT ON COLUMN ${flyway:defaultSchema}.ref_expense.name IS 'Наименование';

GRANT SELECT ON ${flyway:defaultSchema}.ref_expense TO ${service_user};
GRANT USAGE ON SEQUENCE ${flyway:defaultSchema}.ref_expense_id_seq TO ${service_user};

----------------------------------------------------------------------------------------------------
CREATE TABLE ${flyway:defaultSchema}.user_income(
    id BIGSERIAL NOT NULL,
    id_telegram_user int8 NOT NULL,
    id_ref_income int8 NOT NULL,
    amount NUMERIC NOT NULL,
    comment TEXT,
    amount_date TIMESTAMP NOT NULL,
    CONSTRAINT pk_user_income PRIMARY KEY (id),
    CONSTRAINT fk_user_income_telegram_user FOREIGN KEY (id_telegram_user) REFERENCES ${flyway:defaultSchema}.telegram_user,
    CONSTRAINT fk_user_income_ref_income FOREIGN KEY (id_ref_income) REFERENCES ${flyway:defaultSchema}.ref_income
);

COMMENT ON TABLE ${flyway:defaultSchema}.user_income IS 'Доходы пользователя';

COMMENT ON COLUMN ${flyway:defaultSchema}.user_income.id IS 'Идентификатор записи';
COMMENT ON COLUMN ${flyway:defaultSchema}.user_income.id_telegram_user IS 'Идентификатор записи с данными пользователя';
COMMENT ON COLUMN ${flyway:defaultSchema}.user_income.id_ref_income IS 'Идентификатор записи дохода из справочника';
COMMENT ON COLUMN ${flyway:defaultSchema}.user_income.amount IS 'Сумма дохода';
COMMENT ON COLUMN ${flyway:defaultSchema}.user_income.comment IS 'Комментарий к доходу';
COMMENT ON COLUMN ${flyway:defaultSchema}.user_income.amount_date IS 'Дата дохода';

GRANT SELECT, INSERT, UPDATE ON ${flyway:defaultSchema}.user_income TO ${service_user};
GRANT USAGE ON SEQUENCE ${flyway:defaultSchema}.user_income_id_seq TO ${service_user};

----------------------------------------------------------------------------------------------------
CREATE TABLE ${flyway:defaultSchema}.user_expense(
    id BIGSERIAL NOT NULL,
    id_telegram_user int8 NOT NULL,
    id_ref_expense int8 NOT NULL,
    amount NUMERIC NOT NULL,
    comment TEXT,
    amount_date TIMESTAMP NOT NULL,
    CONSTRAINT pk_user_expense PRIMARY KEY (id),
    CONSTRAINT fk_user_expense_telegram_user FOREIGN KEY (id_telegram_user) REFERENCES ${flyway:defaultSchema}.telegram_user,
    CONSTRAINT fk_user_income_ref_expense FOREIGN KEY (id_ref_expense) REFERENCES ${flyway:defaultSchema}.ref_expense
);

COMMENT ON TABLE ${flyway:defaultSchema}.user_expense IS 'Траты пользователя';

COMMENT ON COLUMN ${flyway:defaultSchema}.user_expense.id IS 'Идентификатор записи';
COMMENT ON COLUMN ${flyway:defaultSchema}.user_expense.id_telegram_user IS 'Идентификатор записи с данными пользователя';
COMMENT ON COLUMN ${flyway:defaultSchema}.user_expense.id_ref_expense IS 'Идентификатор записи трат из справочника';
COMMENT ON COLUMN ${flyway:defaultSchema}.user_expense.amount IS 'Сумма траты';
COMMENT ON COLUMN ${flyway:defaultSchema}.user_expense.comment IS 'Комментарий к трате';
COMMENT ON COLUMN ${flyway:defaultSchema}.user_expense.amount_date IS 'Дата траты';

GRANT SELECT, INSERT, UPDATE ON ${flyway:defaultSchema}.user_expense TO ${service_user};
GRANT USAGE ON SEQUENCE ${flyway:defaultSchema}.user_expense_id_seq TO ${service_user};