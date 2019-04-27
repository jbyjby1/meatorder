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
	meat varchar(48) not null,
	flavor varchar(36) not null,
	unit varchar(32) not null,
	create_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
	update_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
	price float(4,2) not null,
	unique (meat, shop),
	PRIMARY KEY(id)
);

update daily_order set create_time=date_add(create_time, interval 8 hour);
update daily_order set update_time=date_add(update_time, interval 8 hour);

update menu set create_time=date_add(create_time, interval 8 hour);
update menu set update_time=date_add(update_time, interval 8 hour);

create table if not exists event (
  id int auto_increment,
  event_name varchar(64),
  event_type varchar(64),
  create_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
  trigger_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
  extra TEXT,
  PRIMARY KEY(id)
)


create table if not exists daily_chicken (
  id int auto_increment,
  chicken_number int not null,
  chicken_type varchar(64),
  chicken_name varchar(64),
  chicken_id varchar(64),
  create_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id)
)

alter table menu drop index meat;

alter table daily_order add column status varchar(32);
update daily_order set status='creeated';
