insert into member(member_id, email, password, member_status, join_date, leave_date, created_at)
values (1, 'test@dduikka.net', '1234', 'JOIN', CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP);
insert into member(member_id, email, password, member_status, join_date, leave_date, created_at)
values (2, 'test2@dduikka.net', '1234', 'LEAVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into member(member_id, email, password, member_status, join_date, leave_date, created_at)
values (3, 'test3@dduikka.net', '1234', 'JOIN', CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP);

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

-- live_chat
insert into live_chat(live_chat_id, member_id, message, is_deleted, created_at)
values (1, 1, '초기 메세지를 세팅합니다', 0, CURRENT_TIMESTAMP);
insert into live_chat(live_chat_id, member_id, message, is_deleted, created_at)
values (2, 1, '초기 메세지를 세팅합니다', 0, CURRENT_TIMESTAMP);
insert into live_chat(live_chat_id, member_id, message, is_deleted, created_at)
values (3, 1, '오늘도 뛰어야 하는데 뛰기 싫다', 0, CURRENT_TIMESTAMP);
insert into live_chat(live_chat_id, member_id, message, is_deleted, created_at)
values (4, 1, '마라톤 신청 괜히 했다', 0, CURRENT_TIMESTAMP);
insert into live_chat(live_chat_id, member_id, message, is_deleted, created_at)
values (5, 1, '오늘 미세먼지 너무 나빠요', 0, CURRENT_TIMESTAMP);
insert into live_chat(live_chat_id, member_id, message, is_deleted, created_at)
values (6, 3, '야외 러닝 못한지 오래됨', 0, CURRENT_TIMESTAMP);
insert into live_chat(live_chat_id, member_id, message, is_deleted, created_at)
values (7, 3, '오늘 가산에 불 났대요', 0, CURRENT_TIMESTAMP);
insert into live_chat(live_chat_id, member_id, message, is_deleted, created_at)
values (8, 1, '1호선이 싫어요', 0, CURRENT_TIMESTAMP);
insert into live_chat(live_chat_id, member_id, message, is_deleted, created_at)
values (9, 3, '2호선도 싫어요', 0, CURRENT_TIMESTAMP);
insert into live_chat(live_chat_id, member_id, message, is_deleted, created_at)
values (10, 3, '출근하기 싫어요', 0, CURRENT_TIMESTAMP);
insert into live_chat(live_chat_id, member_id, message, is_deleted, created_at)
values (11, 1, '전 백수지만 출근하기 싫어요2222', 0, CURRENT_TIMESTAMP);
insert into live_chat(live_chat_id, member_id, message, is_deleted, created_at)
values (12, 1, '오운완', 0, CURRENT_TIMESTAMP);
insert into live_chat(live_chat_id, member_id, message, is_deleted, created_at)
values (13, 3, '노 오프셋 페이징 신기했어요', 0, CURRENT_TIMESTAMP);
insert into live_chat(live_chat_id, member_id, message, is_deleted, created_at)
values (14, 3, '쏟아져 내리는 빛이 댄스댄스댄스', 0, CURRENT_TIMESTAMP);
