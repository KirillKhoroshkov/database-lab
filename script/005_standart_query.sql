-- Сделайте выборку всех данных из каждой таблицы
select * from events.account;
select * from events.account_report;
select * from events.account_verification;
select * from events.clarification;
select * from events.clarification_type;
select * from events.event;
select * from events.event_blacklist;
select * from events.event_comment;
select * from events.event_cost;
select * from events.event_date;
select * from events.event_date_report;
select * from events.event_report;
select * from events.event_tag;
select * from events.federal_subject;
select * from events.federal_subject_timezone;
select * from events.federal_subject_type;
select * from events.link;
select * from events.link_type;
select * from events.locality;
select * from events.locality_type;
select * from events.location;
select * from events.location_clarification;
select * from events.message;
select * from events.organizer_blacklist;
select * from events.organizer_subscription;
select * from events.password;
select * from events.password_token;
select * from events.place;
select * from events.place_type;
select * from events.saved_event;
select * from events.saved_event_date;
select * from events.saved_location;
select * from events.session;
select * from events.tag;
select * from events.tag_blacklist;

-- Сделайте выборку данных из одной таблицы при нескольких условиях,
-- с использованием логических операций, LIKE, BETWEEN, IN (не менее 3-х разных примеров)
select * from events.account where username like 'account%' and is_active = true;
select * from events.account where registered_at between '2018-1-1 12:0:0'::timestamptz and '2018-3-1 12:0:0'::timestamptz;
select * from events.account where email in ('account595@example.com', 'account424@example.com');

-- Создайте в запросе вычисляемое поле
select id, (current_timestamp - registered_at) as registered_during from events.account;

-- Сделайте выборку всех данных с сортировкой по нескольким полям
select * from events.locality
    order by locality_type_id, name;

-- Создайте запрос, вычисляющий несколько совокупных характеристик таблиц
select account_id, count(*) as report_number, max(sent_at) as last_report_sent_at from events.account_report
    group by account_id;

-- Сделайте выборку данных из связанных таблиц (не менее двух примеров)
select account.id as account_id, username, full_name from events.account
    left join events.account_verification
        on account.id = account_verification.account_id;

select event.id as event_id, event_date.id as event_date_id, event_cost.id as event_cost_id  from events.event
    inner join events.event_date
        on event.id = event_date.event_id
    inner join events.event_cost
        on event_date.id = event_cost.event_date_id;

-- Создайте запрос, рассчитывающий совокупную характеристику с использованием
-- группировки, наложите ограничение на результат группировки
select count(*), organizer_id from events.organizer_blacklist
    group by organizer_id
    having count(*) > 10
    order by count(*) desc;

-- Придумайте и реализуйте пример использования вложенного запроса
select * from events.tag
    where id not in (
        select tag_id from events.tag_blacklist
        where account_id = 333
    );

-- С помощью оператора INSERT добавьте в каждую таблицу по одной записи
insert into events.account(id, username, description, email, is_active, is_blocked, registered_at) values
    (3000, 'qwert', 'qwert', 'qwert@qwert.qwer', true, false, current_timestamp);
insert into events.account(id, username, description, email, is_active, is_blocked, registered_at) values
    (3001, 'qwertt', 'qwertt', 'qwertt@qwert.qwer', true, false, current_timestamp);

insert into events.account_report(id, account_id, sender_id, message, sent_at) values
    (3000, 3000, 3001, 'aaaaaa', current_timestamp);

insert into events.account_verification(account_id, full_name, verified_at) values
    (3000, 'full name', current_timestamp);

insert into events.clarification_type(id, name, abbreviation, output_order) values
    (30, 'kmdkm', 'sd', 3);

insert into events.event(id, name, description, age_limit, organizer_id, is_actual, payment_service_url,
                         created_at, is_blocked, type) values
    (3000, 'ALKJFSLKZ', 'lkzcvmlksdmv', 14, 3000, true, null, current_timestamp, false, 'event'::events.event_type);

insert into events.event_blacklist(account_id, event_id) values
    (3000, 3000);

insert into events.event_comment(id, sender_id, event_id, message, sent_at) values
    (51000, 3000, 3000, 'wasdfbcdsf', current_timestamp);

insert into events.federal_subject_type(id, name, abbreviation) values
    (20, 'ddwsfds', 'sdfsd');

