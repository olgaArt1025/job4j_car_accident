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

CREATE TABLE if not exists accident_rule (
    accident_id integer NOT NULL,
    rule_id integer  NOT NULL,
    PRIMARY KEY (accident_id , rule_id ),
    FOREIGN KEY (accident_id) REFERENCES accident,
    FOREIGN KEY (rule_id ) REFERENCES rule
);

CREATE TABLE if not exists authorities (
     id serial primary key,
     authority VARCHAR(50) NOT NULL unique
);

CREATE TABLE if not exists users (
     id serial primary key,
     username VARCHAR(50) NOT NULL unique,
     password VARCHAR(100) NOT NULL,
     enabled boolean default true,
     authority_id int not null references authorities(id)
);

insert into authorities (authority) values ('ROLE_USER');
insert into authorities (authority) values ('ROLE_ADMIN');

insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$wY1twJhMQjGVxv4y5dBC5ucCBlzkzT4FIGa4FNB/pS9GaXC2wm9/W',
        (select id from authorities where authority = 'ROLE_ADMIN'));
