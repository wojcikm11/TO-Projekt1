create sequence hibernate_sequence start with 1 increment by 1
create table faculties (id bigint not null, address varchar(128) not null, contact_email varchar(64) not null, name varchar(256) not null, primary key (id))
