drop table if exists applicants;
create table if not exists applicants (
    id int auto_increment primary key,
    name varchar(255) not null,
    email varchar(255) not null,
    phone varchar(255) not null,
    location varchar(255),
    current_job_role varchar(255) not null,
    old_job_role varchar(255) not null,
    eventid int,
    is_internal boolean,
    start_date date,
    cv_file_path varchar(255)
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