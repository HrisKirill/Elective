SELECT * FROM user
left join users_courses
on user.id = users_courses.user_id
where users_courses.course_id = ? and user.status = 'unblocked'
limit ?,?