package com.hristoforov.elective.dao.interfaces;

import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.entities.topic.Topic;
import com.hristoforov.elective.exceptions.DataBaseInteractionException;

import java.util.List;

/**
 * TopicDao interface with all operations
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public interface TopicDao extends Dao<Topic> {

    /**
     * Find topic by title
     *
     * @param title - topic title
     * @return topic with this title
     * @throws DataBaseInteractionException
     */
    Topic findByTitle(String title) throws DataBaseInteractionException;

    /**
     * Find topic by course id
     *
     * @param courseId - course id
     * @return topic
     * @throws DataBaseInteractionException
     */
    List<Topic> findTopicsByCourse(Long courseId) throws DataBaseInteractionException;

    /**
     * Find all topics that not include course with this id
     *
     * @param courseId - course id
     * @return list of topics
     */
    List<Topic> topicsThatDoNotIncludeTheCourse(Long courseId);

    /**
     * Insert into table 'courses_topics' this pair
     *
     * @param topic  - topic
     * @param course - course
     * @throws DataBaseInteractionException
     */
    void createTopicCourse(Topic topic, Course course) throws DataBaseInteractionException;
}
