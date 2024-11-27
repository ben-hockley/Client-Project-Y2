drop table if exists applicants;
create table if not exists applicants (
    id int auto_increment primary key,
    name varchar(255) not null,
    email varchar(255) not null,
    phone varchar(255) not null,
    location varchar(255),
    currentJobRole varchar(255) not null,
    oldJobRole varchar(255) not null,
    eventID int,
    isInternal boolean,
    startDate date,
    cvFilePath varchar(255)
);

drop table if exists events;
create table if not exists events(
    eventId int auto_increment primary key,
    eventName varchar(255) not null,
    eventLocation varchar(255) not null
);
drop table if exists user;
drop table if exists users;
create table if not exists users(
    id int auto_increment primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    email varchar(255) not null,
    is_admin boolean not null
) engine=InnoDB;
