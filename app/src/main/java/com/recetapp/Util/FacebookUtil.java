package com.recetapp.util;

import android.content.Context;

import com.facebook.AccessToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class FacebookUtil {

    public static boolean isFacebookLoggedIn() {
        return AccessToken.getCurrentAccessToken() != null;
    }

    public static Map<String,Object> assertfbUserData(JSONObject object) throws JSONException {
        Map<String, Object> result = new HashMap<>();
        result.put("email", object.get("email"));
        result.put("name", object.get("name"));
        return result;
    }

    public static RequestCreator getUserPicture(Context ctx, String userId) {
        return Picasso.with(ctx).load("https://graph.facebook.com/" + userId + "/picture?type=large");
    }

}
