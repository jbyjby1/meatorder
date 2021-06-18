BEGIN;

-- 小炒
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '香椿炒鸡蛋', '标准（不含米饭）', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 27.00, 'FRY', 0);

-- 小菜
INSERT INTO `orders`.`menu`(`id`, `shop`, `meat`, `flavor`, `unit`, `create_time`, `update_time`, `price`, `meat_type`, `deleted`) VALUES (default, '醉唐轩（盈创动力店）', '香椿拌豆腐', '小菜', '份', '2020-12-14 06:02:36', '2020-12-14 06:02:36', 10.00, 'PICKLE', 0);

COMMIT;