SELECT * FROM topic
left join courses_topics
on courses_topics.topic_id=topic.id
where course_id=?