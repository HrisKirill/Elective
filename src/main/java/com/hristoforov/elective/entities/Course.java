package com.hristoforov.elective.entities;


import java.sql.Date;
import java.util.Objects;

public class Course {
    private Long id;
    private String title;
    private int duration;
    private Date startDate;
    private Date endDate;
    private User user;

    public Course(){}

    public Course(Long id, String title, int duration, Date startDate, Date endDate, User user) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return duration == course.duration
                && Objects.equals(id, course.id)
                && Objects.equals(title, course.title)
                && Objects.equals(startDate, course.startDate)
                && Objects.equals(endDate, course.endDate)
                && Objects.equals(user, course.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, duration, startDate, endDate, user);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", user=" + user +
                '}';
    }
}
