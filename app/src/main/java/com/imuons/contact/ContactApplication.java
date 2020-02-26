package com.imuons.contact;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.imuons.contact.dbtables.Contact;
import com.imuons.contact.dbtables.Deletee;
import com.imuons.contact.dbtables.Favourite;

import java.net.CacheRequest;

public class ContactApplication extends Application {
    //
    // Initialize Active Android & ButterKnife & Volley
    //
    public static final String TAG = ContactApplication.class.getSimpleName();

    private static ContactApplication mInstance;
    private RequestQueue mRequestQueue;

    public static synchronized ContactApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        com.activeandroid.Configuration dbConfiguration = new com.activeandroid.Configuration.Builder(this)
                .setDatabaseName("healthcare_db.db")
                .setDatabaseVersion(1)
                .addModelClass(Contact.class)
                .addModelClass(Favourite.class)
                .addModelClass(Deletee.class)


                .create();


        // Initialize ActiveAndroid for DB
        ActiveAndroid.initialize(dbConfiguration);


    }

    // get Request Queue
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }
    // Add a request to Request queue with Tag
    public <T> void addToRequestQueue(CacheRequest req, String tag) {
//        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
//
//        req.setRetryPolicy(new DefaultRetryPolicy(
//                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        getRequestQueue().add(req);
    }

    // Add a request to Request queue without Tag
    public <T> void addToRequestQueue(Request<T> req, String API_URL) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    // Cancel All pending requests
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
