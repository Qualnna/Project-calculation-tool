SET foreign_key_checks = 0;
truncate table project;
truncate table sub_project;
truncate table task;
truncate table external_resource;
truncate table skill;
truncate table employee;
truncate table employee_task;
truncate table task_skill;
truncate table employee_skill;
set foreign_key_checks = 1;


-- =========================================================================
-- PROJECTS & SUB-PROJECTS
-- =========================================================================

insert into project(project_name, start_date, deadline)
values ('ERP Migration', '2026-06-10', '2026-10-01');                          -- project_id: 1

insert into project(project_name, start_date, deadline)
values ('Build a new website for Alpha Solutions', '2026-06-10', '2026-10-01'); -- project_id: 2

insert into sub_project(sub_name, sub_deadline, project_id) values ('Frontend',   '2026-08-01', 1); -- sub_id: 1
insert into sub_project(sub_name, sub_deadline, project_id) values ('Backend',    '2026-08-01', 1); -- sub_id: 2
insert into sub_project(sub_name, sub_deadline, project_id) values ('Middleware', '2026-09-01', 1); -- sub_id: 3

insert into sub_project(sub_name, sub_deadline, project_id) values ('Frontend',   '2026-08-01', 2); -- sub_id: 4
insert into sub_project(sub_name, sub_deadline, project_id) values ('Backend',    '2026-08-01', 2); -- sub_id: 5
insert into sub_project(sub_name, sub_deadline, project_id) values ('Middleware', '2026-09-01', 2); -- sub_id: 6


-- =========================================================================
-- TASKS
-- =========================================================================

-- Sub 1: Frontend / ERP Migration
insert into task(task_name, workload, sub_id) values ('Develop a frontpage',    30, 1); -- task_id: 1
insert into task(task_name, workload, sub_id) values ('Develop an account page',15, 1); -- task_id: 2
insert into task(task_name, workload, sub_id) values ('Develop a header',        2, 1); -- task_id: 3

-- Sub 2: Backend / ERP Migration
insert into task(task_name, workload, sub_id) values ('Develop a database map', 30, 2); -- task_id: 4
insert into task(task_name, workload, sub_id) values ('Develop security layer', 15, 2); -- task_id: 5
insert into task(task_name, workload, sub_id) values ('Develop query hooks',     2, 2); -- task_id: 6

-- Sub 3: Middleware / ERP Migration
insert into task(task_name, workload, sub_id) values ('Setup API Gateway',      20, 3); -- task_id: 7
insert into task(task_name, workload, sub_id) values ('Configure message queue',10, 3); -- task_id: 8

-- Sub 4: Frontend / Alpha Solutions
insert into task(task_name, workload, sub_id) values ('Design UI Layout',         30, 4); -- task_id: 9
insert into task(task_name, workload, sub_id) values ('Implement Contact Form',   15, 4); -- task_id: 10
insert into task(task_name, workload, sub_id) values ('Setup Footer Navigation',   2, 4); -- task_id: 11

-- Sub 5: Backend / Alpha Solutions
insert into task(task_name, workload, sub_id) values ('Setup REST API',          25, 5); -- task_id: 12
insert into task(task_name, workload, sub_id) values ('Configure database',      20, 5); -- task_id: 13

-- Sub 6: Middleware / Alpha Solutions
insert into task(task_name, workload, sub_id) values ('Integrate third-party APIs', 15, 6); -- task_id: 14


-- =========================================================================
-- EMPLOYEES
-- =========================================================================

insert into employee (employee_name, hourly_rate) values ('John',    650);  -- employee_id: 1
insert into employee (employee_name, hourly_rate) values ('Sarah',  1650);  -- employee_id: 2
insert into employee (employee_name, hourly_rate) values ('Melissa',1200);  -- employee_id: 3
insert into employee (employee_name, hourly_rate) values ('David',  1800);  -- employee_id: 4
insert into employee (employee_name, hourly_rate) values ('Namirah',2700);  -- employee_id: 5
insert into employee (employee_name, hourly_rate) values ('Isobel', 2500);  -- employee_id: 6


-- =========================================================================
-- SKILLS
-- =========================================================================

insert into skill (skill_name) values ('Backend');            -- skill_id: 1
insert into skill (skill_name) values ('Frontend');           -- skill_id: 2
insert into skill (skill_name) values ('DevOps');             -- skill_id: 3
insert into skill (skill_name) values ('Business Analysis');  -- skill_id: 4
insert into skill (skill_name) values ('Cybersecurity');      -- skill_id: 5
insert into skill (skill_name) values ('Project Management'); -- skill_id: 6


-- =========================================================================
-- EMPLOYEE SKILLS
-- =========================================================================

insert into employee_skill(employee_id, skill_id) values (1, 1); -- John:    Backend
insert into employee_skill(employee_id, skill_id) values (2, 2); -- Sarah:   Frontend
insert into employee_skill(employee_id, skill_id) values (3, 2); -- Melissa: Frontend
insert into employee_skill(employee_id, skill_id) values (3, 1); -- Melissa: Backend
insert into employee_skill(employee_id, skill_id) values (4, 3); -- David:   DevOps
insert into employee_skill(employee_id, skill_id) values (4, 1); -- David:   Backend
insert into employee_skill(employee_id, skill_id) values (5, 5); -- Namirah: Cybersecurity
insert into employee_skill(employee_id, skill_id) values (5, 6); -- Namirah: Project Management
insert into employee_skill(employee_id, skill_id) values (6, 3); -- Isobel:  DevOps
insert into employee_skill(employee_id, skill_id) values (6, 6); -- Isobel:  Project Management
insert into employee_skill(employee_id, skill_id) values (6, 5); -- Isobel:  Cybersecurity


