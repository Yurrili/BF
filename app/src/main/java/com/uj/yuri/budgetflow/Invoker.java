package com.uj.yuri.budgetflow;

import android.app.FragmentManager;

/**
 * Created by Yuri on 2016-01-17.
 */
public class Invoker {
    private Command command;
    private FragmentManager fg;

    public FragmentManager getFg()
    {
        return fg;
    }

    public void setFg(FragmentManager fg) {
        this.fg = fg;
    }

    public void setCommand(Command command)
    {
        this.command = command;
    }

    public void show()
    {
        command.execute(fg);
    }

}
