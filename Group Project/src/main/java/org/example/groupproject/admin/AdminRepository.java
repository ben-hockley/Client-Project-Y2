package org.example.groupproject.admin;

public class AdminRepository {

    //public final jdbcClient jdbcClient;

    public Boolean checkPassword(String username, String password) {
        return username == password;
    }
;}
