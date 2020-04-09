package com.example.cryptocurrencyrates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MutableLiveData<String> priceHigh = new MutableLiveData<String>();
    MutableLiveData<String> priceLow = new MutableLiveData<String>();
    MutableLiveData<String> priceVolume = new MutableLiveData<String>();
    MutableLiveData<String> priceLast = new MutableLiveData<String>();

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(R.layout.activity_main);
    }

    void downloadData(String pairId) {
        new MyAsync(this).execute(pairId);
    }

    static class MyAsync extends AsyncTask<String, Integer, ArrayList<Double>> {

        WeakReference<MainActivity> mainActivityWeakReference;

        public MyAsync(MainActivity activity) {
            mainActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected ArrayList<Double> doInBackground(String... strings) {
            ArrayList<Double> result = new ArrayList<>();
            try {
                final String s = getData("https://api.bittrex.com/api/v1.1/public/getmarketsummary?market=" + strings[0]);
                Log.d("CRYPTO_CURRENCY_LOAD","JSON:" + s);
                JSONObject jsonObject = new JSONObject(s);
                boolean success = jsonObject.getBoolean("success");
                if (success) {
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for(int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject pair = jsonArray.getJSONObject(i);
                        double high = pair.getDouble("High");
                        double low = pair.getDouble("Low");
                        double volume = pair.getDouble("Volume");
                        double last = pair.getDouble("Last");
                        result.add(high);
                        result.add(low);
                        result.add(volume);
                        result.add(last);
                    }
                } else {
                    Log.d("CRYPTO_CURRENCY_LOAD","UNKNOWN ERROR");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Double> result) {
            super.onPostExecute(result);
            MainActivity activity = mainActivityWeakReference.get();
            if(activity != null && !activity.isFinishing()) {
                if(activity.priceHigh != null) {
                    activity.priceHigh.setValue(result.get(0).toString());
                    activity.priceLow.setValue(result.get(1).toString());
                    activity.priceVolume.setValue(result.get(2).toString());
                    activity.priceLast.setValue(result.get(3).toString());
                }
            }
        }

        public String getData(String baseURL) throws IOException {
            URL url = new URL(baseURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine())!=null){
                builder.append(line);
            }
            return builder.toString();
        }

    }


}
