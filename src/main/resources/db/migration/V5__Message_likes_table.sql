CREATE TABLE message_likes (
    user_id BIGINT NOT NULL REFERENCES usr,
    message_id BIGINT NOT NULL REFERENCES message,
    PRIMARY KEY (user_id, message_id)
);