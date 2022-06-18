package com.example.providertest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ShowList extends AppCompatActivity {
    private List<Contact> list = new ArrayList<>();
    ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        initData();
        ListView listView = (ListView) findViewById(R.id.contacts);
        contactsAdapter = new ContactsAdapter(this,R.layout.item,list);
        listView.setAdapter(contactsAdapter);

        Button bt = (Button) findViewById(R.id.single);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText data = (EditText) findViewById(R.id.singleData);
                String id = data.getText().toString();
                Log.d("testId",id);
                Intent intent = new Intent(ShowList.this,SingleActivity.class);
                intent.putExtra("id",id);

                startActivity(intent);
            }
        });

    }

    private void initData() {
        Uri uri = Uri.parse("content://com.example.contactstest.provider/contact");
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
                Contact contact = new Contact();
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String sex = cursor.getString(cursor.getColumnIndex("sex"));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));

                contact.setName(name);
                contact.setSex(sex);
                contact.setPhone(phone);
                Log.d("FirstActivity",contact.toString());
                list.add(contact);
            }
        }else {
            Log.d("FirstActivity","空");
        }
        if (cursor!=null){
            cursor.close();
        }
    }
}