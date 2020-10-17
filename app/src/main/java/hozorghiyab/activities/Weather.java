package hozorghiyab.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.hozorghiyab.R;
import com.kwabenaberko.openweathermaplib.constants.Lang;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;

public class Weather extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        final TextView txMax = findViewById(R.id.txmax);
        final TextView txLow = findViewById(R.id.txLow);

        OpenWeatherMapHelper helper = new OpenWeatherMapHelper(getString(R.string.api));
        helper.setUnits(Units.METRIC);
        helper.setLang(Lang.ENGLISH);
        helper.getCurrentWeatherByCityName("tehran", new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                txLow.setText(String.valueOf(currentWeather.getMain().getTempMax()));
            }
            @Override
            public void onFailure(Throwable throwable) {
            }
        });

    }
}