insert into events.federal_subject(id, name, federal_subject_type_id) values
    (40, 'esfgdsf wsdfs', 20);

insert into events.federal_subject_timezone(id, federal_subject_id, utc_offset) values
    (50, 40, '1 hour'::interval);

insert into events.link_type(id, name) values
    (20, 'cvbgkdfjv');

insert into events.link(id, account_id, url, link_type_id) values
    (11000, 3000, 'http://xfb.dfgdg.fdgdg', 20);

insert into events.locality_type(id, name, abbreviation) values
    (20, 'scxvsdv', 'dvbsd');

insert into events.locality(id, name, locality_type_id, federal_subject_timezone_id) values
    (120, 'xcvsdsd', 20, 50);

insert into events.place_type(id, name, abbreviation) values
    (20, 'xvsdfvdfv', 'xcvscs');

insert into events.place(id, name, place_type_id, locality_id) values
    (320, 'scvsdvsdv', 20, 120);

insert into events.location(id, place_id, name, map_url) values
    (110, 320, 'xdfgdfbdfgdf', 'http://dfgdfg.dfgdf.dfgddgdfg');

insert into events.clarification(id, place_id, clarification_type_id, value) values
    (5000, 320, 30, 'sdfsd');

insert into events.location_clarification(location_id, clarification_id) values
    (110, 5000);

insert into events.event_date(id, event_id, location_id, description, is_actual, start_date,
                              end_date, created_at, is_blocked) values
    (21000, 3000, 110, 'sdfsddfdsaf', true, current_timestamp + '1 hour'::interval,
     current_timestamp + '3 hour'::interval, current_timestamp, false);

insert into events.event_cost(id, event_date_id, cost, description, is_actual, created_at) values
    (64000, 21000, 3000, 'asdfbnvsaf EGSDAGDBSDA', true, current_timestamp);

insert into events.event_date_report(id, event_date_id, sender_id, message, sent_at) values
    (33000, 21000, 3000, 'dfbsdgsdsf', current_timestamp);

insert into events.event_report(id, event_id, sender_id, message, sent_at) values
    (3200, 3000, 3000, 'sdasgarsdg', current_timestamp);

insert into events.tag(id, name) values
    (60, 'jZblx sdv');

insert into events.event_tag(event_id, tag_id) values
    (3000, 60);

insert into events.message(id, sender_id, recipient_id, message, sent_at, event_id, event_date_id) values
    (6100, 3000, 3001, 'asdfbnvsga', current_timestamp, null, null);

insert into events.organizer_blacklist(account_id, organizer_id) values
    (3001, 3000);

insert into events.organizer_subscription(account_id, organizer_id) values
    (3000, 3001);

insert into events.password(account_id, salt, hash) values
    (3000, 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'::uuid,
        events.digest(('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'::uuid)::text || 'aaaaa', 'sha256'));

insert into events.password_token(account_id, code, expires_in) values
    (3001, events.gen_random_uuid(), current_timestamp + '1 day'::interval);

insert into events.saved_event(account_id, event_id) values
    (3000, 3000);

insert into events.saved_event_date(account_id, event_date_id) values
    (3000, 21000);

insert into events.saved_location(account_id, location_id) values
    (3000, 110);

insert into events.session(access_token, refresh_token, expires_in ,account_id, created_at) values
    ('aaaaaaaa-aaaa-aaaa-aaaa-111111111123'::uuid, 'aaaaaaaa-aaaa-aaaa-aaaa-111111111111'::uuid,
        current_timestamp + '1 day'::interval, 3000, current_timestamp);

insert into events.tag_blacklist(account_id, tag_id) values
    (3000, 60);

-- С помощью оператора UPDATE измените значения нескольких полей у всех записей,
-- отвечающих заданному условию
update events.event_cost
    set description = 'Bla bla'
    where event_date_id = 101;

-- С помощью оператора DELETE удалите запись, имеющую максимальное (минимальное)
-- значение некоторой совокупной характеристики
delete from events.password_token where expires_in = (select min(expires_in) from events.password_token);

-- С помощью оператора DELETE удалите записи в главной таблице, на которые не
-- ссылается подчиненная таблица (используя вложенный запрос)
delete from events.link_type where id not in (select link_type_id from events.link);