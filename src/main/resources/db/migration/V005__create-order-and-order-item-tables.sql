create table uorder
(
    id                 bigint         not null auto_increment,
    canceled_date      datetime,
    confirmed_date     datetime,
    delivered_date     datetime,
    address_complement varchar(255),
    address_district   varchar(60),
    address_number     varchar(20),
    address_street     varchar(100),
    address_zip_code   varchar(20),
    delivery_fee       decimal(10, 2) not null,
    partial_amount     decimal(10, 2) not null,
    register_date      datetime       not null,
    status             integer,
    total_amount       decimal(10, 2) not null,
    address_city_id    bigint,
    payment_method_id  bigint         not null,
    restaurant_id      bigint         not null,
    user_id            bigint         not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

alter table uorder
    add constraint fk_uorder_city foreign key (address_city_id) references city (id);

alter table uorder
    add constraint fk_uorder_payment_method foreign key (payment_method_id) references payment_method (id);

alter table uorder
    add constraint fk_uorder_restaurant foreign key (restaurant_id) references restaurant (id);

alter table uorder
    add constraint fk_uorder_user foreign key (user_id) references user (id);

create table uorder_item
(
    id          bigint not null auto_increment,
    notes       varchar(255),
    quantity    integer,
    total_price decimal(10, 2),
    unit_price  decimal(10, 2),
    uorder_id    bigint not null,
    product_id  bigint not null,

    primary key (id),
    unique key uk_uorder_item_product (uorder_id, product_id)
) engine=InnoDB default charset=utf8;

alter table uorder_item
    add constraint fk_uorder_item_uorder foreign key (uorder_id) references uorder (id);

alter table uorder_item
    add constraint fk_uorder_item_product foreign key (product_id) references product (id);