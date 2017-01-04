package com.uj.yuri.budgetflow.DataManagment.ObjectsDO;

import android.provider.BaseColumns;


public class Entries {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String INTEGER_TYPE =" INTEGER";

    public static final String SQL_CREATE_ENTRIES_Incomes =
            "CREATE TABLE " + Entries.Incomes.TABLE_NAME + " (" +
                    Entries.Incomes._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Entries.Incomes.COLUMN_INCOME_NAME + TEXT_TYPE + COMMA_SEP +
                    Entries.Incomes.COLUMN_AMOUNT + TEXT_TYPE + COMMA_SEP +
                    Entries.Incomes.COLUMN_ACTIVE + INTEGER_TYPE + COMMA_SEP +
                    Entries.Incomes.COLUMN_DATETIME_START + TEXT_TYPE + COMMA_SEP +
                    Entries.Incomes.COLUMN_DATETIME_FINISH + TEXT_TYPE + COMMA_SEP +
                    Entries.Incomes.COLUMN_FREQUENCY + TEXT_TYPE + COMMA_SEP +
                    Entries.Incomes.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    Entries.Incomes.COLUMN_DURATION + INTEGER_TYPE +
                    " )";

    public static final String SQL_CREATE_ENTRIES_Outcomes =
            "CREATE TABLE " + Entries.Outcomes.TABLE_NAME + " (" +
                    Entries.Outcomes._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Entries.Outcomes.COLUMN_OUTCOME_NAME + TEXT_TYPE + COMMA_SEP +
                    Entries.Outcomes.COLUMN_AMOUNT + TEXT_TYPE + COMMA_SEP +
                    Entries.Outcomes.COLUMN_ACTIVE + INTEGER_TYPE + COMMA_SEP +
                    Entries.Outcomes.COLUMN_DATETIME_START + TEXT_TYPE + COMMA_SEP +
                    Entries.Outcomes.COLUMN_DATETIME_FINISH + TEXT_TYPE + COMMA_SEP +
                    Entries.Outcomes.COLUMN_FREQUENCY + TEXT_TYPE + COMMA_SEP +
                    Entries.Outcomes.COLUMN_CATEGORY_ID + INTEGER_TYPE +
                    " )";

    public static final String SQL_CREATE_ENTRIES_Categories =
            "CREATE TABLE " + Entries.Categories.TABLE_NAME + " (" +
                    Entries.Categories._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Entries.Categories.COLUMN_CATEGORY_NAME + TEXT_TYPE +
                    " )";

    public static final String SQL_CREATE_ENTRIES_Hist_Saldo =
            "CREATE TABLE " + Entries.Hist_Saldo.TABLE_NAME + " (" +
                    Entries.Hist_Saldo.COLUMN_TIME + TEXT_TYPE + COMMA_SEP +
                    Entries.Hist_Saldo.COLUMN_AMOUNT + TEXT_TYPE +
                    " )";


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
