package com.example.todocapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todocapp.database.dao.ProjectDatabase;
import com.example.todocapp.models.Task;

public class TaskContentProvider extends ContentProvider {
    // FOR DATA
    public static final String AUTHORITY = "com.example.todocapp.provider";
    public static final String TABLE_NAME = Task.class.getSimpleName();
    public static final Uri URI_TASK = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (getContext() != null) {
            long taskId = ContentUris.parseId(uri);
            final Cursor cursor = ProjectDatabase.getInstance(getContext()).taskDao().getTasks();
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }
        throw new IllegalArgumentException("Failed to query row for uri" + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_NAME;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues conventValues) {
        if (getContext() != null) {
            final long id = ProjectDatabase.getInstance(getContext()).taskDao().createTask(Task.fromContentValues(conventValues));
            if (id != 0) {
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            }
        }
        throw new IllegalArgumentException("Failed to insert row into" + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
            if (getContext() != null) {
                final int count = ProjectDatabase.getInstance(getContext()).taskDao().deleteTask(ContentUris.parseId(uri));
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            }
            throw new IllegalArgumentException("Failed to delete row into" + uri);
        }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}


