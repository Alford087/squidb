/*
 * Copyright 2015, Yahoo Inc.
 * Copyrights licensed under the Apache 2.0 License.
 * See the accompanying LICENSE file for terms.
 */
package com.yahoo.squidb.sample.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yahoo.squidb.data.AbstractDatabase;
import com.yahoo.squidb.sample.models.Tag;
import com.yahoo.squidb.sample.models.Task;
import com.yahoo.squidb.sql.Index;
import com.yahoo.squidb.sql.Table;

/**
 * Implementation of AbstractDatabase for this tasks app. Remember--instances of your AbstractDatabase
 * subclass should always be singletons!
 */
public class TasksDatabase extends AbstractDatabase {

    private static final int VERSION = 2;

    public TasksDatabase(Context context) {
        super(context);
    }

    @Override
    protected String getName() {
        return "hello-squidb-tasks.db";
    }

    @Override
    protected int getVersion() {
        return VERSION;
    }

    @Override
    protected Table[] getTables() {
        return new Table[]{
                Task.TABLE,
                Tag.TABLE
        };
    }

    @Override
    protected Index[] getIndexes() {
        return new Index[]{
                Tag.TABLE.index("tag_taskid_idx", Tag.TASK_ID)
        };
    }

    @Override
    protected boolean onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Example DB migration if the tags table and tasks.priority columns were added in version 2
        switch (oldVersion) {
            case 1:
                tryCreateTable(Tag.TABLE);
                tryCreateIndex(Tag.TABLE.index("tag_taskid_idx", Tag.TASK_ID));
                tryAddColumn(Task.PRIORITY);
        }
        return false;
    }
}
