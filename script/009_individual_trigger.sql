
-- Обработка конфликтов по пересечению промежутков времени для event_date
-- одного и того же периодического события.
create function events.check_overlap_of_event_dates()
    returns trigger as $$
    begin
        if (
            exists (
                select * from events.event_date
                    where event_id = new.event_id
                        and (new.start_date, new.end_date) overlaps (start_date, end_date)
            )
        ) then
            raise exception 'Unable to set two dates at the same time';
        end if;

        return new;
    end;
$$ language plpgsql;


create trigger checking_overlap_of_event_dates_trigger
    before insert
    on events.event_date
    for each row
    execute procedure events.check_overlap_of_event_dates();


insert into events.event_date(event_id, location_id, description, start_date, end_date) values
    (1, 1, 'sdfsddfdsaf', '2020-10-26 03:22:41'::timestamp, '2020-10-26 05:22:41'::timestamp);

insert into events.event_date(event_id, location_id, description, start_date, end_date) values
    (1, 1, 'sdfsddfdsaf', '2020-10-26 03:22:41'::timestamp, '2020-10-26 05:22:41'::timestamp);

-- Блокировать аккаунт при добавлении в черный список более n раз.
-- UPD: Блокироваться будет в случае, если поступило более 10 жалоб
create function events.block_account_when_complaints()
    returns trigger as $$
    declare
        account_reports_number integer;
    begin
        select count(*) from events.account_report
            where account_id = new.account_id
            into account_reports_number;

        if (account_reports_number >= 10) then
            update events.account
                set is_blocked = true
                where account.id = new.account_id;
        end if;

        return new;
    end;
$$ language plpgsql;


create trigger blocking_account_when_complaints_trigger
    after insert
    on events.account_report
    for each row
    execute procedure events.block_account_when_complaints();

insert into events.account_report(account_id, sender_id, message) values
    (3000, 3001, 'aaaaaa'); -- execute 10 times

select id, is_blocked from events.account
    where id = 3000;

select count(*) from events.account_report
    where account_id = 3000;
