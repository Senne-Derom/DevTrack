drop table if exists study_entries;
drop table if exists courses;

create table courses (
    id serial primary key,
    name varchar(50),
    study_points int
);

create table study_entries (
    id serial primary key,
    course_id bigint,
    description varchar(255),
    time_spent double precision,
    date timestamp,
    foreign key (course_id) references courses(id)
);