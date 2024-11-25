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

drop table if exists admin;
create table if not exists admin(
    adminId int auto_increment primary key,
    adminUserName varchar(255) not null,
    adminEmail varchar(255) not null,
    adminPassword varchar(255) not null,
isAdmin boolean not null
) engine=InnoDB;
