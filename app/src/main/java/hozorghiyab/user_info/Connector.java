package hozorghiyab.user_info;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Connector {

    public static Object connect(String urlAdress) {

        try {

            URL url = new URL(urlAdress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            con.setDoInput(true);
            con.setDoOutput(true);
            return con;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return ErrorTracker.WRONG_URL;
        }
        catch (IOException e) {
            e.printStackTrace();
            return ErrorTracker.CONNECTION_ERROR;
        }

    }
}
