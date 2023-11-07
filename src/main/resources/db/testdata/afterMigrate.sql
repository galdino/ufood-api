set foreign_key_checks = 0;

delete from kitchen;
delete from state;
delete from city;
delete from restaurant;
delete from payment_method;
delete from upermission;
delete from restaurant_payment_method;
delete from product;

set foreign_key_checks = 1;

alter table kitchen auto_increment = 1;
alter table state auto_increment = 1;
alter table city auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table payment_method auto_increment = 1;
alter table upermission auto_increment = 1;
alter table restaurant_payment_method auto_increment = 1;
alter table product auto_increment = 1;

insert into kitchen (id, name) values (1, 'Thai');
insert into kitchen (id, name) values (2, 'Indian');

insert into state (id, name) values (1, 'CA');
insert into state (id, name) values (2, 'FL');
insert into state (id, name) values (3, 'NY');

insert into city (name, state_id) values ('Los Angeles', 1);

insert into restaurant (name, delivery_fee, kitchen_id, address_city_id, address_street, update_date, register_date) values ('Thai Gourmet', 10, 1, 1, 'Backers Village', utc_timestamp, utc_timestamp);
insert into restaurant (name, delivery_fee, kitchen_id, update_date, register_date) values ('Thai Express', 9.50, 1, utc_timestamp, utc_timestamp);
insert into restaurant (name, delivery_fee, kitchen_id, update_date, register_date) values ('Tuk Tuk Indian Food', 15, 2, utc_timestamp, utc_timestamp);

insert into payment_method (id, description) values (1, 'Credit Card');
insert into payment_method (id, description) values (2, 'Debit Card');
insert into payment_method (id, description) values (3, 'Cash');

insert into upermission (id, name, description) values (1, 'CHECK_KITCHEN', 'Permission to check the kitchens');

insert into restaurant_payment_method (restaurant_id, payment_method_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);

insert into product (name, description, price, active, restaurant_id) values ('Tom Yum Goong', 'This is a spicy shrimp soup that is famous all over the world.', 78.90, 1, 1);
insert into product (name, description, price, active, restaurant_id) values ('Pad Tha', 'This is a signature dish in Thailand and is supposed to be on the menu of every restaurant in Thailand', 54.90, 1, 1);
insert into product (name, description, price, active, restaurant_id) values ('Pad Tha', 'This is a signature dish in Thailand and is supposed to be on the menu of every restaurant in Thailand', 50, 1, 2);
insert into product (name, description, price, active, restaurant_id) values ('Masala Dosa', 'A traditional southern Indian dish made from a batter of soaked rice and lentils that is baked into a thin pancake and usually stuffed with potatoes, onions, and mustard seeds.', 5, 1, 3);
insert into product (name, description, price, active, restaurant_id) values ('Rogan Josh', 'An aromatic lamb curry that is believed to be of Persian origin, although today it is more closely associated with the Kashmir region of India.', 20, 1, 3);