create table payment_method
(
    id          bigint       not null auto_increment,
    description varchar(60) not null,
    primary key (id)
) engine=InnoDB  default charset=utf8;

create table product
(
    id            bigint         not null auto_increment,
    name          varchar(80)    not null,
    description   text           not null,
    price         decimal(10, 2) not null,
    active        bit            not null,
    restaurant_id bigint         not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurant
(
    id                 bigint         not null auto_increment,
    name               varchar(80)   not null,
    delivery_fee       decimal(10, 2) not null,
    update_date        datetime       not null,
    register_date      datetime       not null,

    address_zip_code   varchar(20),
    address_street     varchar(100),
    address_number     varchar(20),
    address_district   varchar(60),
    address_complement varchar(255),
    address_city_id    bigint,
    kitchen_id         bigint         not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurant_payment_method
(
    restaurant_id     bigint not null,
    payment_method_id bigint not null
) engine=InnoDB  default charset=utf8;

create table ugroup
(
    id   bigint       not null auto_increment,
    name varchar(60) not null,

    primary key (id)
) engine=InnoDB  default charset=utf8;

create table ugroup_upermission
(
    ugroup_id      bigint not null,
    upermission_id bigint not null

    primary key (ugroup_id, upermission_id)
) engine=InnoDB default charset=utf8;

create table upermission
(
    id          bigint       not null auto_increment,
    description varchar(60) not null,
    name        varchar(100) not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

create table user
(
    id            bigint       not null auto_increment,
    name          varchar(80)  not null,
    email         varchar(50) not null,
    password      varchar(50) not null,
    register_date datetime     not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

create table user_ugroup
(
    user_id   bigint not null,
    ugroup_id bigint not null

    primary key (user_id, ugroup_id)
) engine=InnoDB default charset=utf8;

alter table product
    add constraint fk_product_restaurant foreign key (restaurant_id) references restaurant (id);

alter table restaurant_payment_method
    add constraint fk_rest_pay_method_pay_method foreign key (payment_method_id) references payment_method (id);

alter table restaurant_payment_method
    add constraint fk_rest_pay_method_rest foreign key (restaurant_id) references restaurant (id);

alter table ugroup_upermission
    add constraint fk_ugro_upermi_upermission foreign key (upermission_id) references upermission (id);

alter table ugroup_upermission
    add constraint fk_ugro_upermi_ugroup foreign key (ugroup_id) references ugroup (id);

alter table user_ugroup
    add constraint fk_user_ugroup_ugroup foreign key (ugroup_id) references ugroup (id);

alter table user_ugroup
    add constraint fk_user_ugroup_user foreign key (user_id) references user (id);create table city (id bigint not null auto_increment, name varchar(255) not null, state_id bigint not null, primary key (id)) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB

create table order
(
    id                 bigint         not null auto_increment,
    canceled_date      datetime,
    confirmed_date     datetime,
    delivered_date     datetime,
    address_complement varchar(255),
    address_district   varchar(255),
    address_number     varchar(255),
    address_street     varchar(255),
    address_zip_code   varchar(255),
    delivery_fee       decimal(19, 2) not null,
    partial_amount     decimal(19, 2) not null,
    register_date      datetime       not null,
    status             integer,
    total_amount       decimal(19, 2) not null,
    address_city_id    bigint,
    payment_method_id  bigint         not null,
    restaurant_id      bigint         not null,
    user_id            bigint         not null,
    primary key (id)
) engine=InnoDB default charset=utf8;

create table order_item
(
    id          bigint not null auto_increment,
    notes       varchar(255),
    quantity    integer,
    total_price decimal(19, 2),
    unit_price  decimal(19, 2),
    order_id    bigint not null,
    product_id  bigint not null,
    primary key (id)
) engine=InnoDB default charset=utf8;

create table payment_method (id bigint not null auto_increment, description varchar(255) not null, primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, active bit not null, description varchar(255) not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_district varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), delivery_fee decimal(19,2) not null, name varchar(255) not null, register_date datetime not null, update_date datetime not null, address_city_id bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table ugroup (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table ugroup_upermission (ugroup_id bigint not null, upermission_id bigint not null) engine=InnoDB
create table upermission (id bigint not null auto_increment, description varchar(255) not null, name varchar(255) not null, primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, register_date datetime not null, primary key (id)) engine=InnoDB
create table user_ugroup (user_id bigint not null, ugroup_id bigint not null) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table order add constraint FKcf8v4u565syfcb0ybjrkawsdf foreign key (address_city_id) references city (id)
alter table order add constraint FKlaf31ka37nhamsdy9gvct70q0 foreign key (payment_method_id) references payment_method (id)
alter table order add constraint FK64rch5g46ue4a83ww6cq7r92w foreign key (restaurant_id) references restaurant (id)
alter table order add constraint FKt7abetueht6dd1gs9jyl3o4t7 foreign key (user_id) references user (id)
alter table order_item add constraint FKt6wv8m7eshksp5kp8w4b2d1dm foreign key (order_id) references order (id)
alter table order_item add constraint FK551losx9j75ss5d6bfsqvijna foreign key (product_id) references product (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FK8pcwgn41lfg43d8u82ewn3cn foreign key (address_city_id) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table ugroup_upermission add constraint FKj0kpvous86qxlk0nmur3c4i5x foreign key (upermission_id) references upermission (id)
alter table ugroup_upermission add constraint FK17mtejiaurwvrux95dw4fh923 foreign key (ugroup_id) references ugroup (id)
alter table user_ugroup add constraint FKir7c4hmaqtu2q5lkypp7gh7ta foreign key (ugroup_id) references ugroup (id)
alter table user_ugroup add constraint FKo3pjbd65f9r1ugjv62qqi7ofl foreign key (user_id) references user (id)
insert into kitchen (id, name) values (1, 'Thai')
insert into kitchen (id, name) values (2, 'Indian')
insert into state (id, name) values (1, 'CA')
insert into state (id, name) values (2, 'FL')
insert into state (id, name) values (3, 'NY')
insert into city (name, state_id) values ('Los Angeles', 1)
insert into restaurant (name, delivery_fee, kitchen_id, address_city_id, address_street, update_date, register_date) values ('Thai Gourmet', 10, 1, 1, 'Backers Village', utc_timestamp, utc_timestamp)
insert into restaurant (name, delivery_fee, kitchen_id, update_date, register_date) values ('Thai Express', 9.50, 1, utc_timestamp, utc_timestamp)
insert into restaurant (name, delivery_fee, kitchen_id, update_date, register_date) values ('Tuk Tuk Indian Food', 15, 2, utc_timestamp, utc_timestamp)
insert into payment_method (id, description) values (1, 'Credit Card')
insert into payment_method (id, description) values (2, 'Debit Card')
insert into payment_method (id, description) values (3, 'Cash')
insert into upermission (id, name, description) values (1, 'CHECK_KITCHEN', 'Permission to check the kitchens')
insert into restaurant_payment_method (restaurant_id, payment_method_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3)
insert into product (name, description, price, active, restaurant_id) values ('Tom Yum Goong', 'This is a spicy shrimp soup that is famous all over the world.', 78.90, 1, 1)
insert into product (name, description, price, active, restaurant_id) values ('Pad Tha', 'This is a signature dish in Thailand and is supposed to be on the menu of every restaurant in Thailand', 54.90, 1, 1)
insert into product (name, description, price, active, restaurant_id) values ('Pad Tha', 'This is a signature dish in Thailand and is supposed to be on the menu of every restaurant in Thailand', 50, 1, 2)
insert into product (name, description, price, active, restaurant_id) values ('Masala Dosa', 'A traditional southern Indian dish made from a batter of soaked rice and lentils that is baked into a thin pancake and usually stuffed with potatoes, onions, and mustard seeds.', 5, 1, 3)
insert into product (name, description, price, active, restaurant_id) values ('Rogan Josh', 'An aromatic lamb curry that is believed to be of Persian origin, although today it is more closely associated with the Kashmir region of India.', 20, 1, 3)
