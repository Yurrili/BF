package com.uj.yuri.budgetflow.db_managment.db_helper_objects;

import android.provider.BaseColumns;


public class Entries {

    public Entries(){}

    public static abstract class Incomes implements BaseColumns {
        public static final String TABLE_NAME = "Incomes";
        public static final String COLUMN_INCOME_NAME = "Name";
        public static final String COLUMN_AMOUNT = "Amount";
        public static final String COLUMN_DATETIME_START = "Start_time";
        public static final String COLUMN_DATETIME_FINISH = "End_time";
        public static final String COLUMN_FREQUENCY = "Frequency";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_DURATION = "Duration";
        public static final String COLUMN_ACTIVE = "Active";

        public static final String[] selectAllList = new String[]{ _ID, COLUMN_INCOME_NAME, COLUMN_AMOUNT,
                COLUMN_DATETIME_START, COLUMN_DATETIME_FINISH, COLUMN_FREQUENCY, COLUMN_DESCRIPTION, COLUMN_DURATION};
    }

    public static abstract class Outcomes implements BaseColumns {

        public static final String TABLE_NAME = "Outcomes";
        public static final String COLUMN_OUTCOME_NAME = "Name";
        public static final String COLUMN_CATEGORY_ID = "Category_id";
        public static final String COLUMN_DATETIME_START = "Start_time";
        public static final String COLUMN_DATETIME_FINISH = "End_time";
        public static final String COLUMN_AMOUNT = "Amount";
        public static final String COLUMN_FREQUENCY = "Frequency";
        public static final String COLUMN_ACTIVE = "Active";

        public static final String[] selectAllList = new String [] { _ID, COLUMN_OUTCOME_NAME, COLUMN_AMOUNT,
                 COLUMN_DATETIME_START, COLUMN_DATETIME_FINISH, COLUMN_CATEGORY_ID, COLUMN_FREQUENCY };

    }

    public static abstract class Categories implements BaseColumns {
        public static final String TABLE_NAME = "Categories";
        public static final String COLUMN_CATEGORY_NAME = "Name";

        public static final String[] selectAllList = new String [] { _ID, COLUMN_CATEGORY_NAME };
    }

    public static abstract class Hist_Saldo implements BaseColumns {
        public static final String TABLE_NAME = "Saldos";
        public static final String COLUMN_TIME = "Time";
        public static final String COLUMN_AMOUNT = "Amount";

        public static final String[] selectAllList = new String [] { _ID, COLUMN_TIME, COLUMN_AMOUNT };

    }

}
