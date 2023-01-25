package com.hristoforov.elective.entities.user;

import com.hristoforov.elective.entities.enums.Role;
import com.hristoforov.elective.entities.enums.Status;

import java.util.Objects;


/**
 * User entity. Matches 'user' table in database.
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public class User {
    private Long id;
    private String lastName;
    private String firstName;
    private String login;
    private String password;
    private String email;
    private Role role;
    private Status status;

    public User() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(lastName, user.lastName) && Objects.equals(firstName, user.firstName) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && role == user.role && status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, login, password, email, role, status);
    }

    public static class Builder {

        private Long id;

        private String password = "";

        private String email = "";

        private String login = "";

        private String firstName = "";

        private String lastName = "";

        private Role role = Role.STUDENT;

        private Status status = Status.UNBLOCKED;


        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;

        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    private User(Builder builder) {
        id = builder.id;
        password = builder.password;
        email = builder.email;
        login = builder.login;
        firstName = builder.firstName;
        lastName = builder.lastName;
        role = builder.role;
        status = builder.status;
    }


    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", " + role +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", blocked='" + status +
                '}';
    }
}
