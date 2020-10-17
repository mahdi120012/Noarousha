package hozorghiyab.customClasses;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlEncoderClass {

    public static String urlEncoder(String myString){
        String myStringEncode="";
        try {
            myStringEncode = URLEncoder.encode(myString,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return myStringEncode;
    }

}
