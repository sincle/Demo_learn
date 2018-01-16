package com.haieros.process_provider_client;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Uri uri = Uri.parse("content://com.haieros.process_provider_server.BookProvider");
        Uri bookUri = Uri.parse("content://com.haieros.process_provider_server.BookProvider/book");
        Uri userUri = Uri.parse("content://com.haieros.process_provider_server.BookProvider/user");

        ContentValues values = new ContentValues();
        values.put("_id",6);
        values.put("name","android6");
        getContentResolver().insert(bookUri,values);
//        getContentResolver().query(uri,null,null,null,null);
//        getContentResolver().query(uri,null,null,null,null);
//        getContentResolver().query(uri,null,null,null,null);
        Cursor query = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);

        while (query.moveToNext()){
            int bookId = query.getInt(0);
            String bookName = query.getString(1);
            Log.e(TAG, "bookId:"+bookId+",bookName:"+bookName);
        }
        query.close();

        Cursor cursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);

        while (cursor.moveToNext()){
            int userId = cursor.getInt(0);
            String userName = cursor.getString(1);
            int sex = cursor.getInt(2);

            Log.e(TAG, "userId:"+userId+",userName:"+userName+",sex:"+sex);
        }

        cursor.close();
    }
}
