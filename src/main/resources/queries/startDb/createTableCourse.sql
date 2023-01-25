CREATE TABLE course (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    duration INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    count_of_student INT default 0,
    PRIMARY KEY (id),
    UNIQUE INDEX title_UNIQUE (title ASC) VISIBLE
);
