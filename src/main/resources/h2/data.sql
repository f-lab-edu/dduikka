insert into member(email, password, member_status, join_date, leave_date, created_at)
values ('test@dduikka.net', '$2a$10$m/3nG8j0jZ1y4NxkfWcDdu05vx3w8wCjCN.Ni9PtNKTLZfoUF7FH2', 'JOIN', CURRENT_TIMESTAMP,
        null, CURRENT_TIMESTAMP);
insert into member(email, password, member_status, join_date, leave_date, created_at)
values ('test2@dduikka.net', '$2a$10$m/3nG8j0jZ1y4NxkfWcDdu05vx3w8wCjCN.Ni9PtNKTLZfoUF7FH2', 'LEAVE', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into member(email, password, member_status, join_date, leave_date, created_at)
values ('test3@dduikka.net', '$2a$10$m/3nG8j0jZ1y4NxkfWcDdu05vx3w8wCjCN.Ni9PtNKTLZfoUF7FH2', 'JOIN', CURRENT_TIMESTAMP,
        null, CURRENT_TIMESTAMP); -- 12345678qW!@

insert into vote(vote_date, created_at)
values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into vote_record(vote_id, user_id, vote_type, is_canceled, created_at)
values (1, 1, 'RUN', 1, CURRENT_TIMESTAMP);
insert into vote_record(vote_id, user_id, vote_type, is_canceled, created_at)
values (1, 2, 'NORUN', 0, CURRENT_TIMESTAMP);
insert into vote_record(vote_id, user_id, vote_type, is_canceled, created_at)
values (1, 3, 'RUN', 0, CURRENT_TIMESTAMP);
insert into vote_record(vote_id, user_id, vote_type, is_canceled, created_at)
values (1, 4, 'NORUN', 0, CURRENT_TIMESTAMP);
insert into vote_record(vote_id, user_id, vote_type, is_canceled, created_at)
values (1, 1, 'RUN', 0, CURRENT_TIMESTAMP);

-- live_chat
insert into live_chat(member_id, message, deleted_flag, created_at)
values (1, '초기 메세지를 세팅합니다', 0, CURRENT_TIMESTAMP);
insert into live_chat(member_id, message, deleted_flag, created_at)
values (1, '초기 메세지를 세팅합니다', 0, CURRENT_TIMESTAMP);
insert into live_chat(member_id, message, deleted_flag, created_at)
values (1, '오늘도 뛰어야 하는데 뛰기 싫다', 0, CURRENT_TIMESTAMP);
insert into live_chat(member_id, message, deleted_flag, created_at)
values (1, '마라톤 신청 괜히 했다', 0, CURRENT_TIMESTAMP);
insert into live_chat(member_id, message, deleted_flag, created_at)
values (1, '오늘 미세먼지 너무 나빠요', 0, CURRENT_TIMESTAMP);
insert into live_chat(member_id, message, deleted_flag, created_at)
values (3, '야외 러닝 못한지 오래됨', 0, CURRENT_TIMESTAMP);
insert into live_chat(member_id, message, deleted_flag, created_at)
values (3, '오늘 가산에 불 났대요', 0, CURRENT_TIMESTAMP);
insert into live_chat(member_id, message, deleted_flag, created_at)
values (1, '1호선이 싫어요', 0, CURRENT_TIMESTAMP);
insert into live_chat(member_id, message, deleted_flag, created_at)
values (3, '2호선도 싫어요', 0, CURRENT_TIMESTAMP);
insert into live_chat(member_id, message, deleted_flag, created_at)
values (3, '출근하기 싫어요', 0, CURRENT_TIMESTAMP);
insert into live_chat(member_id, message, deleted_flag, created_at)
values (1, '전 백수지만 출근하기 싫어요2222', 0, CURRENT_TIMESTAMP);
insert into live_chat(member_id, message, deleted_flag, created_at)
values (1, '오운완', 0, CURRENT_TIMESTAMP);
insert into live_chat(member_id, message, deleted_flag, created_at)
values (3, '노 오프셋 페이징 신기했어요', 0, CURRENT_TIMESTAMP);
insert into live_chat(member_id, message, deleted_flag, created_at)
values (3, '쏟아져 내리는 빛이 댄스댄스댄스', 0, CURRENT_TIMESTAMP);
