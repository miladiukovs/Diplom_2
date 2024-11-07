package org.example;

public class UserCredentials {
    private String email;
    private String password;

    public UserCredentials(User userWithoutSomeData) {
    }

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCredentials from(User user) {
        return new UserCredentials(user.getEmail(), user.getPassword());
    }

    public static UserCredentials create(String email, String password) {
        return new UserCredentials(email, password);
    }

    public static UserCredentials create(User userWithoutSomeData) {
        return new UserCredentials(userWithoutSomeData);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
       this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
