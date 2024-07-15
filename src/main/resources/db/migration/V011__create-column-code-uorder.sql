alter table uorder add code varchar(36) not null after id;
update uorder set code = uuid();
alter table uorder add constraint uk_uorder_code unique (code);