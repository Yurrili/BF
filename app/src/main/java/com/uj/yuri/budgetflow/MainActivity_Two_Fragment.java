package com.uj.yuri.budgetflow;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class MainActivity_Two_Fragment extends Fragment {
    private View myFragmentView;

    public MainActivity_Two_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_two_main_activity, container, false);

        CircularProgressBar circularProgressBar = (CircularProgressBar)myFragmentView.findViewById(R.id.yourCircularProgressbar);
        circularProgressBar.setColor(ContextCompat.getColor(getContext(), R.color.redo));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.redo1));
        //circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.progressBarWidth));
        //circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.backgroundProgressBarWidth));
        int animationDuration = 2500; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(65, animationDuration); // Default duration = 1500ms
        circularProgressBar.setProgress(40);
        return myFragmentView;
    }


}
