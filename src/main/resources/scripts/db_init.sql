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

create table if not exists `event` (
  id int auto_increment,
  event_name varchar(64),
  event_type varchar(64),
  create_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
  trigger_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
  extra TEXT,
  PRIMARY KEY(id)
);


create table if not exists daily_chicken (
  id int auto_increment,
  chicken_number int not null,
  chicken_type varchar(64),
  chicken_name varchar(64),
  chicken_id varchar(64),
  create_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id)
);


alter table menu drop index meat;

alter table daily_order add column `status` varchar(32);
update daily_order set status='creeated';


alter table daily_order add column flavor VARCHAR(36);
update daily_order set flavor='标准';


alter table menu add column meat_type varchar(32);
alter table menu add column deleted boolean default false;

update `orders`.`menu` set deleted=1;


create table if not exists modifier (
  id int auto_increment,
  modifier_type varchar(64) not null,
  shop varchar(64),
  discount_type varchar(64),
	priority int,
  create_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
  start_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
  end_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
	display_name varchar(128),
	modifier_value float(4,2),
  modifier_parameters varchar(512),
  PRIMARY KEY(id)
);

INSERT INTO `orders`.`modifier`(`id`, `modifier_type`, `shop`, `discount_type`, `priority`, `create_time`, `start_time`, `end_time`, `display_name`, `modifier_value`, `modifier_parameters`) VALUES (1, 'DISCOUNT', '醉唐轩（盈创动力店）', 'FULL_REDUCTION', 10, '2019-10-28 23:24:08', '2019-10-29 00:00:01', '2029-10-28 23:24:08', '面食/米粉搭配肉夹馍减2元', -2.00, '{\"meatTypeConditions\":{\"first\":[\"RICE_NOODLE\",\"NOODLE\"],\"second\":[\"CHINESE_HAMBURGER\"]}}');
INSERT INTO `orders`.`modifier`(`id`, `modifier_type`, `shop`, `discount_type`, `priority`, `create_time`, `start_time`, `end_time`, `display_name`, `modifier_value`, `modifier_parameters`) VALUES (2, 'DISCOUNT', '醉唐轩（盈创动力店）', 'FULL_REDUCTION', 20, '2019-10-28 23:29:47', '2019-10-29 00:00:01', '2029-10-28 23:29:47', '面食/米粉搭配小菜和饮品立减5元', -5.00, '{\"meatTypeConditions\":{\"third\":[\"DRINK\"],\"first\":[\"RICE_NOODLE\",\"NOODLE\"],\"second\":[\"PICKLE\"]}}');
INSERT INTO `orders`.`modifier`(`id`, `modifier_type`, `shop`, `discount_type`, `priority`, `create_time`, `start_time`, `end_time`, `display_name`, `modifier_value`, `modifier_parameters`) VALUES (3, 'DISCOUNT', '醉唐轩（盈创动力店）', 'FULL_REDUCTION', 40, '2019-10-28 23:32:10', '2019-10-29 00:00:01', '2029-10-28 23:32:10', '单点饮品第二杯半价', -4.50, '{\"meatTypeConditions\":{\"first\":[\"DRINK\"],\"second\":[\"DRINK\"]}}');
INSERT INTO `orders`.`modifier`(`id`, `modifier_type`, `shop`, `discount_type`, `priority`, `create_time`, `start_time`, `end_time`, `display_name`, `modifier_value`, `modifier_parameters`) VALUES (4, 'DISCOUNT', '醉唐轩（盈创动力店）', 'FULL_REDUCTION', 30, '2019-10-28 23:41:08', '2019-10-29 00:00:01', '2029-10-28 23:41:08', '菜品搭配饮品立减3元', -3.00, '{\"meatTypeConditions\":{\"first\":[\"DRINK\"],\"second\":[\"PILAFF\",\"FRY\",\"NOODLE\",\"RICE_NOODLE\"]}}');

create table if not exists fast_food (
  id int auto_increment,
  fast_food_type varchar(64) not null,
  fast_food_name varchar(64) not null,
  create_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
  available int(1),
	PRIMARY KEY(id)
);

alter table daily_order add column order_remark varchar(128);

-- 20200907
update `orders`.`modifier` set modifier_parameters='{\"meatTypeConditions\":{\"first\":[\"DRINK\"],\"second\":[\"PILAFF\",\"FRY\",\"NOODLE\",\"RICE_NOODLE\",\"FRIED_RICE\",\"GRIDDLE\"]}}' where display_name='菜品搭配饮品立减3元';