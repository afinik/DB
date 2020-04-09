package com.example.cryptocurrencyrates;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PairDetailsFragment extends Fragment {

    public PairDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pair_details, container, false);
        final EditText editHigh = v.findViewById(R.id.editHigh);
        final EditText editLow = v.findViewById(R.id.editLow);
        final EditText editVolume = v.findViewById(R.id.editVolume);
        final EditText editLast = v.findViewById(R.id.editLast);
        ((MainActivity)getActivity()).priceHigh.observe(PairDetailsFragment.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                editHigh.setText(s);
            }
        });
        ((MainActivity)getActivity()).priceLow.observe(PairDetailsFragment.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                editLow.setText(s);
            }
        });
        ((MainActivity)getActivity()).priceVolume.observe(PairDetailsFragment.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                editVolume.setText(s);
            }
        });
        ((MainActivity)getActivity()).priceLast.observe(PairDetailsFragment.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                editLast.setText(s);
            }
        });
        return v;
    }
}
