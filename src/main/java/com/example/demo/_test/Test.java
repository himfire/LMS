package com.example.demo._test;

public class Test {
    public static void main(String[] args) {
        String email = "test-test@example.com";
        String email2 = "h.khartoum@example.com";
        String email3 = "hhh9@ex.com";
        System.out.println(email+ " -->" + email.matches("^[a-zA-Z]+[A-Za-z0-9+_.-]{3,}+@[a-zA-Z0-9_.-]{3,}\\.[a-zA-Z]{2,4}$"));
        System.out.println(email2+ " -->" + email2.matches("^[a-zA-Z]+[A-Za-z0-9+_.-]{3,}+@[a-zA-Z0-9_.-]{3,}\\.[a-zA-Z]{2,4}$"));
        System.out.println(email3+ " -->" + email3.matches("^[a-zA-Z]+[A-Za-z0-9+_.-]{3,}+@[a-zA-Z0-9_.-]{3,}\\.[a-zA-Z]{2,4}$"));
    }
}
