start transaction;

insert into events.account(id, username, description, email) values
    (1, 'Alice', 'First test user', 'alice@example.com'),
    (2, 'Bob', 'Second test user', 'bob@example.com'),
    (3, 'Clara', 'Third test user', 'clara@example.com'),
    (4, 'Denis', 'Fourth test user', 'denis@example.com'),
    (5, 'Elon', 'Fifth test user', 'elon_musk@example.com');

insert into events.password_token(account_id, code, expires_in) values
    (1, events.gen_random_uuid(), current_timestamp + '1 day'::interval),
    (2, events.gen_random_uuid(), current_timestamp + '1 day'::interval),
    (3, events.gen_random_uuid(), current_timestamp + '1 day'::interval),
    (4, events.gen_random_uuid(), current_timestamp + '1 day'::interval),
    (5, events.gen_random_uuid(), current_timestamp + '1 day'::interval);

delete from events.password_token where account_id = 1;
delete from events.password_token where account_id = 2;
delete from events.password_token where account_id = 3;
delete from events.password_token where account_id = 4;
delete from events.password_token where account_id = 5;

insert into events.password(account_id, salt, hash) values
    (1, 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'::uuid,
        events.digest(('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'::uuid)::text || 'aaaaa', 'sha256')),
    (2, 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'::uuid,
        events.digest(('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'::uuid)::text || 'bbbbb', 'sha256')),
    (3, 'cccccccc-cccc-cccc-cccc-cccccccccccc'::uuid,
        events.digest(('cccccccc-cccc-cccc-cccc-cccccccccccc'::uuid)::text || 'ccccc', 'sha256')),
    (4, 'dddddddd-dddd-dddd-dddd-dddddddddddd'::uuid,
        events.digest(('dddddddd-dddd-dddd-dddd-dddddddddddd'::uuid)::text || 'ddddd', 'sha256')),
    (5, 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'::uuid,
        events.digest(('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'::uuid)::text || 'eeeee', 'sha256'));

insert into events.session(access_token, refresh_token, expires_in, account_id) values
    ('aaaaaaaa-aaaa-aaaa-aaaa-111111111111'::uuid, 'aaaaaaaa-aaaa-aaaa-aaaa-111111111111'::uuid,
        current_timestamp + '1 day'::interval, 1),
    ('aaaaaaaa-aaaa-aaaa-aaaa-222222222222'::uuid, 'aaaaaaaa-aaaa-aaaa-aaaa-222222222222'::uuid,
        current_timestamp + '1 day'::interval, 1),
    ('bbbbbbbb-bbbb-bbbb-bbbb-111111111111'::uuid, 'bbbbbbbb-bbbb-bbbb-bbbb-111111111111'::uuid,
        current_timestamp + '1 day'::interval, 2),
    ('bbbbbbbb-bbbb-bbbb-bbbb-222222222222'::uuid, 'bbbbbbbb-bbbb-bbbb-bbbb-222222222222'::uuid,
        current_timestamp + '1 day'::interval, 2),
    ('cccccccc-cccc-cccc-cccc-111111111111'::uuid, 'cccccccc-cccc-cccc-cccc-111111111111'::uuid,
        current_timestamp + '1 day'::interval, 3),
    ('cccccccc-cccc-cccc-cccc-222222222222'::uuid, 'cccccccc-cccc-cccc-cccc-222222222222'::uuid,
        current_timestamp + '1 day'::interval, 3),
    ('dddddddd-dddd-dddd-dddd-111111111111'::uuid, 'dddddddd-dddd-dddd-dddd-111111111111'::uuid,
        current_timestamp + '1 day'::interval, 4),
    ('dddddddd-dddd-dddd-dddd-222222222222'::uuid, 'dddddddd-dddd-dddd-dddd-222222222222'::uuid,
        current_timestamp + '1 day'::interval, 4),
    ('eeeeeeee-eeee-eeee-eeee-111111111111'::uuid, 'eeeeeeee-eeee-eeee-eeee-111111111111'::uuid,
        current_timestamp + '1 day'::interval, 5),
    ('eeeeeeee-eeee-eeee-eeee-222222222222'::uuid, 'eeeeeeee-eeee-eeee-eeee-222222222222'::uuid,
        current_timestamp + '1 day'::interval, 5);

insert into events.account_verification(account_id, full_name) values
    (1, 'First verified user'),
    (2, 'Second verified user'),
    (3, 'Third verified user'),
    (4, 'Fourth verified user'),
    (5, 'Fifth verified user');

insert into events.link_type(id, name) values
    (1, 'vk'),
    (2, 'facebook'),
    (3, 'twitter'),
    (4, 'instagram'),
    (5, 'website');

