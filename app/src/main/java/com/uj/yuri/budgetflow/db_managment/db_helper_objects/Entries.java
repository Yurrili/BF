package com.uj.yuri.budgetflow.db_managment.db_helper_objects;

import android.provider.BaseColumns;


public class Entries {

    public Entries(){}

    public static abstract class Incomes implements BaseColumns {
        public static final String TABLE_NAME = "Incomes";
        public static final String COLUMN_ID = "Id";
        public static final String COLUMN_INCOME_NAME = "Name";
        public static final String COLUMN_AMOUNT = "Amount";
        public static final String COLUMN_DATETIME_START = "Start_time";
        public static final String COLUMN_DATETIME_FINISH = "End_time";
        public static final String COLUMN_FREQUENCY = "Frequency";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_DURATION = "Duration";
        public static final String COLUMN_ACTIVE = "Active";

        public static final String[] selectAllList = new String[]{"Id", "Name", "Amount",
                "Start_time", "End_time", "Frequency", "Description", "Duration"};
    }

    public static abstract class Outcomes implements BaseColumns {

        public static final String TABLE_NAME = "Outcomes";
        public static final String COLUMN_ID = "Id";
        public static final String COLUMN_OUTCOME_NAME = "Name";
        public static final String COLUMN_CATEGORY_ID = "Category_id";
        public static final String COLUMN_DATETIME_START = "Start_time";
        public static final String COLUMN_DATETIME_FINISH = "End_time";
        public static final String COLUMN_AMOUNT = "Amount";
        public static final String COLUMN_FREQUENCY = "Frequency";
        public static final String COLUMN_ACTIVE = "Active";

        public static final String[] selectAllList = new String [] { "Id", "Name", "Amount",
                 "Start_time", "End_time", "Category_id", "Frequency" };

    }

    public static abstract class Categories implements BaseColumns {
        public static final String TABLE_NAME = "Categories";
        public static final String COLUMN_ID = "Id";
        public static final String COLUMN_CATEGORY_NAME = "Name";


    }

    public static abstract class Notifications implements BaseColumns {
        public static final String TABLE_NAME = "Notifications";
        public static final String COLUMN_TIME = "time";

    }

}
