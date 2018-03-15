package eu.epitech.todolist.Utils;

import android.provider.BaseColumns;

public class TaskContract {

    public static final String DB_NAME = "eu.epitech.todolist.db";
    public static final int DB_VERSION = 2;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "Title";
        public static final String COL_TASK_DECS = "Description";
        public static final String COL_TASK_DATE = "Date";
        public static final String COL_TASK_TIME = "Time";
        public static final String COL_TASK_CATEGORY = "Category";
        public static final String COL_TASK_STATUS = "Status";

    }
}
