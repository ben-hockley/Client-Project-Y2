package org.example.groupproject.admin;

public record Admin (String username,
                     String password,
                     String emailAddress,
                     Boolean isAdmin
){ }
