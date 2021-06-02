create extension if not exists "uuid-ossp";

create table if not exists account (
  id serial,
  username text unique not null,
  constraint account_pk_constraint primary key (id)
);

create table if not exists follower (
  follower_id integer not null,
  followed_id integer not null,
  constraint follower_pk_constraint primary key (follower_id, followed_id),
  constraint follower_id_fk_constraint
    foreign key (follower_id)
    references account (id)
    on delete cascade,
  constraint followed_id_fk_constraint
    foreign key (followed_id)
    references account (id)
    on delete cascade
);

create table if not exists product (
  id serial,
  account_id integer not null,
  name text not null,
  type text not null,
  brand text not null,
  color text not null,
  description text not null,
  constraint product_pk_constraint primary key (id),
  constraint account_id_fk_constraint
    foreign key (account_id)
    references account (id)
    on delete cascade
);

create table if not exists post (
  id serial,
  account_id integer not null,
  product_id integer not null,
  category integer not null,
  promotion boolean not null,
  price double precision not null,
  discount double precision not null,
  created_at timestamp not null,
  constraint post_pk_constraint primary key (id),
  constraint account_id_fk_constraint
    foreign key (account_id)
    references account (id)
    on delete cascade,
  constraint product_id_fk_constraint
    foreign key (product_id)
    references product (id)
    on delete restrict
);

create index if not exists account_username_index on account (username);
create index if not exists product_name_index on product (name);
create index if not exists post_product_id_index on post (product_id);
create index if not exists post_created_at_index on post (created_at);
