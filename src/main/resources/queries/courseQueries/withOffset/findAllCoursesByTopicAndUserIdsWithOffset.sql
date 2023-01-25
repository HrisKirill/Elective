select * from course
left join users_courses
on  course.id = users_courses.course_id
left join courses_topics
on course.id = courses_topics.course_id
where users_courses.user_id = ? and  courses_topics.topic_id = ?
limit ?,?