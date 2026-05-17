INSERT INTO skill (skill_id, skill_name) VALUES
                                             (1, 'Java'),
                                             (2, 'SQL'),
                                             (3, 'Python');

INSERT INTO employee (employee_id, employee_name, hourly_rate) VALUES
                                                                   (1, 'Alice', 500.0),
                                                                   (2, 'Bob',   600.0);

INSERT INTO project (project_id, project_name, start_date, deadline) VALUES
    (1, 'Test Project', '2026-01-01', '2026-12-31');

INSERT INTO sub_project (sub_id, project_id, sub_name, sub_deadline) VALUES
    (1, 1, 'Sub A', '2026-06-30');

INSERT INTO task (task_id, sub_id, task_name, workload) VALUES
                                                            (10, 1, 'Design',  4),
                                                            (11, 1, 'Testing', 2);

INSERT INTO employee_skill (employee_id, skill_id) VALUES
                                                       (1, 1),   -- Alice → Java
                                                       (1, 2),   -- Alice → SQL
                                                       (2, 3);   -- Bob   → Python

INSERT INTO employee_task (employee_id, task_id, sub_deadline, time_spent, completion_date) VALUES
                                                                                                (1, 10, '2026-06-30', 5, '2026-06-15'),   -- Alice on Design
                                                                                                (1, 11, '2026-06-30', 3, '2026-06-20'),   -- Alice on Testing
                                                                                                (2, 10, '2026-06-30', 4, '2026-06-25');   -- Bob on Design

INSERT INTO task_skill (task_id, skill_id) VALUES
                                               (10, 1),   -- Design needs Java
                                               (10, 2),   -- Design needs SQL
                                               (11, 1);   -- Testing needs Java