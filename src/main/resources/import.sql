INSERT INTO users (id, created_at, email, first_name, last_name, password) VALUES (1, '2022-01-01 00:00:00', 'nikita@google.com', 'Nikita', 'Io', '12345');
INSERT INTO users (id, created_at, email, first_name, last_name, password) VALUES (2, '2022-01-01 00:00:00', 'ivan@google.com', 'Ivan', 'Petrov', '12345');
INSERT INTO users (id, created_at, email, first_name, last_name, password) VALUES (3, '2022-01-01 00:00:00', 'katerina@google.com.com', 'Katerina', 'Io', '12345');

INSERT INTO statuses (id, created_at, name) VALUES (1, '2022-01-01 00:00:00', 'новый')
INSERT INTO statuses (id, created_at, name) VALUES (2, '2022-01-01 00:00:00', 'в работе')
INSERT INTO statuses (id, created_at, name) VALUES (3, '2022-01-01 00:00:00', 'на тестировании')
INSERT INTO statuses (id, created_at, name) VALUES (4, '2022-01-01 00:00:00', 'завершен')

INSERT INTO tasks (id, created_at, description, name, author_id, executor_id, task_status_id) VALUES (1, '2022-01-01 00:00:00', 'описание1', 'задача1', 1, 1, 1);
INSERT INTO tasks (id, created_at, description, name, author_id, executor_id, task_status_id) VALUES (2, '2022-01-01 00:00:00', 'описание2', 'задача2', 1, 2, 2);
INSERT INTO tasks (id, created_at, description, name, author_id, executor_id, task_status_id) VALUES (3, '2022-01-01 00:00:00', 'описание3', 'задача3', 1, 3, 3);
INSERT INTO tasks (id, created_at, description, name, author_id, executor_id, task_status_id) VALUES (4, '2022-01-01 00:00:00', 'описание4', 'задача4', 3, 1, 4);