package com.tbse.nes.bubblebobble.Fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.tbse.nes.bubblebobble.ActivityMain;
import com.tbse.nes.bubblebobble.R;

/**
 * Created by tbsmith on 7/18/14.
 */
public class StartScreenFragment extends Fragment {

    MediaPlayer music;
    private boolean musicIsPlaying;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View startScreenView = inflater.inflate(R.layout.start_screen_fragment, container, false);

        music = MediaPlayer.create(requireActivity().getApplicationContext(), R.raw.intro);
        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                music.reset();
                music.release();
                musicIsPlaying = false;
            }
        });
        music.start();
        musicIsPlaying = true;

        final TextView tv = (TextView) startScreenView.findViewById(R.id.startGameText);
        tv.setTypeface(ActivityMain.tf);
        tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));

        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (musicIsPlaying) {
                    music.stop();
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                    getParentFragmentManager().beginTransaction().replace(
                            getId(), ActivityMain.fragments.get(1)
                    ).commit();
                }

                return true;

            }
        });

        return startScreenView;

    }

}
