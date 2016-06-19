package com.recetapp.Util;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.recetapp.model.User;

/**
 * Singleton User class
 */
public class UserManager {

    private static UserManager userManager = null;
    private User user;

    private UserManager() {
        this.user = new User();
    }

    public static UserManager getManager() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }


    public User getUser() {
        return this.user;
    }

}
