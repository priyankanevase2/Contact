package com.imuons.contact.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.imuons.contact.HelperMethods;
import com.imuons.contact.R;
import com.imuons.contact.adapter.Contact_Adapter;

import com.imuons.contact.adapter.Delete_Adapter;
import com.imuons.contact.dbtables.Contact;
import com.imuons.contact.dbtables.Deletee;
import com.imuons.contact.dbtables.Favourite;
import com.imuons.contact.model.Contact_Model;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {


    private static ListView contact_listview;
    private static Contact_Adapter adapter;
    List<Contact> contactList;
    List<Favourite> favouriteList;
    List<Deletee> deleteeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getSupportActionBar().setTitle("Contacts");
        contactList = new Select().from(Contact.class).execute();
        favouriteList = new Select().from(Favourite.class).execute();
        for(int i = 0 ;i < favouriteList.size(); i++){
            for(int j = 0 ;j < contactList.size(); j++){
                if(favouriteList.get(i).contactId == contactList.get(j).contactId){
                    contactList.get(j).setFav(favouriteList.get(i).isFav);


                }
            }
        }

        deleteeList = new Select().from(Deletee.class).execute();
        for(int i = 0 ;i < deleteeList.size(); i++){
            for(int j = 0 ;j < deleteeList.size(); j++){
                if(deleteeList.get(i).contactId == deleteeList.get(j).contactId){
                    contactList.get(j).setDeleted(deleteeList.get(i).isDeleted);


                }
            }
        }
        contact_listview = (ListView) findViewById(R.id.contact_listview);

        adapter = new Contact_Adapter(ContactActivity.this, contactList);
        contact_listview.setAdapter(adapter);// set adapter


    }

}