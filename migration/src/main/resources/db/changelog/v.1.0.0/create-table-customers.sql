create sequence if not exists customers_seq;

create table if not exists customers
(
    id bigint not null default nextval('customers_seq'),
    phone varchar(64) not null,
    email varchar(64) not null,
    coordinates varchar(300),
    constraint customers_pk primary key (id)
    );

comment on table customers is 'Клиенты';
comment on column customers.id is 'Идентификатор клиента';
comment on column customers.phone is 'Телефон клиента';
comment on column customers.email is 'Почтовый ящик клиента';
comment on column customers.coordinates is 'Координаты клиента';