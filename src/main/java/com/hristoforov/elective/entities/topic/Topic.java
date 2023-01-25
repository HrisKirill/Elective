package com.hristoforov.elective.entities.topic;

import lombok.Builder;
import lombok.Data;


/**
 * Topic entity. Matches 'topic' table in database.
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
@Data
@Builder
public class Topic {
    private Long id;
    private String title;
}
