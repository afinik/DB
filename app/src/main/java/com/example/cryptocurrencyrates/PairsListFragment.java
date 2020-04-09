package com.example.cryptocurrencyrates;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class PairsListFragment extends Fragment {

    public PairsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pairs_list, container, false);
        final RadioGroup radioGroup = v.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.ETHADX:
                        Log.d("CURRENCY","ETHADX");
                        ((MainActivity)getActivity()).downloadData("ETH-ADX");
                        break;
                    case R.id.USDTOMG:
                        ((MainActivity)getActivity()).downloadData("USDT-OMG");
                        Log.d("CURRENCY","USDTOMG");
                        break;
                    case R.id.BTCDGB:
                        ((MainActivity)getActivity()).downloadData("BTC-DGB");
                        Log.d("CURRENCY","BTCDGB");
                        break;
                    case R.id.USDUSDS:
                        ((MainActivity)getActivity()).downloadData("USD-USDS");
                        Log.d("CURRENCY","USDUSDS");
                        break;
                    default:
                        break;
                }
            }
        });
        return v;
    }
}
