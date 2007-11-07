--
--    PAccMan document creation script for database.
--

-- 
-- Document information table. Contains various information for this account file.
--    -> title: the title of the document
--    -> version: format (model) version
--    -> creationdate: creation date and time (in UTC)
--    -> lastupdate: last update date (in UTC)
--

CREATE TABLE DOCINFO(
    NAME VARCHAR(100) NOT NULL CONSTRAINT DOCINFO_PK PRIMARY KEY,
    VALUE VARCHAR(100) NOT NULL
);

CREATE TABLE OBJ_BANKS (
    BANK_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT BANKS_PK PRIMARY KEY, 
    NAME VARCHAR(100) NOT NULL,
    AGENCY VARCHAR(1024),
    ADDRESS_NUMBER VARCHAR(1024),
    ADDRESS_LINE1 VARCHAR(1024),
    ADDRESS_LINE2 VARCHAR(1024),
    ADDRESS_CITY VARCHAR(1024),
    ADDRESS_ZIP VARCHAR(1024),
    ADDRESS_COUNTRY VARCHAR(1024),
    TELEPHONE VARCHAR(1024),
    FAX VARCHAR(1024),
    INTERNET VARCHAR(1024),
    EMAIL VARCHAR(1024)
);

CREATE TABLE OBJ_CATEGORIES (
    CATEGORY_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT CATEGORIES_PK PRIMARY KEY,
    NAME VARCHAR(100) NOT NULL,
    DESCRIPTION  VARCHAR(1024),
    IS_INCOME SMALLINT, -- NULL for sub-categories (inherited from root category)
    PARENT_CATEGORY_ID BIGINT, -- for sub-categories (NULL for root categories)

    CONSTRAINT CATEGORY__CATEGORY_FK FOREIGN KEY(PARENT_CATEGORY_ID) REFERENCES OBJ_CATEGORIES(CATEGORY_ID)
);

CREATE TABLE OBJ_PAYEES(
    PAYEE_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT PAYEES_PK PRIMARY KEY, 
    NAME VARCHAR(100)
);

CREATE TABLE OBJ_PAYMENT_METHODS (
    PAYMENT_METHOD_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT PAYMENT_METHODS_PK PRIMARY KEY, 
    NAME VARCHAR(100)
);

CREATE TABLE OBJ_ACCOUNTS (
    ACCOUNT_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT ACCOUNTS_PK PRIMARY KEY, 
    NAME VARCHAR(100),
    BANK_ID BIGINT NOT NULL,
    INITIAL_BALANCE DECIMAL(20,2) NOT NULL,
    ACCOUNT_NUMBER VARCHAR(1024) NOT NULL,
    ACCOUNT_NUMBER_KEY VARCHAR(1024),
    NOTE VARCHAR(1024),
    HOLDER_NAME VARCHAR(1024),
    HOLDER_ADDRESS VARCHAR(1024),
    LAST_RECONCILIATION_DATE TIMESTAMP,
    LAST_RECONCILIATION_BALANCE DECIMAL(20,2),
    PENDING_RECONCILIATION SMALLINT,
    PENDING_RECONCILIATION_DATE TIMESTAMP,
    PENDING_RECONCILIATION_BALANCE DECIMAL(20,2),

    CONSTRAINT ACCOUNT__BANK_FK FOREIGN KEY(BANK_ID) REFERENCES OBJ_BANKS(BANK_ID)
);

-- Table for transaction and scheduled transaction
CREATE TABLE OBJ_TRANSACTIONS (
    TRANSACTION_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT TRANSACTIONS_PK PRIMARY KEY, 
    AMOUNT DECIMAL(20,2) NOT NULL,
    VALUE_DATE TIMESTAMP, -- NOT NULL if not scheduled transaction, see constraint below
    TRANSACTION_DATE TIMESTAMP, -- NOT NULL if not scheduled transaction, see constraint below
    NOTE VARCHAR(1024),
    LABEL VARCHAR(1024),
    RECONCILIATION_STATE CHAR(1) NOT NULL DEFAULT 'U',
            -- 'U': unreconciled
            -- 'M': marked
            -- 'R': reconciled
    RECONCILIATION_DATE TIMESTAMP,
    ACCOUNT_ID BIGINT,
    IS_SCHEDTRANSACTION SMALLINT NOT NULL,

    CONSTRAINT TRANSACTION__ACCOUNT_FK FOREIGN KEY(ACCOUNT_ID) REFERENCES OBJ_ACCOUNTS(ACCOUNT_ID),
    CONSTRAINT RECONCILIATION_VALID_CONSTRAINT CHECK(RECONCILIATION_STATE IN ('U', 'M', 'R')),
    -- ACCOUNT_ID is null for scheduled transaction only
    CONSTRAINT ACCOUNT_ID_NOTNULL CHECK((IS_SCHEDTRANSACTION <> 0) OR (ACCOUNT_ID IS NOT NULL)), 
    -- VALUE_DATE is null for scheduled transaction only
    CONSTRAINT VALUE_DATE_NOTNULL CHECK((IS_SCHEDTRANSACTION <> 0) OR (VALUE_DATE IS NOT NULL)), 
    -- TRANSACTION_DATE is null for scheduled transaction only
    CONSTRAINT TRANSACTION_DATE_NOTNULL CHECK((IS_SCHEDTRANSACTION <> 0) OR (TRANSACTION_DATE IS NOT NULL)) 
);

