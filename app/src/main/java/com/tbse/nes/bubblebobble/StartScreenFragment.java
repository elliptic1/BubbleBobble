package com.tbse.nes.bubblebobble;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by tbsmith on 7/18/14.
 */
public class StartScreenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View startScreenView = inflater.inflate(R.layout.start_screen_fragment, container, false);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/prstart.otf");
        TextView tv = (TextView) startScreenView.findViewById(R.id.startGameText);
        tv.setTypeface(tf);

        return startScreenView;

    }

}
