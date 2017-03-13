package com.example.lenovo.myfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnExit,btnOpen;
    Animation anim, animBtn;

    TextView txtNote;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnExit = (Button)findViewById(R.id.btnExit);
        btnOpen = (Button)findViewById(R.id.btnOpen);
        txtNote = (TextView)findViewById(R.id.textView2);
        imageView = (ImageView) findViewById(R.id.imageView3);

        anim = AnimationUtils.loadAnimation(this,R.anim.myscale);
        txtNote.startAnimation(anim);
        imageView.startAnimation(anim);
        btnOpen.startAnimation(anim);
        btnExit.startAnimation(anim);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animBtn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.myrotate);
                imageView.startAnimation(animBtn);
                Intent intent = new Intent(MainActivity.this,ListContactsActivity.class);
                startActivity(intent);
            }
        });
    }
}
