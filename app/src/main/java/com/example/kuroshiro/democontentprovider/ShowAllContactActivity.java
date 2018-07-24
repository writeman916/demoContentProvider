package com.example.kuroshiro.democontentprovider;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.CursorLoader;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ShowAllContactActivity extends Activity {
    Button btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_contact);
        btnback=(Button) findViewById(R.id.btnback);
        btnback.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) { finish();
            }
        });
        ReadContactAtDevice();
    }

    public void ReadContactAtDevice() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        ArrayList<String> mContactArrayList = new ArrayList<>();
//        String ContactName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
//        String ContractPhone = ContactsContract.CommonDataKinds.Phone.NUMBER;
//        String ojContract[] = { ContactName, ContractPhone };
//        Cursor mCursor = getContentResolver().query(uri, ojContract, null, null, null);
//        if (mCursor != null && mCursor.moveToFirst()) {
//            do {
//                String name = mCursor.getString(0);
//                String phone = mCursor.getString(1);
//                mContactArrayList.add(name+" - " + phone);
//            } while (mCursor.moveToNext());
//            mCursor.close();
//        }
        String name = "", phoneNumber = "";
        Cursor mCursor = getContentResolver().query(uri,null,null,null,null);
        while (mCursor.moveToNext())
        {
            int id = mCursor.getInt(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
            name = mCursor.getString(mCursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = mCursor.getString(mCursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER));
            mContactArrayList.add(id+"-"+name+": " + phoneNumber);
        }
        ListView lv=(ListView) findViewById(R.id.listView1);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mContactArrayList);
        lv.setAdapter(adapter);
    }



}