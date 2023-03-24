create table if not exists `user` (
  `id` INT NOT NULL IDENTITY,
  `username` varchar(255) not null,
  `password` varchar(255) not null,
  `created_at` timestamp not null default current_timestamp,
  `updated_at` timestamp not null default current_timestamp on update current_timestamp,
  primary key (`id`)
);

insert into user (username, password) values ('tset',  '1234')