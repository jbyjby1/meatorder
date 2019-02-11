CREATE DATABASE IF NOT EXISTS orders DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

use orders;

create table if not exists daily_order (
	id int auto_increment,
	username varchar(32) not null,
	shop varchar(64) not null,
	meat varchar(48) not null,
	amount int not null,
	unit varchar(32) not null,
	create_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
	update_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
	input_price float(4,2) not null,
	price float(4,2),
	PRIMARY KEY(id)
);

create table if not exists menu (
	id int auto_increment,
	shop varchar(48) not null,
	meat varchar(48) unique not null,
	flavor varchar(36) not null,
	unit varchar(32) not null,
	create_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
	update_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
	price float(4,2) not null,
	PRIMARY KEY(id)
);
