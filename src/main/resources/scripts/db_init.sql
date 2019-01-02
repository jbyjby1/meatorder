CREATE DATABASE IF NOT EXISTS orders DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

use orders;

create table if not exists daily_order (
	id int auto_increment,
	username varchar(32),
	meat varchar(48),
	amount int,
	unit varchar(32),
	create_time timestamp DEFAULT CURRENT_TIMESTAMP,
	update_time timestamp DEFAULT CURRENT_TIMESTAMP,
	input_price float(4,2),
	price float(4,2),
	PRIMARY KEY(id)
);

create table if not exists menu (
	id int auto_increment,
	shop varchar(48),
	meat varchar(48) unique,
	flavor varchar(36),
	unit varchar(32),
	create_time timestamp DEFAULT CURRENT_TIMESTAMP,
	update_time timestamp DEFAULT CURRENT_TIMESTAMP,
	price float(4,2),
	PRIMARY KEY(id)
);