insert into events.link(account_id, url, link_type) values
    (1, 'https://www.instagram.com/Alice/', 4),
    (2, 'https://www.instagram.com/Bob/', 4),
    (3, 'https://www.instagram.com/Clara/', 4),
    (4, 'https://www.instagram.com/Denis/', 4),
    (5, 'https://www.instagram.com/Elon/', 4),
    (1, 'https://vk.com/Alice', 1),
    (2, 'https://vk.com/Bob', 1),
    (3, 'https://vk.com/Clara', 1),
    (4, 'https://vk.com/Denis', 1),
    (5, 'https://vk.com/Elon', 1),
    (1, 'https://Alice.example.com', 5),
    (2, 'https://Bob.example.com', 5),
    (3, 'https://Clara.example.com', 5),
    (4, 'https://Denis.example.com', 5),
    (5, 'https://Elon.example.com', 5);

insert into events.event(id, name, description, age_limit, organizer_id, payment_service_url) values
    (1, 'First event', 'Sample event of first_user',    10, 1, 'https://payment.example.com/1'),
    (2, 'Second event', 'Sample event of second_user',  12, 2, 'https://payment.example.com/2'),
    (3, 'Third event', 'Sample event of third_user',    14, 3, 'https://payment.example.com/3'),
    (4, 'Fourth event', 'Sample event of fourth_user',  6, 4, 'https://payment.example.com/4'),
    (5, 'Fifth event', 'Sample event of fifth_user',    18, 5, 'https://payment.example.com/5');

insert into events.tag(id, name) values
    (1, 'Кино'),
    (2, 'Лекция'),
    (3, 'Театр'),
    (4, 'День открытых дверей'),
    (5, 'Концерт');

insert into events.event_tag(event_id, tag_id) values
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (4, 2);

insert into events.tag_blacklist(account_id, tag_id) values
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

insert into events.federal_subject_type(id, name, abbreviation) values
    (1, 'город федерального значения', 'г.ф.з.'),
    (2, 'область', 'обл.'),
    (3, 'край', 'кр.'),
    (4, 'республика', 'респ.'),
    (5, 'автономный округ', 'а.о.');

insert into events.federal_subject(id, name, federal_subject_type_id) values
    (1, 'Москва', 1),
    (2, 'Санкт-Петербург', 1),
    (3, 'Севастополь', 1),
    (4, 'Алтай', 4),
    (5, 'Татарстан', 4),
    (6, 'Алтайский', 3),
    (7, 'Краснодарский', 3),
    (8, 'Пермский', 3),
    (9, 'Амурская', 2),
    (10, 'Белгородская', 2),
    (11, 'Воронежская', 2),
    (12, 'Ненецкий', 5),
    (13, 'Ханты-Мансийский', 5),
    (14, 'Чукотский', 5),
    (15, 'Саха(Якутия)', 4);

insert into events.federal_subject_timezone(id, federal_subject_id, utc_offset) values
    (1, 1, '3 hours'::interval), -- Москва
    (2, 2, '3 hours'::interval), -- Санкт-Петербург
    (3, 3, '3 hours'::interval), -- Севастополь
    (4, 4, '7 hours'::interval), -- Алтай
    (5, 5, '3 hours'::interval), -- Татарстан
    (6, 6, '7 hours'::interval), -- Алтайский
    (7, 7, '3 hours'::interval), -- Краснодарский
    (8, 8, '5 hours'::interval), -- Пермский
    (9, 9, '9 hours'::interval), -- Амурская
    (10, 10, '3 hours'::interval), -- Белгородская
    (11, 11, '3 hours'::interval), -- Воронежская
    (12, 12, '3 hours'::interval), -- Ненецкий
    (13, 13, '5 hours'::interval), -- Ханты-Мансийский
    (14, 14, '12 hours'::interval), -- Чукотский
    (15, 15, '9 hours'::interval), -- Саха(Якутия)
    (16, 15, '10 hours'::interval), -- Саха(Якутия)
    (17, 15, '11 hours'::interval); -- Саха(Якутия)

insert into events.locality_type(id, name, abbreviation) values
    (1, 'город', 'г.'),
    (2, 'поселок городского типа', 'п.г.т.'),
    (3, 'деревня', 'д.'),
    (4, 'село', 'с.'),
    (5, 'станица', 'ст-ца');

