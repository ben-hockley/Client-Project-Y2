drop schema if exists group_project;
create schema group_project;
use group_project;
drop table if exists applicants;
create table if not exists applicants (
    id int auto_increment primary key,
    name varchar(255) not null,
    email varchar(255) not null,
    phone varchar(255) not null,
    location varchar(255),
    current_job_role varchar(255) not null,
    old_job_role varchar(255) not null,
    skills varchar(255) not null,
    eventid int,
    is_internal boolean,
    start_date date,
    cv_file_path varchar(255),
    is_favourite boolean
);

drop table if exists events;
create table if not exists events(
    eventid int auto_increment primary key,
    event_name varchar(255) not null,
    event_location varchar(255) not null
);

drop table if exists users;
create table if not exists users(
    id int auto_increment primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    email varchar(255) not null,
    is_admin boolean not null
) engine=InnoDB;

create table if not exists contact_history (
    id int auto_increment primary key,
    applicant_id int not null,
    contacted_by varchar(255) not null,
    contact_date datetime not null,
    contact_info varchar(255) not null
);
