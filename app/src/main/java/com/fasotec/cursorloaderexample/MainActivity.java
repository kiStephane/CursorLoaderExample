package com.fasotec.cursorloaderexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks {

    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    ListView contactListView;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactListView = (ListView) findViewById(R.id.contactListView);

        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            getSupportLoaderManager().initLoader(1, null, this);
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Uri contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        return new CursorLoader(this, contactUri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Cursor cursor = (Cursor) data;
        cursor.moveToFirst();
        contactAdapter = new ContactAdapter(this, cursor);
        contactListView.setAdapter(contactAdapter);
    }

    /**
     * Called when there is a change in the data source
     */
    @Override
    public void onLoaderReset(Loader loader) {
    }
}
