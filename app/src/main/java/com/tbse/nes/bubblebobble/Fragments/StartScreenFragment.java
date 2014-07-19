package com.tbse.nes.bubblebobble.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tbse.nes.bubblebobble.ActivityMain;
import com.tbse.nes.bubblebobble.R;

/**
 * Created by tbsmith on 7/18/14.
 */
public class StartScreenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View startScreenView = inflater.inflate(R.layout.start_screen_fragment, container, false);


        final TextView tv = (TextView) startScreenView.findViewById(R.id.startGameText);
        tv.setTypeface(ActivityMain.tf);
        tv.setTextColor(getResources().getColor(R.color.white));

        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.d("bb", "ontouch");

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    tv.setTextColor(getResources().getColor(R.color.red));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    tv.setTextColor(getResources().getColor(R.color.white));
                    getFragmentManager().beginTransaction().replace(
                            getId(), ActivityMain.fragments.get(1)
                    ).commit();
                }

                return true;

            }
        });

        return startScreenView;

    }

}
