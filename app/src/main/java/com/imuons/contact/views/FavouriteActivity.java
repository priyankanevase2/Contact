package com.imuons.contact.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.imuons.contact.R;
import com.imuons.contact.adapter.Contact_Adapter;
import com.imuons.contact.adapter.Favorite_Adapter;
import com.imuons.contact.dbtables.Contact;
import com.imuons.contact.dbtables.Favourite;

import java.util.List;

public class FavouriteActivity extends AppCompatActivity {
    private static ListView favorite_listview;

    private static Favorite_Adapter adapter;
    List<Favourite> favoritelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        getSupportActionBar().setTitle("Favorite Contacts");
        favorite_listview = (ListView) findViewById(R.id.favorite_listview);
        favoritelist = new Select().from(Favourite.class).execute();
        adapter = new Favorite_Adapter(FavouriteActivity.this, favoritelist);
        favorite_listview.setAdapter(adapter);// set adapter
    }
}
