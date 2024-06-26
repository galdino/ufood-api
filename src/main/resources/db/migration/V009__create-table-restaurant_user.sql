create table restaurant_user
(
    restaurant_id   bigint not null,
    user_id bigint not null,

    primary key (restaurant_id, user_id)
) engine=InnoDB default charset=utf8;

alter table restaurant_user
    add constraint fk_restaurant_user_restaurant foreign key (restaurant_id) references restaurant (id);

alter table restaurant_user
    add constraint fk_restaurant_user_user foreign key (user_id) references user (id);