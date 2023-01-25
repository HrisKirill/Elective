CREATE TABLE topic
(
    id          INT         NOT NULL AUTO_INCREMENT,
    topic_title VARCHAR(45) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX title_UNIQUE (topic_title) VISIBLE
);
