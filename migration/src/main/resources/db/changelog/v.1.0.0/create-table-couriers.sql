create sequence if not exists couriers_seq;

create table if not exists couriers
(
    id bigint not null default nextval('couriers_seq'),
    phone varchar(64) not null,
    status varchar(64) not null,
    coordinates varchar(150),
    constraint couriers_pk primary key (id)
    );

comment on table couriers is 'Курьеры';
comment on column couriers.id is 'Идентификатор курьера';
comment on column couriers.phone is 'Телефон курьера';
comment on column couriers.status is 'Статус курьера';
comment on column couriers.coordinates is 'Координаты курьера';
