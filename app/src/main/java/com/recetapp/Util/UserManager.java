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
    private AuthData authData;
    private Firebase ref;

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

    public AuthData getAuthData() {
        return authData;
    }

    public void setAuthData(AuthData authData) {
        this.authData = authData;
    }

    public Firebase getRef() {
        return ref;
    }

    public void setRef(Firebase ref) {
        this.ref = ref;
    }
}
