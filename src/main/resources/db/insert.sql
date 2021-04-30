SET FOREIGN_KEY_CHECKS = 0;

truncate table vehicle;

insert into vehicle(`id`, `vehicle_name`, `vehicle_number`, `modified_date`, `registered_date`, `image_url`)
value               (10, 'Bentley', 'AB8876576V', '26-04-2021', '26-04-2021', '***b.png***' ),
                    (11, 'Mercedes', 'MCD876576V', '26-04-2021', '26-04-2021', '***mc.png***' );









SET FOREIGN_KEY_CHECKS = 1;