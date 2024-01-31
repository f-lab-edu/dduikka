insert into vote(vote_id, vote_date, created_at)
values (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into vote_record(vote_record_id, vote_id, user_id, vote_type, is_canceled, created_at)
values (1, 1, 1, 'RUN', 1, CURRENT_TIMESTAMP);
insert into vote_record(vote_record_id, vote_id, user_id, vote_type, is_canceled, created_at)
values (2, 1, 2, 'NORUN', 0, CURRENT_TIMESTAMP);
insert into vote_record(vote_record_id, vote_id, user_id, vote_type, is_canceled, created_at)
values (3, 1, 3, 'RUN', 0, CURRENT_TIMESTAMP);
insert into vote_record(vote_record_id, vote_id, user_id, vote_type, is_canceled, created_at)
values (4, 1, 4, 'NORUN', 0, CURRENT_TIMESTAMP);
insert into vote_record(vote_record_id, vote_id, user_id, vote_type, is_canceled, created_at)
values (5, 1, 1, 'RUN', 0, CURRENT_TIMESTAMP);
