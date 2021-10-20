drop table if exists faculties;

create table faculties (
    id bigint not null primary key auto_increment,
    name varchar(256) not null unique,
    address varchar(128) not null,
    contact_email varchar(64) not null
);
