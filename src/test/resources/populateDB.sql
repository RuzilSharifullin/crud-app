DELETE FROM meals;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('1999-08-15 10:00:00', 'scrambled eggs with smoked salmon', 500, 111),
       ('1999-08-15 15:00:00', 'pesto lasagna rolls', 1000, 111),
       ('1999-08-15 23:00:00', 'chicken and broccoli stir-fry', 500, 111),
       ('1999-09-9 07:00:00', 'huge burger', 700, 111),
       ('1999-09-9 14:00:00', 'roasted sweet potatoes with honey and cinnamon', 700, 111),
       ('1999-09-9 19:00:00', 'cheese danish', 300, 111),
       ('1999-11-12 08:00:00', 'spaghetti in alfredo sauce', 1000, 111),
       ('1999-11-12 12:00:00', 'seaweed salad', 400, 111),
       ('1999-11-12 20:00:00', 'lion king roll', 800, 111)
