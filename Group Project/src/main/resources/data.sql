delete from applicants;
delete from events;
delete from admin;

insert into applicants (name, email, phone, location, currentJobRole, oldJobRole, eventID, isInternal, startDate)
values ('Test User','test@test.com','1234567890','Wales' ,'TestOldJob1, TestOldJob2','Test Location',1,TRUE,'2021-01-01');

insert into events (eventName, eventLocation)
values ('Test Event','Test Location');

insert into admin (adminUserName, adminEmail, adminPassword, isAdmin)
values ('adminTest','adminTest@test.com','adminTest',FALSE);

select * from admin;
select * from events;
select * from applicants;