package com.example.lenovo.myfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MailActivity extends AppCompatActivity {

    EditText edtTo,edtTheme,edtMess;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        edtTo = (EditText)findViewById(R.id.editTextTo);
        edtTheme = (EditText)findViewById(R.id.editTextSubject);
        edtMess = (EditText)findViewById(R.id.editTextMessage);
        btnSend = (Button)findViewById(R.id.buttonSend);
        Intent i = getIntent();
        String mail = i.getStringExtra("email");
        edtTo.setText(mail);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = edtTo.getText().toString();
                String subject = edtTheme.getText().toString();
                String message = edtMess.getText().toString();
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                email.putExtra(Intent.EXTRA_SUBJECT,subject);
                email.putExtra(Intent.EXTRA_TEXT, message);
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });

    }

}
