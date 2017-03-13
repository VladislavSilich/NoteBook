package com.example.lenovo.myfinalproject;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class ContactInformation extends Activity {

    EditText nameInf, surnameInf, telInf, mailInf, adressInf;
    DBhelper dBhelper;
    ImageButton btnImage, btnMail, btnMaps;
    Button delete, update;
    SQLController controller;
    LocationManager locationManager;
    Geocoder geocoder;

    final int DILOG_DELETE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_information);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        final Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        geocoder = new Geocoder(getApplicationContext());


        nameInf = (EditText) findViewById(R.id.edtNameInf);
        surnameInf = (EditText) findViewById(R.id.edtSurnameInf);
        telInf = (EditText) findViewById(R.id.edtTelInf);
        mailInf = (EditText) findViewById(R.id.edtEmailInf);
        btnImage = (ImageButton) findViewById(R.id.imagebtn);
        delete = (Button) findViewById(R.id.btnDelete);
        update = (Button) findViewById(R.id.btnUpdate);
        btnMail = (ImageButton) findViewById(R.id.imagemail);
        adressInf = (EditText) findViewById(R.id.edtWork);
        btnMaps = (ImageButton) findViewById(R.id.imageMaps);

        dBhelper = new DBhelper(this);
        //SQLiteDatabase database = dBhelper.getWritableDatabase();

        final Intent i = getIntent();
        final String memberId = i.getStringExtra("id");
        SQLiteDatabase database = dBhelper.getWritableDatabase();
        // int id = Integer.parseInt(memberId);
        String selection = "_id = ?";
        String[] column = new String[]{DBhelper.MEMBER_NAME, DBhelper.MEMBER_SURNAME, DBhelper.MEMBER_TEL, DBhelper.MEMBER_MAIL, DBhelper.MEMBER_ADRESS};
        Cursor c = database.query(DBhelper.TABLE_MEMBER, column, selection, new String[]{memberId}, null, null, null);
        c.moveToFirst();
        //int a= c.getInt(c.getColumnIndex(DBhelper.MEMBER_ID));
        String name = c.getString(c.getColumnIndex(DBhelper.MEMBER_NAME));
        nameInf.setText(name);
        String surname = c.getString(c.getColumnIndex(DBhelper.MEMBER_SURNAME));
        surnameInf.setText(surname);
        String telephone = c.getString(c.getColumnIndex(DBhelper.MEMBER_TEL));
        telInf.setText(telephone);
        String mail = c.getString(c.getColumnIndex(DBhelper.MEMBER_MAIL));
        mailInf.setText(mail);
        String adress = c.getString(c.getColumnIndex(DBhelper.MEMBER_ADRESS));
        adressInf.setText(adress);


        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = telInf.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + tel));
                startActivity(intent);
            }
        });

        controller = new SQLController(this);
        controller.open();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DILOG_DELETE);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long upd = Long.parseLong(memberId);
                controller.updateData(upd, nameInf.getText().toString(),
                        surnameInf.getText().toString(), telInf.getText().toString(), mailInf.getText().toString(), adressInf.getText().toString());
                returnHome();
            }
        });

        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mailInf.getText().toString();
                Intent intent = new Intent(getApplicationContext(), MailActivity.class);
                intent.putExtra("email", mail);
                startActivity(intent);
            }
        });

        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (enabled == true) {
                    String addressWork = adressInf.getText().toString();
                    try {
                        List<Address> list = geocoder.getFromLocationName(addressWork, 5);
                        int size = list.size();
                        if (size == 0) {
                            Toast.makeText(ContactInformation.this, "No results.Enter new place.", Toast.LENGTH_LONG).show();
                        } else {
                            double latitude = list.get(0).getLatitude();
                            double longitude = list.get(0).getLongitude();

                            Uri uri = Uri.parse(String.format("geo:%f,%f", latitude, longitude));
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(ContactInformation.this,"Please,enable GPS.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder dilog = new AlertDialog.Builder(this);
        dilog.setTitle("Delete contact");
        dilog.setMessage("Do you want delete this contact?");
        dilog.setIcon(android.R.drawable.ic_menu_delete);
        dilog.setNegativeButton("No", myClickListener);
        dilog.setPositiveButton("Yes", myClickListener);
        return dilog.create();
    }


    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    Intent i = getIntent();
                    String memberId = i.getStringExtra("id");
                    long id = Long.parseLong(memberId);
                    controller.deleteData(id);
                    returnHome();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.cancel();
            }

        }
    };
    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(),
                ListContactsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }


}
