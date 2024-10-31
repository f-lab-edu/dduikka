DROP TABLE IF EXISTS member;
CREATE TABLE member
(
    member_id     INT           NOT NULL AUTO_INCREMENT,
    email         NVARCHAR(100) NOT NULL,
    password      NVARCHAR(512) NOT NULL,
    member_status NVARCHAR(50)  NOT NULL DEFAULT 0,
    join_date     DATE          NOT NULL,
    leave_date    DATE          NULL,
    created_at    DATETIME      NOT NULL DEFAULT NOW(),
    PRIMARY KEY (member_id)
);

DROP TABLE IF EXISTS vote;
CREATE TABLE vote
(
    vote_id    INT      NOT NULL AUTO_INCREMENT,
    vote_date  DATE     NOT NULL UNIQUE,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (vote_id)
);

DROP TABLE IF EXISTS live_chat;
CREATE TABLE live_chat
(
    live_chat_id INT      NOT NULL AUTO_INCREMENT,
    member_id    INT      NOT NULL,
    message      TEXT     NOT NULL,
    created_at   DATETIME NOT NULL DEFAULT NOW(),
    deleted_flag TINYINT  NOT NULL DEFAULT 0,
    PRIMARY KEY (live_chat_id)
);

DROP TABLE IF EXISTS vote_record;
CREATE TABLE vote_record
(
    vote_record_id INT          NOT NULL AUTO_INCREMENT,
    vote_id        INT          NOT NULL,
    member_id      INT          NOT NULL,
    vote_type      NVARCHAR(50) NOT NULL,
    canceled_flag  TINYINT      NOT NULL DEFAULT 0,
    created_at     DATETIME     NOT NULL DEFAULT NOW(),
    PRIMARY KEY (vote_record_id)
);

DROP TABLE IF EXISTS member_location;
CREATE TABLE member_location
(
    member_location_id  VARCHAR(255)  NOT NULL,
    member_id           INT           NOT NULL,
    city                NVARCHAR(50)  NULL,
    district            NVARCHAR(100) NULL,
    latitude            NVARCHAR(100) NOT NULL,
    longitude           NVARCHAR(100) NOT NULL,
    city_code           NVARCHAR(100) NULL,
    representative_flag TINYINT       NOT NULL DEFAULT 0,
    deleted_flag        TINYINT       NOT NULL DEFAULT 0,
    created_at          DATETIME      NOT NULL DEFAULT NOW(),
    PRIMARY KEY (member_location_id)
);

DROP TABLE IF EXISTS weather;
CREATE TABLE weather
(
    weather_id        NVARCHAR(10) NOT NULL,
    latitude          NVARCHAR(50) NOT NULL,
    longitude         NVARCHAR(50) NOT NULL,
    forecast_datetime DATETIME     NOT NULL,
    temperature       DOUBLE       NULL,
    relative_humidity INT          NULL,
    rainfall          DOUBLE       NULL,
    snowfall          DOUBLE       NULL,
    created_at        DATETIME     NOT NULL,
    PRIMARY KEY (weather_id)
);
