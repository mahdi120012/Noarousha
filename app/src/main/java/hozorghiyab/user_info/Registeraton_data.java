package hozorghiyab.user_info;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;


public class Registeraton_data {

    User user;


    public Registeraton_data(User user) {
        this.user = user;
    }


    public String packregisterationData() {

        JSONObject jo = new JSONObject();
        StringBuffer jsonData = new StringBuffer();

        try {
            jo.put("NAME", user.getName());
            jo.put("PHONENUMBER", user.getPhoneNumber());
            jo.put("PASSWORD", user.getPassword());

            Boolean isFirstValue = true;
            Iterator it = jo.keys();

            do {
                String key = it.next().toString();
                String Value = jo.get(key).toString();
                if (isFirstValue) {
                    isFirstValue = false;

                } else {

                    jsonData.append("&");
                }
                jsonData.append(URLEncoder.encode(key, "UTF-8"));
                jsonData.append("=");
                jsonData.append(URLEncoder.encode(Value, "UTF-8"));

            }
            while (it.hasNext());
            return jsonData.toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return ErrorTracker.REGISTERATION_DATA_ERROR;
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ErrorTracker.REGISTERATION_DATA_ENCODING_ERROR;
        }

    }
}
