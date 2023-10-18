insert into kitchen (id, name) values (1, 'Thai');
insert into kitchen (id, name) values (2, 'Indian');

insert into state (id, name) values (1, 'CA');
insert into state (id, name) values (2, 'FL');
insert into state (id, name) values (3, 'NY');

insert into city (name, state_id) values ('Los Angeles', 1);

insert into restaurant (name, delivery_fee, kitchen_id, address_city_id, address_street) values ('Thai Gourmet', 10, 1, 1, 'Backers Village');
insert into restaurant (name, delivery_fee, kitchen_id) values ('Thai Express', 9.50, 1);
insert into restaurant (name, delivery_fee, kitchen_id) values ('Tuk Tuk Indian Food', 15, 2);

insert into payment_method (id, description) values (1, 'Credit Card');
insert into payment_method (id, description) values (2, 'Debit Card');
insert into payment_method (id, description) values (3, 'Cash');

insert into restaurant_payment_method (restaurant_id, payment_method_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);