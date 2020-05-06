TRUNCATE TABLE message;

INSERT INTO message(id, text, tag, user_id)
VALUES (1, 'first', 'some-tag', 1),
       (2, 'second', 'tag', 1),
       (3, 'third', 'my-tag', 1),
       (4, 'fourth', 'some-tag', 1);

alter sequence hibernate_sequence restart with 10;