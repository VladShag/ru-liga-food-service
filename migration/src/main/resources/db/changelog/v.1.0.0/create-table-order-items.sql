create sequence if not exists order_items_seq;

create table if not exists order_items
(
    id bigint not null default nextval('order_items_seq'),
    order_id uuid not null,
    restaurant_menu_item bigint not null,
    price numeric not null,
    quantity int not null,
    constraint order_items_pk primary key (id),
    constraint order_fk foreign key(order_id) references orders(id)
);

comment on table order_items is 'Позиции в заказе';
comment on column order_items.id is 'Идентификатор позиции';
comment on column order_items.order_id is 'Идентификатор заказа';
comment on column order_items. restaurant_menu_item is 'Идентификатор позиции в меню';
comment on column order_items.price is 'Цена';
comment on column order_items.price is 'Количество';

