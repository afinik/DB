package com.example.cryptocurrencyrates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.annotation.SuppressLint;
import android.content.Context;
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

//    MutableLiveData<String> priceHigh = new MutableLiveData<String>();
//    MutableLiveData<String> priceLow = new MutableLiveData<String>();
//    MutableLiveData<String> priceVolume = new MutableLiveData<String>();
//    MutableLiveData<String> priceLast = new MutableLiveData<String>();


    MainViewModule mainViewModule = new MainViewModule(getApplication());

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(R.layout.activity_main);
    }

    void downloadData(String pairId) {
        new MyAsync(this).execute(pairId);
        Log.e("success is ", String.valueOf(MyAsync.success));
        if (MyAsync.success){
            //TODO here we add smth to the Database
            try {
                mainViewModule.addCurrencyRates(MyAsync.jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("error", "Ошибка записи в Базу Данных. Запись не осуществлена.");
            }
        }
        else {
            //TODO присваиваем предыдущее значение из БД

        }
    }

    static class MyAsync extends AsyncTask<String, Integer, ArrayList<Double>> {


        WeakReference<MainActivity> mainActivityWeakReference;
        static boolean success;
        static JSONArray jsonArray;

        public   MyAsync(MainActivity activity) {
            mainActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected ArrayList<Double> doInBackground(String... strings) {
            ArrayList<Double> result = new ArrayList<>();
            try {
                final String s = getData("https://api.bittrex.com/api/v1.1/public/getmarketsummary?market=" + strings[0]);
                Log.d("CRYPTO_CURRENCY_LOAD","JSON:" + s);
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                if (success) {
                    Log.e("Операция", "Одобрена - " + String.valueOf(success));
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
//                    for(int i = 0; i < jsonArray.length(); ++i) {
                    int i = 0;
                    for (CurrencyPairs curr : CurrencyPairs.values()) {
                        JSONObject pair = jsonArray.getJSONObject(i);
                        String pairCurrency = curr.getCode();
                        double high = pair.getDouble("High");
                        double low = pair.getDouble("Low");
                        double volume = pair.getDouble("Volume");
                        double last = pair.getDouble("Last");
                        result.add(high);
                        result.add(low);
                        result.add(volume);
                        result.add(last);
                        i++;
                    }
                } else {
                    Log.d("CRYPTO_CURRENCY_LOAD","UNKNOWN ERROR");
                }
            } catch (IOException e) {
                e.printStackTrace();
//                success = false;
            } catch (JSONException e) {
                e.printStackTrace();
//                success = false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Double> result) {
            super.onPostExecute(result);
            MainActivity activity = mainActivityWeakReference.get();
            if(activity != null && !activity.isFinishing()) {
                if(activity.mainViewModule.priceHigh != null) {
                    activity.mainViewModule.priceHigh.setValue(result.get(0).toString());
                    activity.mainViewModule.priceLow.setValue(result.get(1).toString());
                    activity.mainViewModule.priceVolume.setValue(result.get(2).toString());
                    activity.mainViewModule.priceLast.setValue(result.get(3).toString());
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
