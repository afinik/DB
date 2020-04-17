package com.example.cryptocurrencyrates;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainViewModule extends AndroidViewModel {
    private MyDBHelper myDBHelper;
    MutableLiveData<String> priceHigh = new MutableLiveData<String>();
    MutableLiveData<String> priceLow = new MutableLiveData<String>();
    MutableLiveData<String> priceVolume = new MutableLiveData<String>();
    MutableLiveData<String> priceLast = new MutableLiveData<String>();

    public MainViewModule(@NonNull Application application) {
        super(application);
        myDBHelper = new MyDBHelper(application);
    }

    public void addCurrencyRates(JSONArray jsonArray) throws JSONException {
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        db.beginTransaction();
        ContentValues cv = new ContentValues();

        int i = 0;
        for (CurrencyPairs curr : CurrencyPairs.values()) {
            JSONObject pair = jsonArray.getJSONObject(i);
            String pairCurrency = curr.getCode();
            cv.put(MyDBHelper.PAIR, pairCurrency);
            cv.put(MyDBHelper.HIGH, pair.getDouble("High"));
            cv.put(MyDBHelper.LOW, pair.getDouble("Low"));
            cv.put(MyDBHelper.VOLUME, pair.getDouble("Volume"));
            cv.put(MyDBHelper.LAST, pair.getDouble("Last"));
            i++;
        }



    }

    public void dropAndCreateDB(SQLiteDatabase db){
        myDBHelper.onUpgrade(db, myDBHelper.DB_VERSION, myDBHelper.DB_VERSION);
    }
}
