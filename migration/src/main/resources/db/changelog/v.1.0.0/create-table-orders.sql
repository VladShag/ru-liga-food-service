create sequence if not exists orders_seq;

create table if not exists orders
(
    id bigint not null default nextval('orders_seq'),
    customer_id bigint not null,
    restaurant_id bigint not null,
    status varchar(64) not null,
    courier_id int,
    timestamp date not null,
    constraint order_pk primary key (id),
    constraint customer_fk foreign key(customer_id) references customers(id),
    constraint restaurant_fk foreign key(restaurant_id) references restaurants(id),
    constraint courier_fk foreign key(courier_id) references couriers(id)
);

comment on table orders is 'Заказы';
comment on column orders.id is 'Идентификатор заказа';
comment on column orders.customer_id is 'Идентификатор покупателя';
comment on column orders. restaurant_id is 'Идентификатор ресторана';
comment on column orders.status is 'Статус заказа';
comment on column orders.courier_id is 'Идентификатор курьера';
comment on column orders.timestamp is 'Дата';
