package com.recetapp.Util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;
import android.util.Patterns;

import com.facebook.login.LoginManager;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.recetapp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
        if (FacebookUtil.isFacebookLoggedIn()) {
            LoginManager.getInstance().logOut();
        }
        Firebase ref = new Firebase("https://recetapp-android.firebaseio.com/");
        ref.unauth();
    }

    //Obtains the list of emails used on the device
    public static List<String> getEmailAddress(Context context) {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(context).getAccounts();
        List<String> result = new ArrayList<>();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                result.add(account.name);
            }
        }
        return result;
    }
}