insert into events.locality(id, name, locality_type_id, federal_subject_timezone_id) values
    (1, 'Москва', 1, 1),
    (2, 'Санкт-Петербург', 1, 2),
    (3, 'Севастополь', 1, 3),
    (4, 'Панино', 2, 11),
    (5, 'Няшино', 3, 8),
    (6, 'Алтайское', 4, 6),
    (7, 'Каневская', 5, 7),
    (8, 'Якутск', 1, 15),
    (9, 'Батагай', 2, 16),
    (10, 'Среднеколымск', 1, 17);

insert into events.place_type(id, name, abbreviation) values
    (1, 'улица', 'ул.'),
    (2, 'проспект', 'просп.'),
    (3, 'набережная', 'наб.'),
    (4, 'парк', 'парк'),
    (5, 'площадь', 'пл.');

insert into events.place(id, name, place_type_id, locality_id) values
    (1, 'Маросейка', 1, 1),
    (2, 'Думская', 1, 2),
    (3, 'Садовая', 1, 2),
    (4, 'Ленинский', 2, 1),
    (5, 'Невский', 2, 2),
    (6, 'Науки', 2, 2),
    (7, 'Крымская', 3, 1),
    (8, 'реки Мойки', 3, 2),
    (9, 'канала Грибоедова', 3, 2),
    (10, 'Горького', 4, 1),
    (11, 'имени Академика Сахарова', 4, 2),
    (12, 'Пионерский', 4, 2),
    (13, 'Ленинская', 5, 1),
    (14, 'Академика Иоффе', 5, 2),
    (15, 'Мужества', 5, 2);

insert into events.clarification_type(id, name, abbreviation, output_order) values
    (1, 'дом', 'д.', 1),
    (2, 'корпус', 'корп.', 2),
    (3, 'литера', 'лит.', 2),
    (4, 'квартира', 'кв.', 3),
    (5, 'аудитория', 'ауд.', 3);

insert into events.clarification(id, place_id, clarification_type_id, value) values
    (1, 3, 1, '1'),
    (2, 3, 1, '2'),
    (3, 3, 1, '3'),
    (4, 5, 1, '4'),
    (5, 5, 1, '5'),
    (6, 5, 1, '28'),
    (7, 6, 1, '7'),
    (8, 6, 1, '8'),
    (9, 6, 1, '9');

insert into events.location(id, place_id, name, map_url) values
    (1, 5, 'Дом Зингера', 'https://yandex.ru/maps/-/CCVXi8MV'),
    (2, 5, null, null),
    (3, 5, null, null),
    (4, 3, null, null),
    (5, 3, null, null);

insert into events.location_clarification(location_id, clarification_id) values
    (1, 6),
    (2, 4),
    (3, 5),
    (4, 1),
    (5, 2);

insert into events.saved_location(account_id, location_id) values
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

insert into events.event_date(id, event_id, location_id, description, start_date, end_date) values
    (1, 2, 1, null, current_timestamp + '2 days'::interval, current_timestamp + '2 days 2 hours'::interval),
    (2, 2, 1, null, current_timestamp + '3 days'::interval, current_timestamp + '3 days 3 hours'::interval),
    (3, 4, 1, null, current_timestamp + '4 days'::interval, current_timestamp + '4 days 4 hours'::interval),
    (4, 4, 1, null, current_timestamp + '5 days'::interval, current_timestamp + '5 days 5 hours'::interval),
    (5, 4, 1, null, current_timestamp + '6 days'::interval, current_timestamp + '6 days 6 hours'::interval);

insert into events.event_cost(id, event_date_id, cost, description) values
    (1, 1, 1200, 'Обычный'),
    (2, 2, 1000, 'Обычный'),
    (3, 3, 1200, 'Обычный'),
    (4, 4, 1600, 'Обычный'),
    (5, 5, 1300, 'Для детей до 12 лет'),
    (6, 5, 1600, 'Обычный');

insert into events.message(id, sender_id, recipient_id, message) values
    (1, 1, 3, 'Question or notification № 1'),
    (2, 2, 4, 'Question or notification № 2'),
    (3, 3, 2, 'Question or notification № 3');

insert into events.saved_event(account_id, event_id) values
    (1, 2),
    (2, 3),
    (3, 4),
    (4, 5),
    (5, 1);

insert into events.saved_event_date(account_id, event_date_id) values
    (1, 2),
    (2, 3),
    (3, 4),
    (4, 5),
    (5, 1);

insert into events.organizer_subscription(account_id, organizer_id) values
    (1, 2),
    (2, 3),
    (3, 4),
    (4, 5),
    (5, 1);

insert into events.organizer_blacklist(account_id, organizer_id) values
    (1, 3),
    (2, 4),
    (3, 5),
    (4, 1),
    (5, 2);

commit transaction;