package com.uj.yuri.budgetflow.db_managment.BackUp;

/**
 * Created by Yuri on 2016-01-17.
 */
public class ImpExpUses {

    private ImportExport impExpStr;

    public void setImpExpWayStr(ImportExport impExpStr)
    {
        this.impExpStr = impExpStr;
    }

    public void doIt()
    {
        impExpStr.moveDataBase();
    }

}
