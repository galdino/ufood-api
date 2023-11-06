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
    upermission_id bigint not null,

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
    ugroup_id bigint not null,

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
    add constraint fk_user_ugroup_user foreign key (user_id) references user (id);