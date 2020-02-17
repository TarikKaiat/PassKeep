package com.project.myprojectdemo;

public class Password {
    private MyAdapter myAdapter;
    private PasswordDB db;
    private int id;
    private String title,password;
    public Password(){

    }
    public Password(MyAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
