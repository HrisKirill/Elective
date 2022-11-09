select *from course
left outer join user
on course.user_id = user.user_id
where course.title=?
