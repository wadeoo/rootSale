package cn.edu.fzu.rootsale.model;

public class User {
    private Long id;
    private String username;
    private String password;
    private String address;

    public User(String username, String password, String address) {
        this.username = username;
        this.password = password;
        this.address = address;
    }

    // 构造函数、Getter和Setter方法


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
