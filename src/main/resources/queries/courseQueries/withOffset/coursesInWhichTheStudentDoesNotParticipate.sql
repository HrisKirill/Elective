select * from course
where id not in (select course_id from users_courses where  users_courses.user_id = ?) and course.end_date > curdate()
limit ?,?