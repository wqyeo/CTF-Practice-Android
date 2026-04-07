package com.example.practiceCTF;

@FunctionalInterface
public interface LoginAuthenticator {
    boolean authenticate(String username, String password);
}

