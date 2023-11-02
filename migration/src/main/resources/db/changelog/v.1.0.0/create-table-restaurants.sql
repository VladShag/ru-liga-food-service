create sequence if not exists restaurants_seq;

create table if not exists restaurants
(
    id bigint not null default nextval('restaurants_seq'),
    name varchar(64) not null,
    coordinates varchar(300) not null,
    status varchar(64) not null,
    constraint restaurants_pk primary key (id)
    );

comment on table restaurants is 'Рестораны';
comment on column restaurants.id is 'Идентификатор ресторана';
comment on column restaurants.name is 'Название ресторана';
comment on column restaurants.coordinates is 'Координаты ресторана';
comment on column restaurants.status is 'Статус ресторана';