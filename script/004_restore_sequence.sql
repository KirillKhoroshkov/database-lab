
select setval('events.account_id_seq', (select max(id) from events.account) + 1);
select setval('events.account_report_id_seq', (select max(id) from events.account_report) + 1);
select setval('events.clarification_id_seq', (select max(id) from events.clarification) + 1);
select setval('events.clarification_type_id_seq', (select max(id) from events.clarification_type) + 1);
select setval('events.event_comment_id_seq', (select max(id) from events.event_comment) + 1);
select setval('events.event_cost_id_seq', (select max(id) from events.event_cost) + 1);
select setval('events.event_date_id_seq', (select max(id) from events.event_date) + 1);
select setval('events.event_date_report_id_seq', (select max(id) from events.event_date_report) + 1);
select setval('events.event_id_seq', (select max(id) from events.event) + 1);
select setval('events.event_report_id_seq', (select max(id) from events.event_report) + 1);
select setval('events.federal_subject_id_seq', (select max(id) from events.federal_subject) + 1);
select setval('events.federal_subject_timezone_id_seq', (select max(id) from events.federal_subject_timezone) + 1);
select setval('events.federal_subject_type_id_seq', (select max(id) from events.federal_subject_type) + 1);
select setval('events.link_id_seq', (select max(id) from events.link) + 1);
select setval('events.link_type_id_seq', (select max(id) from events.link_type) + 1);
select setval('events.locality_id_seq', (select max(id) from events.locality) + 1);
select setval('events.locality_type_id_seq', (select max(id) from events.locality_type) + 1);
select setval('events.location_id_seq', (select max(id) from events.location) + 1);
select setval('events.message_id_seq', (select max(id) from events.message) + 1);
select setval('events.place_id_seq', (select max(id) from events.place) + 1);
select setval('events.place_type_id_seq', (select max(id) from events.place_type) + 1);
select setval('events.tag_id_seq', (select max(id) from events.tag) + 1);