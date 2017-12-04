# --- !Ups

SET REFERENTIAL_INTEGRITY FALSE;

INSERT INTO student (id,name, stud_id) VALUES (0,'Ann',345533);
INSERT INTO student (id,name,stud_id) VALUES (1,'Tom',345534);
INSERT INTO student (id,name,stud_id) VALUES (2,'Helen',345535);
INSERT INTO student (id,name,stud_id) VALUES (3,'Helen',345536);

DROP SEQUENCE IF EXISTS student_seq;
CREATE SEQUENCE student_seq START WITH 10;

INSERT INTO course (id,name) VALUES (0,'e-Business Concepts and Technologies');
INSERT INTO course (id,name) VALUES (1,'Web Programming');
INSERT INTO course (id,name) VALUES (2,'Introduction to Programming in C++');
INSERT INTO course (id,name) VALUES (3,'Numerical Analysis');
INSERT INTO course (id,name) VALUES (4,'Parallel Computing');

DROP SEQUENCE IF EXISTS course_seq;
CREATE SEQUENCE course_seq START WITH 10;

INSERT INTO enrollment (id,student_id, course_id) VALUES (0,1,1);
INSERT INTO enrollment (id,student_id, course_id) VALUES (1,1,0);
INSERT INTO enrollment (id,student_id, course_id) VALUES (2,1,1);
INSERT INTO enrollment (id,student_id, course_id) VALUES (3,2,3);
INSERT INTO enrollment (id,student_id, course_id) VALUES (4,2,4);
INSERT INTO enrollment (id,student_id, course_id) VALUES (5,2,1);
INSERT INTO enrollment (id,student_id, course_id) VALUES (6,3,4);
INSERT INTO enrollment (id,student_id, course_id) VALUES (7,3,1);

SET REFERENTIAL_INTEGRITY TRUE;