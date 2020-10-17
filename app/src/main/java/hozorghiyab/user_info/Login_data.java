package hozorghiyab.user_info;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;


public class Login_data {

    User_login user;


    public Login_data(User_login user) {
        this.user = user;
    }


    public String packLoginData() {

        JSONObject jo = new JSONObject();
        StringBuffer packData = new StringBuffer();

        try {
            jo.put("USERNAME", user.getUsernameOrEmail());
            jo.put("PASSWORD", user.getPassword());

            Boolean isFirstValue = true;
            Iterator it = jo.keys();

            do {
                String key = it.next().toString();
                String Value = jo.get(key).toString();
                if (isFirstValue) {
                    isFirstValue = false;

                } else {

                    packData.append("&");
                }
                packData.append(URLEncoder.encode(key, "UTF-8"));
                packData.append("=");
                packData.append(URLEncoder.encode(Value, "UTF-8"));

            }
            while (it.hasNext());
            return packData.toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return ErrorTracker.LOGIN_DATA_ERROR;
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ErrorTracker.LOGIN_DATA_ENCODING_ERROR;
        }

    }
}
