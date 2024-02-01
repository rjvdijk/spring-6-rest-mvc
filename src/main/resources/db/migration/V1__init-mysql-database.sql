-- Beer
drop table if exists beer;
create table beer (
    id varchar(36) not null,
     beer_name varchar(50) not null,
     beer_style tinyint not null,
     created_date datetime(6),
     price decimal(38,2),
     quantity_on_hand integer,
     upc varchar(255) not null,
     update_date datetime(6),
     version integer,
     primary key (id)
) engine=InnoDB;
-- Customer
drop table if exists customer;
create table customer (
    id varchar(36) not null,
    created_date datetime(6),
    name varchar(255),
    update_date datetime(6),
    version integer,
    primary key (id)
) engine=InnoDB;