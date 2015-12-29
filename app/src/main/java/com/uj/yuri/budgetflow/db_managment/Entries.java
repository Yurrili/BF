package com.uj.yuri.budgetflow.db_managment;

import android.provider.BaseColumns;


public class Entries {

    public Entries(){}

    public static abstract class Incomes implements BaseColumns {
        public static final String TABLE_NAME = "time_entries";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TimeEntry_ID = "time_entry_id";
        public static final String COLUMN_PROJECT_ID = "project_id";
        public static final String COLUMN_DATETIME_START = "starttime";
        public static final String COLUMN_DATETIME_FINISH = "endtime";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_SEND = "updated";
    }

    public static abstract class ProjectsTab implements BaseColumns {
        public static final String TABLE_NAME = "Projects";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_PROJECT_NAME = "name";
        public static final String COLUMN_ACTIVE = "active";
        public static final String COLUMN_update_at = "update_at";
    }

    public static abstract class Notific implements BaseColumns {
        public static final String TABLE_NAME = "Notifications";
        public static final String COLUMN_TIME = "time";

    }

}
