package com.hristoforov.elective.entities.course;


import lombok.Builder;
import lombok.Data;

import java.sql.Date;

/**
 * Course entity. Matches 'course' table in database.
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
@Data
@Builder
public class Course {
    private Long id;
    private String title;
    private int duration;
    private Date startDate;
    private Date endDate;
}
