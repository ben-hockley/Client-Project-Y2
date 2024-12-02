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
