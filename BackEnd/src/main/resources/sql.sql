-- drop table if exists role;
-- drop table if exists user;
-- drop table if exists user_roles;
-- create table role (id bigint not null auto_increment, code varchar(255), name varchar(255), primary key (id)) engine=MyISAM;
-- create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=MyISAM;


-- INSERT INTO role (id, code, name) VALUES (4, 'TCS020', 'ADMIN');
-- INSERT INTO role (id, code, name) VALUES (5, 'TCS021', 'USER');