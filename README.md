# Database Lab

### Лабораторная 1: Разработка структуры БД

Разработка схемы базы данных. База данных для сервиса-афиши.<br>

<b>Схема:</b> https://github.com/KirillKhoroshkov/database-lab/tree/master/diagram/initial

### Лабораторная 2: Язык SQL-DDL

Создание таблиц БД с первичными и внешними ключами в соответствии со схемой, составленной в лабораторной работе 1.

<b>Скрипт:</b> https://github.com/KirillKhoroshkov/database-lab/blob/master/script/001_create.sql

Заполнение таблиц тестовыми данными.

<b>Скрипт:</b> https://github.com/KirillKhoroshkov/database-lab/blob/master/script/002_test_data.sql

Внесение изменений в БД.

<b>Скрипт:</b> https://github.com/KirillKhoroshkov/database-lab/blob/master/script/003_alter.sql
        
<b>Схема:</b> https://github.com/KirillKhoroshkov/database-lab/tree/master/diagram/modified

### Лабораторная 3: Генерация тестовых данных

Реализация в виде программы на Kotlin параметризуемого генератора, позволяющего формировать набор данных в каждой таблице.

<b>Программа:</b> https://github.com/KirillKhoroshkov/database-lab/tree/master/generator/events

Перед запуском нужно удалить ограничение у таблицы <i>event_date</i>.

<b>Команда:</b> `alter table events.event_date drop constraint date_validation;`

После запуска ограничение нужно снова добавить.

<b>Команда:</b> `alter table events.event_date add constraint date_validation check (
        start_date at time zone 'UTC' > CURRENT_TIMESTAMP
        and end_date at time zone 'UTC' > CURRENT_TIMESTAMP
        and end_date > start_date
    ) not valid;`

А также обновить sequence-ы.

<b>Скрипт:</b> https://github.com/KirillKhoroshkov/database-lab/blob/master/script/004_restore_sequence.sql

### Лабораторная 4: Язык SQL-DML

Реализация стандартных запросов.

<b>Скрипт:</b> https://github.com/KirillKhoroshkov/database-lab/blob/master/script/005_standart_query.sql

Реализация запросов, в соответствии с полученным индивидуальным заданием.

<b>Скрипт:</b> https://github.com/KirillKhoroshkov/database-lab/blob/master/script/006_individual_query.sql

### Лабораторная 5: SQL-программирование, хранимые процедуры

Создание хранимых процедур в соответствии с индивидуальным заданием.

<b>Скрипт:</b> https://github.com/KirillKhoroshkov/database-lab/blob/master/script/007_stored_procedure.sql

### Лабораторная 6: SQL-программирование, триггеры, вызовы процедур

Создание двух триггеров: один триггер для автоматического заполнения ключевого поля, второй триггер для контроля целостности данных в подчиненной таблице при удалении/изменении записей в главной
таблице.

<b>Скрипт:</b> https://github.com/KirillKhoroshkov/database-lab/blob/master/script/008_standart_trigger.sql

Создание двух триггеров в соответствии с индивидуальным заданием.

<b>Скрипт:</b> https://github.com/KirillKhoroshkov/database-lab/blob/master/script/006_individual_query.sql

### Курсовая работа: Реализация GraphQL API

Приложение, реализующее доступ к базе данных посредством GraphQL API. Должна быть возможность с помощью приложения просматривать, добавлять, изменять и удалять данные из БД.

<b>Программа:</b> https://github.com/KirillKhoroshkov/database-lab/tree/master/graphql/events-graph
