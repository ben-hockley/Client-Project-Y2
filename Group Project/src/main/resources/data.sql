delete from applicants;
delete from events;
delete from users;
delete from contact_history;

insert into applicants (name, email, phone, location, current_job_role, old_job_role, skills, expected_salary, qualification, eventID, is_internal,
                        start_date, cv_file_path, is_favourite)
values ('Test User','MoicoT@cardiff.ac.uk','1234567890','Wales','TestOldJob1, TestOldJob2','Test Location',
        'Skill1, Skill2','30000','Msc',1,TRUE,'2024-10-01','TestCVPath', FALSE),
       ('Alice Johnson','alice.johnson@example.com','2345678901','Yorkshire_and_the_Humber','Developer','Junior Developer',
        'Java, Spring','50000','Bsc',2,FALSE,'2024-02-01','AliceCVPath', FALSE),
       ('Bob Smith','bob.smith@example.com','3456789012','Yorkshire_and_the_Humber','Manager','Assistant Manager',
        'Management, Leadership','60000','PhD',3,TRUE,'2024-03-01','BobCVPath', FALSE),
       ('Charlie Brown','charlie.brown@example.com','4567890123','London','Analyst','Junior Analyst',
        'Data Analysis, SQL','70000','Msc',4,FALSE,'2024-04-01','CharlieCVPath', FALSE),
       ('Diana Prince','diana.prince@example.com','5678901234','Wales','Designer','Junior Designer',
        'Design, Creativity','40000','Bsc',1,TRUE,'2024-05-01','DianaCVPath', FALSE),
       ('Eve Adams','eve.adams@example.com','6789012345','London','Engineer','Junior Engineer',
        'Engineering, Problem Solving','50000','PhD',2,FALSE,'2024-06-01','EveCVPath', FALSE),
       ('Frank Wright','frank.wright@example.com','7890123456','Wales','Consultant','Junior Consultant',
        'Consulting, Strategy','40000','Msc',3,TRUE,'2024-07-01','FrankCVPath', FALSE),
       ('Grace Lee','grace.lee@example.com','8901234567','Scotland','Architect','Junior Architect',
        'Architecture, Planning','30000','Msc',4,FALSE,'2024-08-01','GraceCVPath', FALSE),
       ('Hank Green','hank.green@example.com','9012345678','Wales','Scientist','Junior Scientist',
        'Research, Experimentation','60000','Bsc',1,TRUE,'2024-09-01','HankCVPath', FALSE),
       ('Ivy White','ivy.white@example.com','0123456789','Other','Technician','Junior Technician',
        'Technical Skills, Maintenance','50000','PhD',2,FALSE,'2024-10-01','IvyCVPath', FALSE);

insert into events (event_name, event_location)
values ('Test Event 1','Test Location 1'),
       ('Test Event 2','Test Location 2'),
       ('Test Event 3','Test Location 3'),
       ('Test Event 4','Test Location 4');

insert into users (username, email, password, is_admin)
values ('adminTest','adminTest@test.com','adminTest',FALSE);

insert into newsletter (email)
values ('coled4@cardiff.ac.uk'),
       ('cole.b.david@gmail.com');

select * from users;
select * from events;
select * from applicants;
select * from contact_history;