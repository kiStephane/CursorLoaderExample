package com.fasotec.cursorloaderexample;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import static android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
import static android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER;
import static android.provider.ContactsContract.CommonDataKinds.Phone.PHOTO_URI;

/**
 * Created by stephaneki on 16/02/2017 .
 */
public class ContactAdapter extends BaseAdapter {

    private Cursor cursor;
    private Context context;
    private LayoutInflater inflater;

    public ContactAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View contactView = view;
        Holder holder;
        cursor.moveToPosition(i);

        if (contactView == null) {
            contactView = inflater.inflate(R.layout.contact_view, viewGroup, false);
            holder = new Holder();
            holder.contactNameTv = (TextView) contactView.findViewById(R.id.contactNameTv);
            holder.contactIv = (ImageView) contactView.findViewById(R.id.contactIv);
            holder.contactNumberTv = (TextView) contactView.findViewById(R.id.contactNumberTv);
            contactView.setTag(holder);
        } else {
            holder = (Holder) contactView.getTag();
        }

        holder.contactNumberTv.setText(cursor.getString(cursor.getColumnIndex(NUMBER)));
        holder.contactNameTv.setText(cursor.getString(cursor.getColumnIndex(DISPLAY_NAME)));
        String imageUri = cursor.getString(cursor.getColumnIndex(PHOTO_URI));

        try {
            if (imageUri != null) {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        context.getContentResolver(), Uri.parse(imageUri));
                holder.contactIv.setImageBitmap(bitmap);
            }else{
                holder.contactIv.setImageResource(android.R.drawable.ic_dialog_info);
            }

        } catch (IOException ioe) {
            holder.contactIv.setImageResource(android.R.drawable.ic_dialog_info);
        }

        return contactView;
    }

    private class Holder {
        TextView contactNameTv, contactNumberTv;
        ImageView contactIv;
    }
}
