ALTER TABLE MEMBER
    ALTER COLUMN MEMBER_ID RESTART WITH 1;
ALTER TABLE VOTE
    ALTER COLUMN VOTE_ID RESTART WITH 1; -- 시퀀스 초기화
ALTER TABLE VOTE_RECORD
    ALTER COLUMN VOTE_RECORD_ID RESTART WITH 1;
ALTER TABLE LIVE_CHAT
    ALTER COLUMN LIVE_CHAT_ID RESTART WITH 1;
