create table if not exists `user` (
  `id` INT NOT NULL IDENTITY,
  `username` varchar(255) not null,
  `password` varchar(255) not null,
  `created_at` timestamp not null default current_timestamp,
  `updated_at` timestamp not null default current_timestamp on update current_timestamp,
  primary key (`id`)
);

insert into user (username, password) values ('test',  '1234')

// mysql

CREATE TABLE `montshell`.`user` (
    `id` INT NOT NULL,
    `username` VARCHAR(50) NOT NULL,
    `password` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`));

CREATE TABLE `montshell`.`problem` (
   `id` INT NOT NULL AUTO_INCREMENT,
   `title` VARCHAR(45) NOT NULL,
   `description` VARCHAR(1000) NOT NULL,
   `input_description` VARCHAR(300) NOT NULL,
   `output_description` VARCHAR(300) NOT NULL,
   `difficulty` INT NOT NULL,
   `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`));

insert into problem (title, author, description, input_description, output_description, difficulty) values ('test_title', 'test_author','test_description', 'test_input_description', 'test_output_description', 1);


CREATE TABLE `montshell`.`test_case` (
    `id` INT NOT NULL,
    `test_input` TEXT NOT NULL,
    `test_output` TEXT NOT NULL,
    PRIMARY KEY (`id`));

insert into test_case (id, test_input, test_output) values (1, '1 2', '3');