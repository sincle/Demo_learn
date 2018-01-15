package com.haieros.mobileapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<RequestParameter> params = new ArrayList<RequestParameter>();
        RequestParameter rp3 = new RequestParameter("cityAa2", "111");
        RequestParameter rp1 = new RequestParameter("cityaA", "111");
        RequestParameter rp2 = new RequestParameter("cityName", "Beijing");
        params.add(rp1);
        params.add(rp3);
        params.add(rp2);

        RemoteService.getInstance().invoke(this, "getWeatherInfo", params,
                weatherCallback);
    }
}
