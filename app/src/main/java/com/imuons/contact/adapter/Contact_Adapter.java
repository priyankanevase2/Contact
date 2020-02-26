package com.imuons.contact.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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
import com.activeandroid.query.Update;
import com.amulyakhare.textdrawable.TextDrawable;
import com.imuons.contact.R;

import com.imuons.contact.dbtables.Contact;
import com.imuons.contact.dbtables.Deletee;
import com.imuons.contact.dbtables.Favourite;
import com.imuons.contact.model.Contact_Model;
import com.imuons.contact.views.ContactActivity;

import java.util.ArrayList;
import java.util.List;

public class Contact_Adapter extends BaseAdapter {
    private Context context;
    List<Contact> contactList;
    List<Favourite>favouritelist;
    List<Deletee>deleteeList;
  //  private ArrayList<Contact_Model> arrayList;

    private int pos;


    public Contact_Adapter(ContactActivity context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }



    @Override
    public int getCount() {

        return contactList.size();
    }

    @Override
    public Contact getItem(int position) {

        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Contact model = contactList.get(position);

        ViewHodler holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_view, parent, false);
            holder = new ViewHodler();
            holder.contactImage = (ImageView) convertView
                    .findViewById(R.id.contactImage);
            holder.contactName = (TextView) convertView
                    .findViewById(R.id.contactName);

            holder.contactNumber = (TextView) convertView
                    .findViewById(R.id.contactNumber);

            holder.delete = (ImageView)convertView.findViewById(R.id.delete);
            holder.delete.setTag(position);
            holder.unfav = (ImageView)convertView.findViewById(R.id.unfav);
            holder.fav = (ImageView)convertView.findViewById(R.id.fav);
            convertView.setTag(holder);
        } else {
            holder = (ViewHodler) convertView.getTag();
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

if(contactList.get(position).isDeleted()){

    contactList.remove(position);
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

            else {
                TextDrawable drawable = TextDrawable.builder()
                        .buildRect(String.valueOf(model.contactName.charAt(0)), Color.RED);

                holder.contactImage.setImageDrawable(drawable);
            }
        } else {

            TextDrawable drawable = TextDrawable.builder()
                    .buildRect(String.valueOf(model.contactName.charAt(0)), Color.RED);

            holder.contactImage.setImageDrawable(drawable);
        }

       holder.delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               long cid =model.contactId;

               AlertDialog.Builder builder =  new AlertDialog.Builder(context);

               builder.setMessage("Are You sure you want to delete this contact?")
                       .setCancelable(false)
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {

                               Deletee deletee = new Deletee();
                               deletee.contactName = model.contactName;
                               deletee.contactId = model.contactId;
                               deletee.contactNumber= model.contactNumber;
                               deletee.contactPhoto = model.contactPhoto;
                                deletee.isDeleted = true;
                               deletee.save();
                               //Log.e("contactsize: ", contactList.size()+"");
                                new Delete().from(Contact.class).where("contactId=?", model.contactId).execute();
                                int localPos = (int) v.getTag();
                                contactList.remove(localPos);
                                notifyDataSetChanged();

                             //  Log.e("updatecontactsize: ", contactList.size()+"");
                               deleteeList = new Select().from(Deletee.class).execute();
                               Toast.makeText(context, "move to deleted!!", Toast.LENGTH_SHORT).show();

                           }
                       })
                       .setNegativeButton("No", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {

                               dialog.cancel();

                           }
                       });

               AlertDialog alert = builder.create();
               alert.setCanceledOnTouchOutside(true);
               alert.show();



           }
       });
        if(contactList.get(position).isFav()){
            holder.fav.setVisibility(View.VISIBLE);
            holder.unfav.setVisibility(View.GONE);

        }else{
            holder.fav.setVisibility(View.GONE);
            holder.unfav.setVisibility(View.VISIBLE);
        }
        holder.unfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long cid =model.contactId;
                Favourite favourite = new Favourite();
                favourite.contactName = model.contactName;
                favourite.contactId = model.contactId;
                favourite.contactNumber= model.contactNumber;
                favourite.contactPhoto = model.contactPhoto;
                favourite.isFav = true;
                favourite.save();
                favouritelist = new Select().from(Favourite.class).execute();

                Toast.makeText(context, "move to favorite!!", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.unfav.setImageResource(R.drawable.fav);
                }

            }
        });
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long cid =model.contactId;
                Favourite favourite = new Favourite();
                favourite.contactName = model.contactName;
                favourite.contactId = model.contactId;
                favourite.contactNumber= model.contactNumber;
                favourite.contactPhoto = model.contactPhoto;
                favourite.isFav = false;
                favourite.save();
                favouritelist = new Delete().from(Favourite.class).where("contactId=?", model.contactId).execute();
                Toast.makeText(context, "remove from favorite!!", Toast.LENGTH_SHORT).show();

                    holder.fav.setImageResource(R.drawable.unfav);


            }
        });
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




    private class ViewHodler {
        ImageView contactImage,delete,unfav,fav;
        TextView contactName, contactNumber;
    }
}

