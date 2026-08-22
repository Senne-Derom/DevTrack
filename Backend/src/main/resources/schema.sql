drop table if exists study_entries;
drop table if exists courses;
drop table if exists users;

create table users (
    id serial primary key not null,
    username varchar(30) not null,
    email varchar(55) not null,
    password text not null
);

create table courses (
    id serial primary key not null,
    user_id bigint not null,
    name varchar(50) not null,
    study_points int not null,
    foreign key (user_id) references users(id)
);

create table study_entries (
    id serial primary key not null,
    course_id bigint not null,
    description varchar(255) not null,
    time_spent double precision not null,
    date timestamp not null,
    foreign key (course_id) references courses(id)
);