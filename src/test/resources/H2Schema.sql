
drop table if exists task_skill;
drop table if exists employee_skill;
drop table if exists employee_task;
drop table if exists skill;
drop table if exists employee;
--drop table if exists task;
drop table if exists external_resource;
--drop table if exists sub_project;
--drop table if exists project;

create table if not exists project (
                         project_id int auto_increment primary key,
                         project_name varchar(255) not null,
                         start_date date,
                         deadline date,
                         constraint valid_date check (start_date < deadline)
);

create table if not exists sub_project (
                             sub_id int auto_increment primary key ,
                             project_id int not null,
                             sub_name varchar(255) not null,
                             sub_deadline date,
                             foreign key (project_id) references project (project_id) on delete cascade
);

create table if not exists task (
                      task_id int auto_increment primary key ,
                      sub_id int not null,
                      task_name varchar(255) not null,
                      workload int not null,
                      foreign key (sub_id) references sub_project (sub_id) on delete cascade
);

create table external_resource (
                                   resource_id int auto_increment primary key ,
                                   task_id int not null,
                                   payment_type varchar(63) not null,
                                   price float not null,
                                   resource_name varchar(255) not null,
                                   description varchar(1000),
                                   source varchar(1000),
                                   foreign key (task_id) references task (task_id) on delete cascade
);

create table skill (
                       skill_id int auto_increment primary key ,
                       skill_name varchar(255) not null
);

create table employee (
                          employee_id int auto_increment primary key ,
                          employee_name varchar(255) not null,
                          hourly_rate float not null
);

create table employee_task (
                               employee_id int not null,
                               task_id int not null,
                               sub_deadline date references sub_project (sub_deadline),
                               time_spent int not null,
                               completion_date date not null,
                               primary key (employee_id, task_id),
                               foreign key (employee_id) references employee (employee_id) on delete cascade,
                               foreign key (task_id) references  task (task_id) on delete cascade,
                               constraint deadline_met check (completion_date < sub_deadline)
);

create table task_skill (
                            task_id int not null,
                            skill_id int not null,
                            primary key (task_id, skill_id),
                            foreign key (task_id) references task (task_id) on delete cascade,
                            foreign key (skill_id) references skill (skill_id) on delete cascade
);

create table employee_skill (
                                employee_id int not null,
                                skill_id int not null,
                                primary key (employee_id, skill_id),
                                foreign key (employee_id) references employee (employee_id) on delete cascade,
                                foreign key (skill_id) references skill (skill_id) on delete cascade
);
