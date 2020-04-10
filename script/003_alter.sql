
alter table events.message add column
    event_id integer default null references events.event(id);

alter table events.message add column
    event_date_id integer default null references events.event_date(id);

alter table events.link rename column
    link_type to link_type_id;

start transaction;
alter table events.clarification_type drop constraint clarification_type_output_order_check;
alter table events.clarification_type add constraint
    output_order_positive check ( output_order >= 0 );
commit transaction;

create table events.event_comment (
    id serial primary key,
    sender_id integer not null references events.account(id),
    event_id integer not null references events.event(id),
    message text not null,
    sent_at timestamptz not null default CURRENT_TIMESTAMP
);

create table events.account_report (
    id serial primary key,
    account_id integer not null references events.account(id),
    sender_id integer not null references events.account(id),
    message text not null,
    sent_at timestamptz not null default CURRENT_TIMESTAMP
);

create table events.event_report (
    id serial primary key,
    event_id integer not null references events.event(id),
    sender_id integer not null references events.account(id),
    message text not null,
    sent_at timestamptz not null default CURRENT_TIMESTAMP
);

alter table events.event add column
    is_blocked boolean not null default false;

create table events.event_date_report (
    id serial primary key,
    event_date_id integer not null references events.event_date(id),
    sender_id integer not null references events.account(id),
    message text not null,
    sent_at timestamptz not null default CURRENT_TIMESTAMP
);

alter table events.event_date add column
    is_blocked boolean not null default false;

create table events.event_blacklist (
    account_id integer references events.account(id),
    event_id integer not null references events.event(id),
    primary key (account_id, event_id)
);

start transaction;
create type events.event_type as enum ('event', 'schedule', 'service');
alter table events.event add column
    type events.event_type not null default 'event';
commit transaction;