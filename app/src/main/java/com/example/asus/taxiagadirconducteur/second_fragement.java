package com.example.asus.taxiagadirconducteur;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

public class second_fragement extends Fragment{
    private float r;

    public second_fragement() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_second, container, false) ;
        final RatingBar rb = view.findViewById(R.id.ratingBar8);
        final TextView t = view.findViewById(R.id.textView3);
        r=1.0f;
        rb.setRating(r);

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                t.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        t.setText("Very bad");
                        break;
                    case 2:
                        t.setText("Need some improvement");
                        break;
                    case 3:
                        t.setText("Good");
                        break;
                    case 4:
                        t.setText("Great");
                        break;
                    case 5:
                        t.setText("Awesome. I love it");
                        break;
                    default:
                        t.setText("");
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
