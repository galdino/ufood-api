set foreign_key_checks = 0;

delete from kitchen;
delete from state;
delete from city;
delete from restaurant;
delete from payment_method;
delete from upermission;
delete from restaurant_payment_method;
delete from restaurant_user;
delete from product;
delete from ugroup;
delete from user;
delete from ugroup_upermission;
delete from user_ugroup;
delete from uorder;
delete from uorder_item;
delete from product_image;

set foreign_key_checks = 1;

alter table kitchen auto_increment = 1;
alter table state auto_increment = 1;
alter table city auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table payment_method auto_increment = 1;
alter table upermission auto_increment = 1;
alter table restaurant_payment_method auto_increment = 1;
alter table restaurant_user auto_increment = 1;
alter table product auto_increment = 1;
alter table ugroup_upermission auto_increment = 1;
alter table user_ugroup auto_increment = 1;
alter table uorder auto_increment = 1;
alter table uorder_item auto_increment = 1;

insert into kitchen (id, name) values (1, 'Thai');
insert into kitchen (id, name) values (2, 'Indian');

insert into state (id, name) values (1, 'CA');
insert into state (id, name) values (2, 'FL');
insert into state (id, name) values (3, 'NY');

insert into city (name, state_id) values ('Los Angeles', 1);

insert into restaurant (name, delivery_fee, kitchen_id, address_city_id, address_street, update_date, register_date, active, open) values ('Thai Gourmet', 10, 1, 1, 'Backers Village', utc_timestamp, utc_timestamp, 1, 1);
insert into restaurant (name, delivery_fee, kitchen_id, update_date, register_date, active, open) values ('Thai Express', 9.50, 1, utc_timestamp, utc_timestamp, 1, 1);
insert into restaurant (name, delivery_fee, kitchen_id, update_date, register_date, active, open) values ('Tuk Tuk Indian Food', 15, 2, utc_timestamp, utc_timestamp, 1, 1);

insert into payment_method (id, description) values (1, 'Credit Card');
insert into payment_method (id, description) values (2, 'Debit Card');
insert into payment_method (id, description) values (3, 'Cash');

insert into ugroup (id, name) values (1, 'Manager');
insert into ugroup (id, name) values (2, 'Chef');
insert into ugroup (id, name) values (3, 'Delivery');

insert into user (id, name, email, password, register_date) values (1, 'Bob Brown', '9vKb5@example.com', '$2a$10$eACCYoNOHEqXve', utc_timestamp);
insert into user (id, name, email, password, register_date) values (2, 'Maria Green', 'ZLXO3@example.com', '$2a$10$eACCYoNOHEqXve', utc_timestamp);
insert into user (id, name, email, password, register_date) values (3, 'Alex Green', 'fgaldinoo@gmail.com', '$2a$10$eACCYoNOHEqXve', utc_timestamp);

insert into upermission (id, name, description) values (1, 'CHECK_KITCHEN', 'Permission to check the kitchens');
insert into upermission (id, name, description) values (2, 'EDIT_KITCHEN', 'Permission to edit kitchens');

insert into restaurant_payment_method (restaurant_id, payment_method_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);

insert into ugroup_upermission (ugroup_id, upermission_id) values (1, 1), (1, 2), (2, 1), (2, 2), (3, 1);

insert into user_ugroup (user_id, ugroup_id) values (1, 1), (1, 2), (2, 2);

insert into restaurant_user (restaurant_id, user_id) values (1, 1), (2, 2), (2, 3), (3, 3);

insert into product (name, description, price, active, restaurant_id) values ('Tom Yum Goong', 'This is a spicy shrimp soup that is famous all over the world.', 78.90, 0, 1);
insert into product (name, description, price, active, restaurant_id) values ('Pad Tha', 'This is a signature dish in Thailand and is supposed to be on the menu of every restaurant in Thailand', 54.90, 1, 1);
insert into product (name, description, price, active, restaurant_id) values ('Pad Tha', 'This is a signature dish in Thailand and is supposed to be on the menu of every restaurant in Thailand', 50, 1, 2);
insert into product (name, description, price, active, restaurant_id) values ('Masala Dosa', 'A traditional southern Indian dish made from a batter of soaked rice and lentils that is baked into a thin pancake and usually stuffed with potatoes, onions, and mustard seeds.', 5, 1, 3);
insert into product (name, description, price, active, restaurant_id) values ('Rogan Josh', 'An aromatic lamb curry that is believed to be of Persian origin, although today it is more closely associated with the Kashmir region of India.', 20, 1, 3);

insert into uorder (id, code, partial_amount, delivery_fee, total_amount, register_date, status, payment_method_id, user_id,
                    restaurant_id, address_zip_code, address_street, address_number, address_complement, address_district, address_city_id)
values (1, 'f9981ca4-5a5e-4da3-af04-933861df3e55', 298.90, 10, 308.90, date_sub(utc_timestamp, interval 2 day), 'DELIVERED', 1, 1, 1, '38400-000', '5 Avenue', '3000', 'Floor 2', 'Broken', 1);

insert into uorder_item (id, quantity, unit_price, total_price, uorder_id, product_id) values (1, 1, 149.45, 149.45, 1, 1);
insert into uorder_item (id, quantity, unit_price, total_price, uorder_id, product_id) values (2, 1, 149.45, 149.45, 1, 2);

insert into uorder (id, code, partial_amount, delivery_fee, total_amount, register_date, status, payment_method_id, user_id,
                    restaurant_id, address_zip_code, address_street, address_number, address_complement, address_district, address_city_id)
values (2, 'd178b637-a785-4768-a3cb-aa1ce5a8cdab', 298.90, 10, 308.90, utc_timestamp, 'CONFIRMED', 2, 2, 3, '85400-000', 'Barks Village', '5', 'Reception', 'Village', 1);

insert into uorder_item (id, quantity, unit_price, total_price, uorder_id, product_id) values (3, 1, 298.90, 298.90, 2, 4);

insert into uorder (id, code, partial_amount, delivery_fee, total_amount, register_date, status, payment_method_id, user_id,
                    restaurant_id, address_zip_code, address_street, address_number, address_complement, address_district, address_city_id)
values (3, 'd178b637-a785-4768-a3cb-bb2dq4r3afcr', 149.45, 10, 159.45, utc_timestamp, 'CREATED', 2, 3, 3, '85400-000', 'Barks Village', '5', 'Reception', 'Village', 1);

insert into uorder_item (id, quantity, unit_price, total_price, uorder_id, product_id) values (4, 1, 149.45, 149.45, 3, 1)