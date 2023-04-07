create table if not exists `user` (
  `id` INT NOT NULL IDENTITY,
  `username` varchar(255) not null,
  `password` varchar(255) not null,
  `created_at` timestamp not null default current_timestamp,
  `updated_at` timestamp not null default current_timestamp on update current_timestamp,
  primary key (`id`)
);

insert into user (username, password) values ('tset',  '1234')

// mysql

CREATE TABLE `montshell`.`user` (
    `id` INT NOT NULL,
    `username` VARCHAR(50) NOT NULL,
    `password` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`));