CREATE TABLE OBJ_TRANSFERS (
    TRANSACTION_ID BIGINT NOT NULL,
    ACCOUNT_ID BIGINT NOT NULL,

    CONSTRAINT TRANSFER__TRANSACTION_FK FOREIGN KEY(TRANSACTION_ID) REFERENCES OBJ_TRANSACTIONS(TRANSACTION_ID),
    CONSTRAINT TRANSFER__ACCOUNT_FK FOREIGN KEY(ACCOUNT_ID) REFERENCES OBJ_ACCOUNTS(ACCOUNT_ID)
);

CREATE TABLE OBJ_PAYMENTS (
    TRANSACTION_ID BIGINT NOT NULL,
    PAYEE_ID BIGINT,
    PAYMENT_METHOD_ID BIGINT,

    CONSTRAINT PAYMENT__TRANSACTION_FK FOREIGN KEY(TRANSACTION_ID) REFERENCES OBJ_TRANSACTIONS(TRANSACTION_ID),
    CONSTRAINT PAYMENT__PAYEE_FK FOREIGN KEY(PAYEE_ID) REFERENCES OBJ_PAYEES(PAYEE_ID),
    CONSTRAINT PAYMENT__PAYMENT_METHOD_FK FOREIGN KEY(PAYMENT_METHOD_ID) REFERENCES OBJ_PAYMENT_METHODS(PAYMENT_METHOD_ID)
);

CREATE TABLE OBJ_SIMPLE_PAYMENTS (
    TRANSACTION_ID BIGINT NOT NULL,
    CATEGORY_ID BIGINT NOT NULL,

    CONSTRAINT SIMPLE_PAYMENT__TRANSACTION_FK FOREIGN KEY(TRANSACTION_ID) REFERENCES OBJ_TRANSACTIONS(TRANSACTION_ID),
    CONSTRAINT SIMPLE_PAYMENT__CATEGORY_FK FOREIGN KEY(CATEGORY_ID) REFERENCES OBJ_CATEGORIES(CATEGORY_ID)
);

CREATE TABLE OBJ_SPLIT_PAYMENTS (
    TRANSACTION_ID BIGINT NOT NULL,

    CONSTRAINT SPLIT_PAYMENT__TRANSACTION_FK FOREIGN KEY(TRANSACTION_ID) REFERENCES OBJ_TRANSACTIONS(TRANSACTION_ID)
);

CREATE TABLE OBJ_SPLIT_TRANSACTIONS (
    TRANSACTION_ID BIGINT NOT NULL,
    AMOUNT DECIMAL(20,2) NOT NULL,
    CATEGORY_ID BIGINT NOT NULL,
    NOTE VARCHAR(1024),

    CONSTRAINT SPLIT_TRANSACTION__TRANSACTION_FK FOREIGN KEY(TRANSACTION_ID) REFERENCES OBJ_TRANSACTIONS(TRANSACTION_ID),
    CONSTRAINT SPLIT_PAYMENT__CATEGORY_FK FOREIGN KEY(CATEGORY_ID) REFERENCES OBJ_CATEGORIES(CATEGORY_ID)
);

CREATE TABLE OBJ_SCHED_TRANSACTIONS (
    SCHED_TRANSACTION_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT SCHED_TRANSACTIONS_PK PRIMARY KEY, 
    ACCOUNT_ID BIGINT NOT NULL,
    AUTOMATIC SMALLINT NOT NULL,
    DESCRIPTION VARCHAR(1024), 
    FIXED_AMOUNT SMALLINT NOT NULL,
    NEXT_OCCURENCE TIMESTAMP NOT NULL,
    PERIOD INTEGER NOT NULL,
    PERIOD_UNIT CHAR(1) NOT NULL,
            -- 'D': day
            -- 'W': week
            -- 'M': month
            -- 'Y': year
    SCHED_DAYS INTEGER NOT NULL, -- number of days between the registration of the transaction and the scheduled date of the transaction
    TRANSACTION_ID BIGINT NOT NULL,

    CONSTRAINT PERIOD_UNIT_VALID_CONSTRAINT CHECK(PERIOD_UNIT IN ('D', 'W', 'M', 'Y')),
    CONSTRAINT SCHED_TRANSACTIONS__TRANSACTION_FK FOREIGN KEY(TRANSACTION_ID) REFERENCES OBJ_TRANSACTIONS(TRANSACTION_ID),
    CONSTRAINT SCHED_TRANSACTIONS__ACCOUNT_FK FOREIGN KEY(ACCOUNT_ID) REFERENCES OBJ_ACCOUNTS(ACCOUNT_ID)
);
