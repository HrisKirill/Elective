CREATE TABLE topic
(
    id    INT         NOT NULL AUTO_INCREMENT,
    title VARCHAR(45) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX title_UNIQUE (title ASC) VISIBLE
);