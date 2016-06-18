package com.recetapp.Util;

import com.facebook.login.LoginManager;
import com.recetapp.model.User;

/**
 * Created by Victor on 18/06/2016.
 */
public class UserUtil {

    public static void createUserManager(User user) {
        User u = UserManager.getManager().getUser();
        u.setEmail(user.getEmail());
        u.setName(user.getName());
        u.setId(user.getId());
    }

    //Logout depending on provider
    public static void logOut() {
        if(UserManager.getManager().getAuthData() != null) {
            if (UserManager.getManager().getAuthData().getProvider().equals("facebook")) {
                LoginManager.getInstance().logOut();
            }
        }
    }
}
