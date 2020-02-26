package com.imuons.contact.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.amulyakhare.textdrawable.TextDrawable;
import com.imuons.contact.R;
import com.imuons.contact.dbtables.Contact;
import com.imuons.contact.dbtables.Favourite;
import com.imuons.contact.views.ContactActivity;
import com.imuons.contact.views.FavouriteActivity;

import java.util.List;

public class Favorite_Adapter extends BaseAdapter {
    private Context context;

    List<Favourite>favouritelist;
    //  private ArrayList<Contact_Model> arrayList;

    private int pos;


    public Favorite_Adapter(FavouriteActivity context, List<Favourite> favouritelist) {
        this.context = context;
        this.favouritelist = favouritelist;
    }



    @Override
    public int getCount() {

        return favouritelist.size();
    }

    @Override
    public Favourite getItem(int position) {

        return favouritelist.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Favourite model = favouritelist.get(position);

        Favorite_Adapter.ViewHodler holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.favorite_view, parent, false);
            holder = new ViewHodler();
            holder.contactImage = (ImageView) convertView
                    .findViewById(R.id.contactImage);
            holder.contactName = (TextView) convertView
                    .findViewById(R.id.contactName);

            holder.contactNumber = (TextView) convertView
                    .findViewById(R.id.contactNumber);



            convertView.setTag(holder);
        } else {
            holder = (Favorite_Adapter.ViewHodler) convertView.getTag();
        }

        // Set items to all view
        if (!model.contactName.equals("")
                && model.contactName != null) {
            holder.contactName.setText(model.contactName);
        } else {
            holder.contactName.setText("No Name");
        }

        if (!model.contactNumber.equals("")
                && model.contactNumber != null) {
            holder.contactNumber.setText("CONTACT NUMBER -"
                    + model.contactNumber);
        } else {
            holder.contactNumber.setText("CONTACT NUMBER -"
                    + "No Contact Number");
        }



        // Bitmap for imageview
        Bitmap image = null;
        if (!model.contactPhoto.equals("")
                && model.contactPhoto != null) {
            image = BitmapFactory.decodeFile(model.contactPhoto);// decode
            // the
            // image
            // into
            // bitmap
            if (image != null)
                holder.contactImage.setImageBitmap(image);// Set image if bitmap
                // is not null
            else {
                TextDrawable drawable = TextDrawable.builder()
                        .buildRect(String.valueOf(model.contactName.charAt(0)), Color.RED);
//				image = BitmapFactory.decodeResource(context.getResources(),
//						R.drawable.androhub_logo);// if bitmap is null then set
//													// default bitmap image to
//													// contact image
                holder.contactImage.setImageDrawable(drawable);
            }
        } else {

            TextDrawable drawable = TextDrawable.builder()
                    .buildRect(String.valueOf(model.contactName.charAt(0)), Color.RED);
//			image = BitmapFactory.decodeResource(context.getResources(),
//					R.drawable.androhub_logo);
            holder.contactImage.setImageDrawable(drawable);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = model.contactNumber;
                AlertDialog.Builder builder =  new AlertDialog.Builder(context);

                builder.setMessage("Choose Action")
                        .setCancelable(false)
                        .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Uri u = Uri.parse("tel:" + phone);


                                Intent i = new Intent(Intent.ACTION_DIAL, u);

                                try
                                {

                                    context.startActivity(i);
                                }
                                catch (SecurityException s)
                                {

                                    Toast.makeText(context, s.getMessage(), Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                        })
                        .setNegativeButton("Message", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                                Intent intent = new Intent("android.intent.action.VIEW");


                                Uri data = Uri.parse("sms:" + phone);

                                intent.setData(data);


                                context.startActivity(intent);
                            }
                        });

                AlertDialog alert = builder.create();
                alert.setCanceledOnTouchOutside(true);
                alert.show();

            }
        });
        return convertView;
    }



    // View holder to hold views
    private class ViewHodler {
        ImageView contactImage;
        TextView contactName, contactNumber;
    }
}


