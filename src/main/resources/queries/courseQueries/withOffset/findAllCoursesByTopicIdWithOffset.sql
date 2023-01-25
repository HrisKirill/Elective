SELECT * FROM course
left join courses_topics
on course.id = courses_topics.course_id
where courses_topics.topic_id = ?
limit ?,?
