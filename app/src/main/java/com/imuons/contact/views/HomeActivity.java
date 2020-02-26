package com.imuons.contact.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.imuons.contact.HelperMethods;
import com.imuons.contact.R;
import com.imuons.contact.adapter.Contact_Adapter;
import com.imuons.contact.dbtables.Contact;
import com.imuons.contact.dbtables.Deletee;
import com.imuons.contact.dbtables.Favourite;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    byte[] photoByte = null;    private static ListView contact_listview;
    private static ProgressDialog pd;
    private static Contact_Adapter adapter;
    List<Contact> contactList;
    List<Favourite> favouriteList;
    List<Deletee> deleteeList;
    Cursor cur;
    ContentResolver cr;
    @BindView(R.id.contact)
    Button contact;
    @BindView(R.id.favourites)
    Button favourites;
    @BindView(R.id.deleted)
    Button deleted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        askForContactPermission();

        //new HelperMethods().clearContact();

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent contact = new Intent(HomeActivity.this, ContactActivity.class);
                startActivity(contact);
            }
        });
        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contact = new Intent(HomeActivity.this, FavouriteActivity.class);
                startActivity(contact);
            }
        });
        deleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contact = new Intent(HomeActivity.this, DeletedActivity.class);
                startActivity(contact);
            }
        });

    }
    private void getcontact() {
        cr  = getContentResolver();

        cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);

        pd = ProgressDialog.show(HomeActivity.this, "Loading Contacts",
                "Please Wait...");
        if (cur.moveToFirst()) {
            do {
                long contctId = cur.getLong(cur
                        .getColumnIndex("_ID")); // Get contact ID
                Uri dataUri = ContactsContract.Data.CONTENT_URI; // URI to get

                Cursor dataCursor = getContentResolver().query(dataUri, null,
                        ContactsContract.Data.CONTACT_ID + " = " + contctId,
                        null, null);// Retrun data cusror represntative to

                String displayName = "";
                String nickName = "";
                String homePhone = "";
                String mobilePhone = "";
                String workPhone = "";
                String photoPath = "" + R.drawable.ic_launcher; // Photo path
                byte[] photoByte = null;// Byte to get photo since it will come
                // in BLOB

                String contactNumbers = "";

                String contactOtherDetails = "";

                // Now start the cusrsor
                if (dataCursor.moveToFirst()) {
                    displayName = dataCursor
                            .getString(dataCursor
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));// get
                    // the
                    // contact
                    // name
                    do {
                        if (dataCursor
                                .getString(
                                        dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE)) {
                            nickName = dataCursor.getString(dataCursor
                                    .getColumnIndex("data1")); // Get Nick Name
                            contactOtherDetails += "NickName : " + displayName
                            ;// Add the nick name to string //+ "\n"

                        }

                        if (dataCursor
                                .getString(
                                        dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {


                            switch (dataCursor.getInt(dataCursor
                                    .getColumnIndex("data2"))) {


                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    mobilePhone = dataCursor.getString(dataCursor
                                            .getColumnIndex("data1"));
                                    contactNumbers += "Mobile Phone : "
                                            + mobilePhone ; //+ "\n"
                                    break;

                            }
                        }


                        if (dataCursor
                                .getString(
                                        dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
                            photoByte = dataCursor.getBlob(dataCursor
                                    .getColumnIndex("data15")); // get photo in
                            // byte

                            if (photoByte != null) {


                                Bitmap bitmap = BitmapFactory.decodeByteArray(
                                        photoByte, 0, photoByte.length);
                                File cacheDirectory = getBaseContext()
                                        .getCacheDir();
                                File tmp = new File(cacheDirectory.getPath()
                                        + "/_androhub" + contctId + ".png");
                                try {
                                    FileOutputStream fileOutputStream = new FileOutputStream(
                                            tmp);
                                    bitmap.compress(Bitmap.CompressFormat.PNG,
                                            100, fileOutputStream);
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    e.printStackTrace();
                                }
                                photoPath = tmp.getPath();

                            }

                        }

                    } while (dataCursor.moveToNext());

                    Contact contact = new Contact();
                    contact.contactName = displayName;
                    contact.contactId = contctId;
                    contact.contactNumber= mobilePhone;
                    contact.contactPhoto = photoPath;
                    contact.save();
                    contactList = new Select().from(Contact.class).execute();
                    Log.e("contactsize: ", contactList.size()+"");
                }

            } while (cur.moveToNext());
        }
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
//        adapter = new Contact_Adapter(HomeActivity.this, contactList);
//        contact_listview.setAdapter(adapter);// set adapter

        // Hide dialog if showing
        if (pd.isShowing())
            pd.dismiss();
    }

    public void askForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS}
                                    , PERMISSIONS_REQUEST_READ_CONTACTS);
                        }
                    });
                    builder.show();
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {



                    ActivityCompat.requestPermissions(HomeActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            PERMISSIONS_REQUEST_READ_CONTACTS);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {

            }
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
getcontact();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(this, "No permission for contacts", Toast.LENGTH_LONG).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
