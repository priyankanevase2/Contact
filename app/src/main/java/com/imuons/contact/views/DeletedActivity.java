package com.imuons.contact.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.imuons.contact.R;
import com.imuons.contact.adapter.Delete_Adapter;
import com.imuons.contact.adapter.Favorite_Adapter;
import com.imuons.contact.dbtables.Deletee;
import com.imuons.contact.dbtables.Favourite;

import java.util.List;

public class DeletedActivity extends AppCompatActivity {
    private static ListView delete_listview;
    private static Delete_Adapter adapter;
    List<Deletee> deleteeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleted);
        getSupportActionBar().setTitle("Deleted Contacts");
        delete_listview = (ListView) findViewById(R.id.delete_listview);
        deleteeList = new Select().from(Deletee.class).execute();
        adapter = new Delete_Adapter(DeletedActivity.this, deleteeList);
        delete_listview.setAdapter(adapter);// set adapter
    }
}
