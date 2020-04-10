
-- Вывести периодические события, которые всегда увеличивали сборы от продажи билетов
-- UPD: Евенты с постоянно растущими сохранениями дат
create or replace function events.has_event_positive_save_growth(needed_event_id integer)
    returns boolean as '
    declare
        current_row record;
        previous_number_of_saves bigint default 0;
    begin
        for current_row in
            select id, count(*) as number_of_saves from events.event_date
                inner join events.saved_event_date
                    on event_date.id = saved_event_date.event_date_id
                where event_date.event_id = needed_event_id
                group by id, start_date
                order by start_date
        loop
            if (current_row.number_of_saves < previous_number_of_saves) then
                return false;
            end if;
            select current_row.number_of_saves into previous_number_of_saves;
        end loop;

        return true;
    end
' language plpgsql;

create or replace function events.get_events_with_positive_save_growth()
    returns table(event_id integer) as '
    begin
        return query select id from events.event
            where events.has_event_positive_save_growth(id);
    end
' language plpgsql;

select * from events.get_events_with_positive_save_growth();

-- Предыдущее одним запросом
select id from events.event
    where id not in (
        select distinct event_id from (
            select first.event_id as event_id, first.id as first_id, second.id as second_id from events.event_date as first
                inner join events.event_date as second
                    on first.event_id = second.event_id and
                        first.id != second.id and
                        second.start_date > first.start_date
            ) as sub
            inner join (select event_date_id as fedi, count(event_date_id) as first_count from events.saved_event_date group by event_date_id) as ft
                on first_id = fedi
            inner join (select event_date_id as sedi, count(event_date_id) as second_count from events.saved_event_date group by event_date_id) as st
                on second_id = sedi
            where (second_count - first_count) < 0
    );


-- Вывести 5 локаций с максимальным средним количеством событий в год
create or replace function events.get_popular_locations()
    returns table(location_id integer, events_per_year double precision) as '
    declare
        min_date timestamptz;
        max_date timestamptz;
        years_number double precision;
    begin
        select max(end_date) from events.event_date into max_date;
        select min(start_date) from events.event_date into min_date;
        select (extract(epoch from (max_date - min_date)) / (3600 * 8760)) into years_number;

        return query select edl.location_id, (events_number / years_number) as events_per_year
            from (
                select count(*) as events_number, event_date.location_id
                    from events.event_date
                    group by event_date.location_id
            ) as edl
            order by events_per_year desc
            limit 5;
    end
' language plpgsql;

select * from events.get_popular_locations();