package com.example.lenovo.myfinalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewContactActivity extends AppCompatActivity {

    Button btnAddNewContact;
    EditText edtName ,edtTelephone,edtSurname,edtEmail,edtAdress;
    SQLController dbcon;
    DBhelper dBhelper;
    String selection = null;
    private final int DIALOG_SHOW = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        btnAddNewContact = (Button)findViewById(R.id.btnAddNewContact);
        edtName = (EditText)findViewById(R.id.edtName);
        edtTelephone = (EditText)findViewById(R.id.edtTel);
        edtSurname = (EditText)findViewById(R.id.edtSurname);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtAdress = (EditText)findViewById(R.id.edtWork);



        dbcon = new SQLController(this);
        dbcon.open();
        dBhelper = new DBhelper(this);


        btnAddNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            String name = edtName.getText().toString();
                String surname = edtSurname.getText().toString();
                String tel = edtTelephone.getText().toString();
                String email = edtEmail.getText().toString();
                String adress = edtAdress.getText().toString();
                if(name.isEmpty() && surname.isEmpty() && tel.isEmpty() && email.isEmpty() && adress.isEmpty()){
                    showDialog(DIALOG_SHOW);
                }else {
                    dbcon.insertData(name, surname, tel, email,adress);
                    Intent main = new Intent(NewContactActivity.this, ListContactsActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(main);
                }
            }
        });



    }

        protected Dialog onCreateDialog (int id){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Warning");
            dialog.setMessage("Please, fill at least one field");
            dialog.setIcon(android.R.drawable.stat_sys_warning);
            dialog.setPositiveButton("Ok",myClickListener);
            return dialog.create();
        }

    Dialog.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case Dialog.BUTTON_POSITIVE :
                    dialog.cancel();
            }
        }
    };
}
