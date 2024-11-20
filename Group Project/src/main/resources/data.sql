delete from applicants;
delete from events;
delete from admin;

insert into applicants (name, email, phone, location, currentJobRole, oldJobRole, eventID, isInternal, startDate)
values ('Test User','test@test.com','1234567890','TestJob','TestOldJob1, TestOldJob2','Test Location',1,TRUE,'2021-01-01');

insert into events (eventName, eventLocation)
values ('Test Event 1','Test Location 1'),
       ('Test Event 2','Test Location 2'),
      ('Test Event 3','Test Location 3'),
      ('Test Event 4','Test Location 4');


insert into admin (adminUserName, adminEmail, adminPassword, isAdmin)
values ('adminTest','adminTest@test.com','adminTest',FALSE);

select * from admin;
select * from events;
select * from applicants;