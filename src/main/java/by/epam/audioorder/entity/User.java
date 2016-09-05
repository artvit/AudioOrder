package by.epam.audioorder.entity;

public class User {
    private long userId;
    private String login;
    private String passwordHash;
    private String email;
    private UserType role;
    private double balance;

    public User() {
        this.role = UserType.USER;
        this.balance = 0;
    }

    public User(long userId, String login, String passwordHash, UserType role, double balance) {
        this.userId = userId;
        this.login = login;
        this.passwordHash = passwordHash;
        this.role = role;
        this.balance = balance;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
