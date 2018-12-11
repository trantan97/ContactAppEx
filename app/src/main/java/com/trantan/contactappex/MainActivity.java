package com.trantan.contactappex;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final int READ_CONTACT_REQUEST_CODE = 1;
    public static final String FAVORITE = "1";
    private RecyclerView mRecyclerView;
    private List<Contact> mContacts;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRequestPermistions();
        setUpUi();
    }

    private void setUpUi() {
        mRecyclerView = findViewById(R.id.recycler);
        mAdapter = new RecyclerAdapter(mContacts);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        DividerItemDecoration decoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
    }

    private List<Contact> loadContact() {
        List<Contact> contacts = new ArrayList<>();
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            boolean isFavorite = FAVORITE.equals(cursor
                    .getString(cursor.getColumnIndex(ContactsContract.Contacts.STARRED)));
            contacts.add(new Contact(name, phoneNumber, isFavorite));
        }
        cursor.close();
        return contacts;
    }

    private void myRequestPermistions() {
        mContacts = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        READ_CONTACT_REQUEST_CODE);
            else mContacts.addAll(loadContact());
        } else mContacts.addAll(loadContact());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_CONTACT_REQUEST_CODE)
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mContacts.addAll(loadContact());
                mAdapter.notifyDataSetChanged();
            } else Toast.makeText(this, getString(R.string.access_deny), Toast.LENGTH_SHORT).show();
    }
}
