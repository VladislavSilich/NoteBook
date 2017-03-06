package com.example.lenovo.myfinalproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ListContactsActivity extends AppCompatActivity {

    String [] data = new String[]{"Contacts"};
    Button btnAddContact;
    SQLController dbcon;
    ListView list;
    EditText edtSearch;
    //TextView member_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contacts);



        dbcon = new SQLController(this);
        dbcon.open();

        list = (ListView) findViewById(R.id.list);
        btnAddContact = (Button) findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListContactsActivity.this, NewContactActivity.class);
                startActivity(intent);
            }
        });

        Cursor cursor = dbcon.readData();
        adapter(cursor);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                long idT = id;
                String idS = String.valueOf(idT);
                Intent intent = new Intent(getApplicationContext(), ContactInformation.class);
                intent.putExtra("id", idS);
                startActivity(intent);
            }
        });
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = edtSearch.getText().toString();
                if (name.isEmpty()) {
                    Cursor cursor = dbcon.readData();
                    adapter(cursor);
                    //Toast.makeText(ListContactsActivity.this,"Enter your name",Toast.LENGTH_LONG).show();
                } else {
                    Cursor cursor = dbcon.readDataSearch(name);
                    adapter(cursor);
                }
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void adapter(Cursor cursor) {
        String[] from = new String[]{DBhelper.MEMBER_ID, DBhelper.MEMBER_NAME, DBhelper.MEMBER_SURNAME};
        int[] to = new int[]{R.id.member_id, R.id.member_name, R.id.member_surname};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                ListContactsActivity.this, R.layout.view_member_entry,
                cursor, from, to);

        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

    }


}
