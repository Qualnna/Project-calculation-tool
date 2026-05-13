use project_calculation;

Set foreign_key_checks = 0;
truncate table project;
truncate table sub_project;
truncate table task;
truncate table external_resource;
truncate table skill;
truncate table employee;
set foreign_key_checks = 1;

start transaction;


#First project 'My First Project'
insert into project(project_name, start_date, deadline) values ('My first project', '2026-6-10', '2026-10-1');
insert into sub_project(sub_name, sub_deadline, project_id) values ('Frontend', '2026-8-1', 1);
insert into sub_project(sub_name, sub_deadline, project_id) values ('Backend', '2026-8-1', 1);
insert into sub_project(sub_name, sub_deadline, project_id) values ('Middleware', '2026-8-1', 1);

# THree separata tasks associated with sub project 1
insert into task(task_name, workload, sub_id) values ('Develop a frontpage', 30, 1);
insert into task(task_name, workload, sub_id) values ('Develop an account page', 15, 1);
insert into task(task_name, workload, sub_id) values ('Develop a header', 2, 1);


#Available employees in the company
insert into employee (employee_name, hourly_rate) values ('John', 650);
insert into employee (employee_name, hourly_rate) values ('Sarah', 1650);
insert into employee (employee_name, hourly_rate) values ('Melissa', 1200);


# Available Skills
insert into skill (skill_name) values ('AngularJS'); #1
insert into skill (skill_name) values ('NodeJS'); #2
insert into skill (skill_name) values ('MySQL'); #3

# Available external ressources:
# 1 - Front end Consultant
# 2 - Management consultant
insert into external_resource (task_id, payment_type, price, resource_name, description, source) values(1, 'hourly',1000, 'Frontend Consultant', 'Adapt Consulting - frontend consultancy', 'Adapt Consulting A/S');
insert into external_resource (task_id, payment_type, price, resource_name, description, source) values(1, 'hourly',1000, 'Management Consultant', 'Change management ressource', 'Deloitte Consulting A/S');

# John has AngularJS as a listed skill
insert into employee_skill(employee_id, skill_id) select e.employee_id, s.skill_id
                                                  from (select employee_id from employee where employee_id = 1 )
                                                    as e
                                                        cross join (select skill_id from skill where skill_id = 1) as s;


# John is assigned task develop a front end where he will use 10 hours
insert into employee_task(employee_id, task_id, sub_deadline, time_spent, completion_date) select e.employee_id, t.task_id, s.sub_deadline, 10, '2026-7-1'
from (select employee_id from employee where employee_id = 1 )
         as e
         cross join (select task_id, sub_id from task where task_id = 1) as t
         cross join (select sub_deadline from sub_project where sub_id = 1) as s;


# Task 1: Develop a Frontend - Skill: AngularJS
insert into task_skill (task_id, skill_id) select t.task_id, s.skill_id
from (select task_id from task where task_id = 1) as t
cross join (select skill_id from skill where skill_id = 1 ) as s;


commit;