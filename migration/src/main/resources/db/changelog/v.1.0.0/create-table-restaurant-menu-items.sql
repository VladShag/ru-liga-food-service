create sequence if not exists restaurant_menu_items_seq;

create table if not exists restaurant_menu_items
(
    id bigint not null default nextval('restaurant_menu_items_seq'),
    restaurant_id bigint not null,
    name varchar(64) not null,
    price numeric not null,
    image varchar(512),
    description varchar(512),
    constraint restaurant_menu_items_pk primary key (id),
    constraint restaurant_fk foreign key(restaurant_id) references restaurants(id)
    );

comment on table restaurant_menu_items is 'Меню ресторана';
comment on column restaurant_menu_items.id is 'Идентификатор позиции';
comment on column restaurant_menu_items.restaurant_id is 'Идентификатор ресторана';
comment on column restaurant_menu_items.name is 'Название';
comment on column restaurant_menu_items.price is 'Цена';
comment on column restaurant_menu_items.image is 'Изображение';
comment on column restaurant_menu_items.description is 'Описание';
