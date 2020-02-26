package com.imuons.contact;

import com.activeandroid.Configuration;
import com.activeandroid.content.ContentProvider;
import com.imuons.contact.dbtables.Contact;
import com.imuons.contact.dbtables.Deletee;
import com.imuons.contact.dbtables.Favourite;

public class DatabaseContentProviders extends ContentProvider {

    @Override
    protected Configuration getConfiguration() {
        Configuration.Builder builder = new Configuration.Builder(getContext());
        builder.addModelClass(Contact.class);
        builder.addModelClass(Favourite.class);
        builder.addModelClass(Deletee.class);
        return builder.create();
    }
}

