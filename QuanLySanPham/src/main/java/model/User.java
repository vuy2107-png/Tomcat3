package model;

import java.sql.Timestamp;

public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private int roleId; // Đã sửa lỗi
    private boolean isActive;
    private Timestamp createdAt;

    public User(String email, String password, String name, int roleId, boolean isActive) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roleId = roleId;
        this.isActive = isActive;
    }

    public User(int id, String email, String password, String name, int roleId, boolean isActive, Timestamp createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.roleId = roleId;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public User() {}

    // --- Getters and Setters ---
    // Khắc phục lỗi 'getRole' bằng cách dùng getRoleId()
    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}