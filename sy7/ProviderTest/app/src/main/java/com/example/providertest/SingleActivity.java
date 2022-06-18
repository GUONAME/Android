package com.example.providertest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SingleActivity extends AppCompatActivity {
    private List<Contact> list = new ArrayList<>();
    ContactsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getStringExtra("id"));
        Log.d("testId",String.valueOf(id));

        ListView listView = (ListView) findViewById(R.id.singleContact);
        adapter = new ContactsAdapter(this,R.layout.item,list);
        listView.setAdapter(adapter);


        Uri uri = Uri.parse("content://com.example.contactstest.provider/contact/"+id);
        Cursor cursor;
        cursor = getContentResolver().query(uri,null,null,null,null);
        if (cursor.moveToFirst()){
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String sex = cursor.getString(cursor.getColumnIndex("sex"));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));

            Contact contact = new Contact();
            contact.setName(name);
            contact.setSex(sex);
            contact.setPhone(phone);
            list.add(contact);
        }
        if (cursor!=null){
            cursor.close();
        }
    }
}