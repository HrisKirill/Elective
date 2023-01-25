SELECT * FROM course
left join users_courses
on course.id = users_courses.course_id
where users_courses.user_id = ? and course.start_date < now() and course.end_date > now()
