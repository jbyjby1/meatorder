BEGIN;
update menu set deleted='1';

-- 面食
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '豪华油泼面', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 22.00, 'NOODLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '豆角肉末面', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 22.00, 'NOODLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '岐山臊子扯面', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 20.00, 'NOODLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '香辣牛肉面', '单品（汤面）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 28.00, 'NOODLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '香菇鸡块面', '单品（汤面）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 19.00, 'NOODLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '至尊牛肉面', '单品（干拌）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 28.00, 'NOODLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '西红柿鸡蛋面', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 18.00, 'NOODLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '醉唐轩臊子面', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 20.00, 'NOODLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '油泼臊子扯面', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 20.00, 'NOODLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '经典油泼面', '单品（纯素）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 18.00, 'NOODLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '虾仁炒面', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 23.00, 'NOODLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '家常炒面', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 23.00, 'NOODLE', 0);

INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '汉中面皮', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 13.00, 'NOODLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '麻酱凉皮', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 14.00, 'NOODLE', 0);

-- 肉夹馍
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '腊汁肉夹馍', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 13.00, 'CHINESE_HAMBURGER', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '潼关饼', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 5.00, 'CHINESE_HAMBURGER', 0);

-- 炒饭
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '海鲜什锦炒饭', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 23.00, 'FRIED_RICE', 0);

-- 米粉
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '牛肉米粉', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 22.00, 'RICE_NOODLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '肉末米粉', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 19.00, 'RICE_NOODLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '酸辣粉', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 19.00, 'RICE_NOODLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '酸汤菌蔬粉', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 19.00, 'RICE_NOODLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '凉拌米粉', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 18.00, 'RICE_NOODLE', 0);

-- 盖浇饭
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '干煸豆角饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 18.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '菌烧日本豆腐饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 22.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '重庆辣子鸡饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 21.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '杭椒炒肉片饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 22.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '孜然肥牛饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 22.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '毛豆仁烧茄子饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 19.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '精品小炒肉饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 22.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '精品大盘鸡饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 25.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '回锅肉饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 20.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '干煸牛肉条饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 22.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '滑蛋虾仁饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 20.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '肉末茄子饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 18.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '香菇肉片饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 20.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '小炒肥牛饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 20.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '小炒藕丁饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 19.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '浇汁鸡柳饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 20.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '西红柿鸡蛋饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 18.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '老陕待客菜', '标准（带饼）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 27.00, 'PILAFF', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '香辣鱼饭', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 25.00, 'PILAFF', 0);

-- 小炒
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '干煸豆角', '标准（不含米饭）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 27.00, 'FRY', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '菌烧日本豆腐', '标准（不含米饭）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 29.00, 'FRY', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '重庆辣子鸡', '标准（不含米饭）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 29.00, 'FRY', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '毛豆仁烧茄子', '标准（不含米饭）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 27.00, 'FRY', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '精品小炒肉', '标准（不含米饭）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 30.00, 'FRY', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '一碗香', '标准（不含米饭）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 25.00, 'FRY', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '椒麻跳跳鸡', '标准（不含米饭）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 26.00, 'FRY', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '紫茄焖豆角', '标准（不含米饭）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 27.00, 'FRY', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '椒盐平菇', '标准（不含米饭）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 27.00, 'FRY', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '白灼菜心', '标准（不含米饭）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 23.00, 'FRY', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '培根土豆片', '标准（不含米饭）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 29.00, 'FRY', 0);

-- 锅类
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '干锅土豆片', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 20.00, 'GRIDDLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '干锅仔鸡', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 25.00, 'GRIDDLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '干锅金钱肚', '标准', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 27.00, 'GRIDDLE', 0);

-- 小菜
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '土豆丝', '小菜', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 8.00, 'PICKLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '凉拌豆腐丝', '小菜', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 8.00, 'PICKLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '洋葱木耳', '小菜', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 8.00, 'PICKLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '西芹腐竹', '小菜', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 8.00, 'PICKLE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '腊汁肉', '小菜', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 27.00, 'PICKLE', 0);

-- 粥汤类
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '疙瘩汤', '小小', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 6.00, 'PORRIDGE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '疙瘩汤', '小', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 12.00, 'PORRIDGE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '疙瘩汤', '大', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 20.00, 'PORRIDGE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '水盆羊肉', '标准（不带饼）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 29.00, 'PORRIDGE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '水盆羊肉', '标准（带饼）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 31.00, 'PORRIDGE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '羊杂汤', '标准（不带饼）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 22.00, 'PORRIDGE', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '羊杂汤', '标准（带饼）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 25.00, 'PORRIDGE', 0);


-- 米饭
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '米饭', '单品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 2.00, 'RICE', 0);

-- 快餐
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '快餐', '一荤一素', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 17.00, 'FAST_FOOD', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '快餐', '一荤两素', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 19.00, 'FAST_FOOD', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '快餐', '两荤一素', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 21.00, 'FAST_FOOD', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '快餐', '两荤两素', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 25.00, 'FAST_FOOD', 0);

-- 饮品
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '芒果汁（500ml）', '饮品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 9.00, 'DRINK', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '蜂蜜柚子茶（500ml）', '饮品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 9.00, 'DRINK', 0);
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '百香果汁（500ml）', '饮品', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 9.00, 'DRINK', 0);

COMMIT;