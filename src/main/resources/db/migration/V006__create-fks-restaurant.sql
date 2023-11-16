alter table restaurant
    add constraint fk_restaurant_city foreign key (address_city_id) references city (id);

alter table restaurant
    add constraint fk_restaurant_kitchen foreign key (kitchen_id) references kitchen (id);