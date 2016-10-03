package by.epam.audioorder.entity;

import java.util.Objects;

public class User {
    private long userId;
    private String login;
    private String passwordHash;
    private String email;
    private UserType role;

    public User() {
        this.role = UserType.USER;
    }

    public User(long userId, String login, String passwordHash, UserType role, double balance) {
        this.userId = userId;
        this.login = login;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

    public void setRole(String role) {
        this.role = UserType.valueOf(role.toUpperCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                Objects.equals(login, user.login) &&
                Objects.equals(passwordHash, user.passwordHash) &&
                Objects.equals(email, user.email) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, passwordHash, email, role);
    }
}
