start transaction;

--- Schemas

create schema events;

--- Functions

create function events.contains_redundant_spaces(raw_text text) returns boolean as $$
begin
    return ((position('  ' in raw_text)) > 0
        or (position(E'\n' in raw_text)) > 0
        or (position(E'\t' in raw_text)) > 0
        or raw_text like ' %'
        or raw_text like '% ');
end
$$ language plpgsql;

--- Extensions

create extension citext with schema events;

create extension pgcrypto with schema events;

--- Tables

create table events.account (
    id serial primary key,
    username events.citext not null unique,
    description text default null,
    email varchar(255) not null unique,
    is_active boolean not null default false,
    is_blocked boolean not null default false,
    registered_at timestamptz not null default CURRENT_TIMESTAMP,
    constraint username_max_length check ( length(username) < 64 ),
    constraint username_regex check ( username similar to '([A-Za-z]+(\_*[A-Za-z0-9]+)*){3,64}' ),
    constraint email_regex check ( email similar to '[a-z0-9._%-]+@[a-z0-9._%-]+\.[a-z]{2,4}')
);

create table events.password_token (
    account_id integer primary key references events.account(id),
    code uuid not null unique,
    expires_in timestamptz not null
);

create table events.password (
    account_id integer primary key references events.account(id),
    salt uuid not null,
    hash bytea not null -- sha256
);

create table events.session ( -- no more than 5
    access_token uuid primary key,
    refresh_token uuid not null,
    expires_in timestamptz not null,
    account_id integer not null references events.account(id),
    created_at timestamptz not null default CURRENT_TIMESTAMP
);

create table events.account_verification (
    account_id integer primary key references events.account(id),
    full_name events.citext not null unique,
    verified_at timestamptz not null default CURRENT_TIMESTAMP,
    constraint full_name_max_length check ( length(full_name) < 64 ),
    constraint full_name_redundant_spaces check ( not events.contains_redundant_spaces(full_name) )
);

create table events.link_type ( -- facebook, vk, website...
    id smallserial primary key,
    name events.citext not null unique,
    constraint name_max_length check ( length(name) < 32 ),
    constraint name_redundant_spaces check ( not events.contains_redundant_spaces(name) )
);

create table events.link ( -- links to social networks, website, etc.
    id serial primary key,
    account_id integer not null references events.account(id),
    url varchar(255) not null unique,
    link_type smallint not null references events.link_type(id),
    unique (account_id, url),
    unique (account_id, link_type)
);

create table events.event (
    id serial primary key,
    name events.citext not null,
    description text not null,
    age_limit smallint not null check (age_limit between 0 and 18),
    organizer_id integer not null references events.account(id),
    is_actual boolean not null default true,
    payment_service_url varchar(255) default null,
    created_at timestamptz not null default CURRENT_TIMESTAMP,
    constraint name_max_length check ( length(name) < 128 ),
    constraint name_redundant_spaces check ( not events.contains_redundant_spaces(name) )
);

create table events.tag (
    id serial primary key,
    name events.citext unique not null,
    constraint name_max_length check ( length(name) < 64 ),
    constraint name_redundant_spaces check ( not events.contains_redundant_spaces(name) )
);

create table events.event_tag (
    event_id integer references events.event(id),
    tag_id integer references events.tag(id),
    primary key (event_id, tag_id)
);

create table events.tag_blacklist (
    account_id integer references events.account(id),
    tag_id integer references events.tag(id),
    primary key (account_id, tag_id)
);

create table events.federal_subject_type (
    id smallserial primary key,
    name events.citext unique not null,
    abbreviation events.citext unique not null,
    constraint name_max_length check ( length(name) < 64 ),
    constraint abbreviation_max_length check ( length(abbreviation) < 64 ),
    constraint name_redundant_spaces check ( not events.contains_redundant_spaces(name) ),
    constraint abbreviation_redundant_spaces check ( not events.contains_redundant_spaces(abbreviation) )
);

create table events.federal_subject (
    id smallserial primary key,
    name events.citext unique not null,
    federal_subject_type_id smallint not null references events.federal_subject_type(id),
    constraint name_max_length check ( length(name) < 64 ),
    constraint name_redundant_spaces check ( not events.contains_redundant_spaces(name) )
);

create table events.federal_subject_timezone (
    id smallserial primary key,
    federal_subject_id smallint not null references events.federal_subject(id),
    utc_offset interval hour not null,
    constraint timezone_validation check (
        utc_offset between '-12 hours'::interval and '12 hours'::interval
    )
);

