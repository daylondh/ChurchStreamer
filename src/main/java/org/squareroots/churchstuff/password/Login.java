package org.squareroots.churchstuff.password;

public class Login {

    public static boolean authenticate(String username, String password) {
        // hardcoded username and password
        if (username.equals("hi") && password.equals("hi")) {
            return true;
        }
        return false;
    }
}