-- =========================================================================
-- TASK SKILLS
-- =========================================================================

insert into task_skill(task_id, skill_id) values (1,  2); -- Develop a frontpage       -> Frontend
insert into task_skill(task_id, skill_id) values (2,  2); -- Develop an account page   -> Frontend
insert into task_skill(task_id, skill_id) values (3,  2); -- Develop a header          -> Frontend
insert into task_skill(task_id, skill_id) values (4,  1); -- Develop a database map    -> Backend
insert into task_skill(task_id, skill_id) values (5,  5); -- Develop security layer    -> Cybersecurity
insert into task_skill(task_id, skill_id) values (6,  1); -- Develop query hooks       -> Backend
insert into task_skill(task_id, skill_id) values (7,  3); -- Setup API Gateway         -> DevOps
insert into task_skill(task_id, skill_id) values (8,  3); -- Configure message queue   -> DevOps
insert into task_skill(task_id, skill_id) values (9,  2); -- Design UI Layout          -> Frontend
insert into task_skill(task_id, skill_id) values (10, 2); -- Implement Contact Form    -> Frontend
insert into task_skill(task_id, skill_id) values (11, 2); -- Setup Footer Navigation   -> Frontend
insert into task_skill(task_id, skill_id) values (12, 1); -- Setup REST API            -> Backend
insert into task_skill(task_id, skill_id) values (13, 1); -- Configure database        -> Backend
insert into task_skill(task_id, skill_id) values (14, 3); -- Integrate third-party APIs-> DevOps


-- =========================================================================
-- EXTERNAL RESOURCES
-- =========================================================================

insert into external_resource (task_id, payment_type, price, resource_name, description, source)
values (1, 'hourly', 1000, 'Frontend Consultant',  'Adapt Consulting - frontend consultancy', 'Adapt Consulting A/S');

insert into external_resource (task_id, payment_type, price, resource_name, description, source)
values (1, 'hourly', 1000, 'Management Consultant', 'Change management resource', 'Deloitte Consulting A/S');

insert into external_resource (task_id, payment_type, price, resource_name, description, source)
values (5, 'fixed', 50000, 'Security Auditor', 'External penetration testing', 'Secure Labs ApS');

-- =========================================================================
-- EMPLOYEE TASKS
-- =========================================================================

-- Sub 1 tasks (deadline 2026-08-01)
insert into employee_task(employee_id, task_id, sub_deadline, time_spent, completion_date)
values (2, 1, '2026-08-01', 28, '2026-07-20'); -- Sarah:   Develop a frontpage

insert into employee_task(employee_id, task_id, sub_deadline, time_spent, completion_date)
values (3, 2, '2026-08-01', 14, '2026-07-18'); -- Melissa: Develop an account page

insert into employee_task(employee_id, task_id, sub_deadline, time_spent, completion_date)
values (1, 3, '2026-08-01', 10, '2026-07-01'); -- John:    Develop a header (your original row)

-- Sub 2 tasks (deadline 2026-08-01)
insert into employee_task(employee_id, task_id, sub_deadline, time_spent, completion_date)
values (1, 4, '2026-08-01', 25, '2026-07-25'); -- John:    Develop a database map

insert into employee_task(employee_id, task_id, sub_deadline, time_spent, completion_date)
values (5, 5, '2026-08-01', 12, '2026-07-22'); -- Namirah: Develop security layer

insert into employee_task(employee_id, task_id, sub_deadline, time_spent, completion_date)
values (4, 6, '2026-08-01',  2, '2026-07-10'); -- David:   Develop query hooks

-- Sub 3 tasks (deadline 2026-09-01)
insert into employee_task(employee_id, task_id, sub_deadline, time_spent, completion_date)
values (4, 7, '2026-09-01', 18, '2026-08-15'); -- David:   Setup API Gateway

insert into employee_task(employee_id, task_id, sub_deadline, time_spent, completion_date)
values (4, 8, '2026-09-01',  8, '2026-08-20'); -- David:   Configure message queue

-- Sub 4 tasks (deadline 2026-08-01)
insert into employee_task(employee_id, task_id, sub_deadline, time_spent, completion_date)
values (4, 9, '2026-08-01',  5, '2026-07-15'); -- David:   Design UI Layout

insert into employee_task(employee_id, task_id, sub_deadline, time_spent, completion_date)
values (2, 10, '2026-08-01', 13, '2026-07-28'); -- Sarah:  Implement Contact Form

insert into employee_task(employee_id, task_id, sub_deadline, time_spent, completion_date)
values (3, 11, '2026-08-01',  2, '2026-07-05'); -- Melissa: Setup Footer Navigation

-- Sub 5 tasks (deadline 2026-08-01)
insert into employee_task(employee_id, task_id, sub_deadline, time_spent, completion_date)
values (3, 12, '2026-08-01', 20, '2026-07-24'); -- Melissa: Setup REST API

insert into employee_task(employee_id, task_id, sub_deadline, time_spent, completion_date)
values (1, 13, '2026-08-01', 18, '2026-07-30'); -- John:    Configure database

-- Sub 6 tasks (deadline 2026-09-01)
insert into employee_task(employee_id, task_id, sub_deadline, time_spent, completion_date)
values (4, 14, '2026-09-01', 12, '2026-08-25'); -- David:   Integrate third-party APIs