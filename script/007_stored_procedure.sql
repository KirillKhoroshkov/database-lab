-- За заданное количество лет выводить списки периодических событий, которые стоит "закрыть".
-- Для принятия такого решения учитывать количество и динамику по следующим показателям:
-- 1. подписчики события
-- 2. комментарии к событию
create or replace function events.get_unprofitable_events(years_number integer)
    returns table(event_id integer) as $$
    declare
        start_timestamp timestamp;
        middle_timestamp timestamp;
        current_event record;
        subscriptions_amount_in_the_first_half integer;
        subscriptions_amount_in_the_second_half integer;
        comments_amount_in_the_first_half integer;
        comments_amount_in_the_second_half integer;
        blacklisted_amount integer;
        saved_amount integer;
    begin
        if (years_number <= 0) then
            raise exception 'Param "years_number" must be more than 0';
        end if;

        select current_timestamp - concat((years_number)::text, ' years'::text)::interval
            into start_timestamp;

        select start_timestamp + ((current_timestamp - start_timestamp) / 2)
            into middle_timestamp;

        create temporary table unprofitable_events (event_id integer) on commit drop;

        for current_event in
            select * from events.event
        loop
            select count(*) from events.event_date
                inner join events.saved_event_date
                    on event_date.id = saved_event_date.event_date_id
                where start_date > start_timestamp
                    and start_date < middle_timestamp
                    and event_date.event_id = current_event.id
                into subscriptions_amount_in_the_first_half;

            select count(*) from events.event_date
                inner join events.saved_event_date
                    on event_date.id = saved_event_date.event_date_id
                where start_date > middle_timestamp
                    and start_date < current_timestamp
                    and event_date.event_id = current_event.id
                into subscriptions_amount_in_the_second_half;

            select count(*) from events.event_comment
                where sent_at > start_timestamp
                    and sent_at < middle_timestamp
                    and event_comment.event_id = current_event.id
                into comments_amount_in_the_first_half;

            select count(*) from events.event_comment
                where sent_at > middle_timestamp
                    and sent_at < current_timestamp
                    and event_comment.event_id = current_event.id
                into comments_amount_in_the_second_half;

            select count(*) from events.event_blacklist
                where event_blacklist.event_id = current_event.id
                into blacklisted_amount;

            select count(*) from events.saved_event
                where saved_event.event_id = current_event.id
                into saved_amount;

            if (subscriptions_amount_in_the_first_half > subscriptions_amount_in_the_second_half
                    and comments_amount_in_the_first_half > comments_amount_in_the_second_half
                    and blacklisted_amount > saved_amount) then
                insert into unprofitable_events values (current_event.id);
            end if;

        end loop;

        return query select * from unprofitable_events;
    end
$$ language plpgsql;


select * from events.get_unprofitable_events(2);

select * from events.event_date where event_id = 48;

select * from events.event_blacklist where event_id = 48;

select * from events.account where

-- Дублирование всех периодических событий с прошлого года на заданный по заданной локации.
create or replace function events.duplicate_event_dates(needed_year integer, needed_location_id integer)
    returns void as $$
    declare
        previous_year integer;
        years_difference interval;
        current_tuple record;
        old_date_id integer;
        new_date_id integer;
    begin
        select extract(years from current_timestamp) - 1
            into previous_year;

        if ((needed_year - previous_year) <= 0) then
            raise exception 'Param "needed_year" must be greater than previous year';
        end if;

        select concat((needed_year - previous_year)::text, ' years'::text)::interval
            into years_difference;

        for current_tuple in
            select * from events.event_date
                where extract(years from start_date) = previous_year and
                    location_id = needed_location_id
        loop
            select current_tuple.id
                into old_date_id;

            insert into events.event_date(event_id, location_id, description, start_date, end_date) values
                (
                    current_tuple.event_id,
                    current_tuple.location_id,
                    current_tuple.description,
                    current_tuple.start_date + years_difference,
                    current_tuple.end_date + years_difference
                ) returning id into new_date_id;

            insert into events.event_cost(event_date_id, cost, description)
                select new_date_id, cost, description from events.event_cost
                    where event_date_id = old_date_id;
        end loop;
    end
$$ language plpgsql;


select location_id, count(*) from events.event_date
    group by location_id
    having count(*) < 100;

select * from events.event_date
    where location_id = 62
        and start_date < '2019-01-01 00:00:00'::timestamptz;

select events.duplicate_event_dates(2020, 62);

select * from events.event_date
    where location_id = 62
        and start_date > '2020-01-01 00:00:00'::timestamptz;
