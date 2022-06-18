package com.example.contactstest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ContactsAdapter extends ArrayAdapter<Contact> {
    private int resourceId;
    public ContactsAdapter( Context context, int resource,  List<Contact> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Contact contact = getItem(position);//获取当前项的Rank实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView name = (TextView) view.findViewById(R.id.c_name);
        TextView phone = (TextView) view.findViewById(R.id.c_phone);
        TextView sex = (TextView) view.findViewById(R.id.c_sex);
        sex.setText(contact.getSex());
        name.setText(contact.getName());
        phone.setText(contact.getPhone());
        return view;
    }
}
