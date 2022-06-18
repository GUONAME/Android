package com.example.contactstest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        dbHelper = new MyDatabaseHelper(this,"Contacts.db",null,2);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*创建数据库*/
                dbHelper.getWritableDatabase();
                Toast.makeText(FirstActivity.this, "建库成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button add_data = (Button) findViewById(R.id.add_data);
        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*添加联系人*/
                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                if(cursor!=null){
                    int n = 0;
                    String sex;
                    while (cursor.moveToNext()){
//                        Contact contact = new Contact();
                        /*获取系统联系人姓名、性别、号码，插入到应用的Contact数据库*/
                        @SuppressLint("Range") String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        if(n%2==0)
                            sex = "男";
                        else
                            sex = "女";
//
//                        values.put("name",displayName);
//                        values.put("phone",number);
//                        values.put("sex",sex);
//                        db.insert("contact",null,values);
//                        values.clear();
//                        n++;

                        Uri uri = Uri.parse("content://com.example.contactstest.provider/contact");
                        ContentValues values = new ContentValues();
                        values.put("name",displayName);
                        values.put("phone",number);
                        values.put("sex",sex);
                        getContentResolver().insert(uri,values);
                    }
                }
                cursor.close();
                Toast.makeText(FirstActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            }
        });


        Button query_data = (Button) findViewById(R.id.query_data);
        query_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*查询应用的数据库所有联系人*/
                Intent intent = new Intent(FirstActivity.this,ShowList.class);
                startActivity(intent);
            }
        });

//        Button add_data = (Button) findViewById(R.id.add_data);
//        Button add_data = (Button) findViewById(R.id.add_data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                }else{
                    Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}