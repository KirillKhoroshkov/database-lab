
-- триггер для автоматического заполнения ключевого поля
create or replace function events.generate_message_id()
    returns trigger as $$
    begin
        new.id = (select max(id)  + 1 from events.message);

        return new;
    end;
$$ language plpgsql;


create trigger auto_generating_message_id_trigger
    before insert
    on events.message
    for each row
    execute procedure events.generate_message_id();


insert into events.message(id, sender_id, recipient_id, message) values
    (6150, 3000, 3001, 'Bla-bla-bla');

select * from events.message where id = 6150;


insert into events.message(sender_id, recipient_id, message) values
    (3000, 3001, 'Bla-bla-bla');

select * from events.message where id = (select max(id) from events.message);


-- триггер для контроля целостности данных в подчиненной таблице при
-- удалении/изменении записей в главной таблице
create function events.tag_deleting()
    returns trigger as $$
    begin
        delete from events.tag_blacklist where tag_id = old.id;
        delete from events.event_tag where tag_id = old.id;
        return old;
    end;
$$ language plpgsql;


create trigger tag_deleting_trigger
    before delete
    on events.tag
    for each row
    execute procedure events.tag_deleting();


delete from events.tag where id = 1;

select * from events.tag
    where id = 1;

select * from events.tag_blacklist
    where tag_id = 1;

select * from events.event_tag
    where tag_id = 1;