package com.imuons.contact.adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.amulyakhare.textdrawable.TextDrawable;
import com.imuons.contact.R;
import com.imuons.contact.dbtables.Contact;
import com.imuons.contact.dbtables.Deletee;
import com.imuons.contact.dbtables.Favourite;
import com.imuons.contact.views.DeletedActivity;
import com.imuons.contact.views.FavouriteActivity;

import java.util.List;

public class Delete_Adapter extends BaseAdapter {
    private Context context;
    List<Contact> contactList;
    List<Deletee> deleteeList;
    //  private ArrayList<Contact_Model> arrayList;

    private int pos;


    public Delete_Adapter(DeletedActivity context, List<Deletee> deleteeList) {
        this.context = context;
        this.deleteeList = deleteeList;
    }



    @Override
    public int getCount() {

        return deleteeList.size();
    }

    @Override
    public Deletee getItem(int position) {

        return deleteeList.get(position);

    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Deletee model = deleteeList.get(position);

        Delete_Adapter.ViewHodler holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_delete__adapter, parent, false);
            holder = new Delete_Adapter.ViewHodler();
            holder.contactImage = (ImageView) convertView
                    .findViewById(R.id.contactImage);
            holder.contactName = (TextView) convertView
                    .findViewById(R.id.contactName);

            holder.contactNumber = (TextView) convertView
                    .findViewById(R.id.contactNumber);


            holder.restore = (ImageView)convertView.findViewById(R.id.restore) ;
            holder.restore.setTag(position);
            convertView.setTag(holder);
        } else {
            holder = (Delete_Adapter.ViewHodler) convertView.getTag();
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

        holder.restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = new Contact();
                contact.contactName = model.contactName;
                contact.contactId = model.contactId;
                contact.contactNumber= model.contactNumber;
                contact.contactPhoto = model.contactPhoto;
                contact.isDeleted = false;
                contact.save();

                new Delete().from(Deletee.class).where("contactId=?", model.contactId).execute();
                int localPos = (int) v.getTag();
                deleteeList.remove(localPos);
                notifyDataSetChanged();


            new Select().from(Contact.class).execute();
                Toast.makeText(context, "restored!!", Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }



    // View holder to hold views
    private class ViewHodler {
        ImageView contactImage,restore;
        TextView contactName, contactNumber;
    }
}


