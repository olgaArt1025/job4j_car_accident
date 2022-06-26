create table if not exists accident_type (
     id serial primary key,
     name varchar(2000)
);

create table if not exists rule (
     id serial primary key,
     name varchar(2000)
    );

CREATE table if not exists accident (
    id serial primary key,
    name varchar(2000),
    text TEXT,
    address varchar(2000),
    accident_type_id int not null references accident_type(id)
);

CREATE TABLE accident_rule (
    accident_id integer NOT NULL,
    rule_id integer  NOT NULL,
    PRIMARY KEY (accident_id , rule_id ),
    FOREIGN KEY (accident_id) REFERENCES accident,
    FOREIGN KEY (rule_id ) REFERENCES rule
);


