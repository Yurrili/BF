package com.uj.yuri.budgetflow;

import android.app.FragmentManager;

/**
 * Created by Yuri on 2016-01-17.
 */

public interface Command
{
    void execute(FragmentManager cmd);
}