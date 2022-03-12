CREATE DATABASE to_do_app;

CREATE TABLE priority (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(45),
    color VARCHAR(45)
);

CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(45),
    completed_count BIGINT,
    uncompleted_count BIGINT
);

CREATE TABLE task (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100),
    completed INT,
    date TIMESTAMP,
    priority_id BIGINT REFERENCES priority(id) ON DELETE SET NULL ON UPDATE RESTRICT,
    category_id BIGINT REFERENCES category(id) ON DELETE SET NULL ON UPDATE RESTRICT
);

CREATE TABLE stat (
    id BIGSERIAL PRIMARY KEY,
    completed_total BIGINT,
    uncompleted_total BIGINT
);


SELECT * FROM task;
SELECT * FROM stat;

INSERT INTO priority (title, color)
VALUES  ('Прочиать книгу', 'Желтый');

UPDATE priority
    SET title = 'Не срочный'
    WHERE id = 1;

INSERT INTO category (title, completed_count, uncompleted_count)
VALUES ('Чтение', 0, 0);

INSERT INTO task (title, completed, date, priority_id, category_id)
VALUES ('Прочиать книгу', 0, now(), 2, 2);

INSERT INTO task (title, completed, date, priority_id, category_id)
VALUES ('Прочиать книгу2', 0, now(), 2, 2);

INSERT INTO task (title, completed, date, priority_id, category_id)
VALUES ('Прочиать книгу3', 0, now(), 2, 2);


INSERT INTO task (title, completed, date, priority_id, category_id)
VALUES ('Прочиать книгу4', 0, now(), 2, 2);


INSERT INTO task (title, completed, date, priority_id, category_id)
VALUES ('Прочиать книгу6', 1, now(), 2, 2);


INSERT INTO task (title, completed, date, priority_id, category_id)
VALUES ('Прочиать книгу9', 0, now(), 2, 2);


INSERT INTO stat (completed_total, uncompleted_total)
VALUES (0, 0);



DELETE FROM task
WHERE id = 13;