create table events.locality_type (
    id smallserial primary key,
    name events.citext unique not null,
    abbreviation events.citext unique not null,
    constraint name_max_length check ( length(name) < 64 ),
    constraint abbreviation_max_length check ( length(abbreviation) < 64 ),
    constraint name_redundant_spaces check ( not events.contains_redundant_spaces(name) ),
    constraint abbreviation_redundant_spaces check ( not events.contains_redundant_spaces(abbreviation) )
);

create table events.locality (
    id serial primary key,
    name events.citext not null,
    locality_type_id smallint not null references events.locality_type(id),
    federal_subject_timezone_id smallint not null references events.federal_subject_timezone(id),
    unique(name, federal_subject_timezone_id),
    constraint name_max_length check ( length(name) < 64 ),
    constraint name_redundant_spaces check ( not events.contains_redundant_spaces(name) )
);

create table events.place_type (
    id smallserial primary key,
    name events.citext unique not null,
    abbreviation events.citext unique not null,
    constraint name_max_length check ( length(name) < 64 ),
    constraint abbreviation_max_length check ( length(abbreviation) < 64 ),
    constraint name_redundant_spaces check ( not events.contains_redundant_spaces(name) ),
    constraint abbreviation_redundant_spaces check ( not events.contains_redundant_spaces(abbreviation) )
);

create table events.place (
    id serial primary key,
    name events.citext not null,
    place_type_id smallint not null references events.place_type(id),
    locality_id integer not null references events.locality(id),
    unique(name, locality_id, place_type_id),
    constraint name_max_length check ( length(name) < 64 ),
    constraint name_redundant_spaces check ( not events.contains_redundant_spaces(name) )
);

create table events.clarification_type (
    id smallserial primary key,
    name events.citext unique not null,
    abbreviation events.citext unique not null,
    output_order smallint not null check ( output_order > 0 ),
    constraint name_max_length check ( length(name) < 64 ),
    constraint abbreviation_max_length check ( length(abbreviation) < 64 ),
    constraint name_redundant_spaces check ( not events.contains_redundant_spaces(name) ),
    constraint abbreviation_redundant_spaces check ( not events.contains_redundant_spaces(abbreviation) )
);

create table events.clarification (
    id serial primary key,
    place_id integer not null references events.place(id),
    clarification_type_id smallint not null references events.clarification_type(id),
    value events.citext not null,
    unique (place_id, clarification_type_id, value),
    constraint value_max_length check ( length(clarification.value) < 32 ),
    constraint value_redundant_spaces check ( not events.contains_redundant_spaces(clarification.value) )
);

create table events.location (
    id serial primary key,
    place_id integer not null references events.place(id),
    name varchar(255) default null, -- the official name of the place such as the Hermitage and the Mariinsky Theater
    map_url varchar(255) default null,
    constraint name_redundant_spaces check ( name is null or not events.contains_redundant_spaces(name) )
);

create table events.location_clarification (
    location_id integer references events.location(id),
    clarification_id integer references events.clarification(id),
    primary key (location_id, clarification_id)
);

create table events.saved_location (
    account_id integer references events.account(id),
    location_id integer references events.location(id),
    primary key (account_id, location_id)
);

create table events.event_date (
    id serial primary key,
    event_id integer not null references events.event(id),
    location_id integer not null references events.location(id),
    description text default null,
    is_actual boolean not null default true,
    start_date timestamp not null, -- UTC
    end_date timestamp not null, -- UTC
    created_at timestamptz not null default CURRENT_TIMESTAMP,
    constraint max_duration check ( ( end_date - start_date ) < '22 days'::interval ),
    constraint date_validation check (
        start_date at time zone 'UTC' > CURRENT_TIMESTAMP
        and end_date at time zone 'UTC' > CURRENT_TIMESTAMP
        and end_date > start_date
    )
);

create table events.event_cost (
    id bigserial primary key,
    event_date_id integer not null references events.event_date(id),
    cost integer not null check ( cost >= 0 ),
    description varchar(255) not null,
    is_actual boolean not null default true,
    created_at timestamptz not null default CURRENT_TIMESTAMP
);

create table events.message (
    id serial primary key,
    sender_id integer not null references events.account(id),
    recipient_id integer not null references events.account(id),
    message text not null,
    sent_at timestamptz not null default CURRENT_TIMESTAMP
);

create table events.saved_event (
    account_id integer references events.account(id),
    event_id integer references events.event(id),
    primary key (account_id, event_id)
);

create table events.saved_event_date (
    account_id integer references events.account(id),
    event_date_id integer references events.event_date(id),
    primary key (account_id, event_date_id)
);

create table events.organizer_subscription (
    account_id integer references events.account(id),
    organizer_id integer references events.account(id),
    primary key (account_id, organizer_id)
);

create table events.organizer_blacklist (
    account_id integer references events.account(id),
    organizer_id integer references events.account(id),
    primary key (account_id, organizer_id)
);

commit transaction;