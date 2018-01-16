package com.haieros.process_provider_server;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Kang on 2018/1/16.
 */

public class BookProvider extends ContentProvider {

    private static final String TAG = BookProvider.class.getSimpleName();
    private static final String AUTHORITIY = "com.haieros.process_provider_server.BookProvider";
    private static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITIY + "/book");
    private static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITIY + "/user");
    private static final int BOOK_URI_CODE = 0;
    private static final int USER_URI_CODE = 1;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITIY, "book", BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITIY, "user", USER_URI_CODE);
    }

    private SQLiteDatabase mDb;
    private Context mContext;

    public String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DBOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DBOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }

    @Override
    public boolean onCreate() {
        Log.e(TAG, "onCreate current Thread " + Thread.currentThread().getName());
        mContext = getContext();
        initProviderData();
        return true;
    }

    private void initProviderData() {
        mDb = new DBOpenHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from " + DBOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DBOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(3,'andorid');");
        mDb.execSQL("insert into book values(4,'ios');");
        mDb.execSQL("insert into book values(5,'h5');");
        mDb.execSQL("insert into user values(1,'jake',1);");
        mDb.execSQL("insert into user values(2,'tom',0);");
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.e(TAG, "query current Thread " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if(tableName == null) {
            throw new IllegalArgumentException("Unsupported uri:"+uri);
        }
        return mDb.query(tableName,projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.e(TAG, "getType current Thread " + Thread.currentThread().getName());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.e(TAG, "insert current Thread " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if(tableName == null) {
            throw new IllegalArgumentException("Unsupported uri:"+uri);
        }
        mDb.insert(tableName, null, values);
        getContext().getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.e(TAG, "delete current Thread " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if(tableName == null) {
            throw new IllegalArgumentException("Unsupported uri:"+uri);
        }
        int result = mDb.delete(tableName,selection,selectionArgs);
        if(result > 0) {
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return result;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.e(TAG, "update current Thread " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if(tableName == null) {
            throw new IllegalArgumentException("Unsupported uri:"+uri);
        }
        int update = mDb.update(tableName, values, selection, selectionArgs);
        if(update > 0) {

            getContext().getContentResolver().notifyChange(uri,null);
        }
        return update;
    }
}
