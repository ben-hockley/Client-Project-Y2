delete from applicants;
delete from events;
delete from users;

insert into applicants (name, email, phone, location, current_job_role, old_job_role, eventID, is_internal, start_date, cv_file_path, is_favourite)
values ('Test User','MoicoT@cardiff.ac.uk','1234567890','Wales','TestOldJob1, TestOldJob2','Test Location',1,TRUE,'2021-01-01','TestCVPath', false),
       ('Alice Johnson','alice.johnson@example.com','2345678901','Wales','Developer','Junior Developer',2,FALSE,'2022-02-01','AliceCVPath', false),
       ('Bob Smith','bob.smith@example.com','3456789012','Wales','Manager','Assistant Manager',3,TRUE,'2022-03-01','BobCVPath', false),
       ('Charlie Brown','charlie.brown@example.com','4567890123','Wales','Analyst','Junior Analyst',4,FALSE,'2022-04-01','CharlieCVPath', false),
       ('Diana Prince','diana.prince@example.com','5678901234','Wales','Designer','Junior Designer',1,TRUE,'2022-05-01','DianaCVPath', false),
       ('Eve Adams','eve.adams@example.com','6789012345','Wales','Engineer','Junior Engineer',2,FALSE,'2022-06-01','EveCVPath', true),
       ('Frank Wright','frank.wright@example.com','7890123456','Wales','Consultant','Junior Consultant',3,TRUE,'2022-07-01','FrankCVPath', false),
       ('Grace Lee','grace.lee@example.com','8901234567','Wales','Architect','Junior Architect',4,FALSE,'2022-08-01','GraceCVPath', true),
       ('Hank Green','hank.green@example.com','9012345678','Wales','Scientist','Junior Scientist',1,TRUE,'2022-09-01','HankCVPath', false),
       ('Ivy White','ivy.white@example.com','0123456789','Wales','Technician','Junior Technician',2,FALSE,'2022-10-01','IvyCVPath', false);

insert into events (event_name, event_location)
values ('Test Event 1','Test Location 1'),
       ('Test Event 2','Test Location 2'),
      ('Test Event 3','Test Location 3'),
      ('Test Event 4','Test Location 4');


insert into users (username, email, password, is_admin)
values ('adminTest','adminTest@test.com','adminTest',FALSE);

select * from users;
select * from events;
select * from applicants;