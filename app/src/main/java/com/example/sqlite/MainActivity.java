package com.example.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SqliteAdapter sqliteAdapter;
    EditText first, last, search, newf, newl;
    String searchitem, firstnew, lastnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        first = findViewById(R.id.et_first);
        last = findViewById(R.id.et_last);
        search = findViewById(R.id.et_search);
        newf = findViewById(R.id.et_newfirst);
        //newl = findViewById(R.id.et_newlast);

        sqliteAdapter = new SqliteAdapter(this);
    }

    public void adduser(View view) {
        String fname = first.getText().toString();
        String lname = last.getText().toString();
        long id = sqliteAdapter.insert(fname, lname);
        if (id < 0) {
            Toast.makeText(this, "unsuccessful", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "successful", Toast.LENGTH_LONG).show();
        }
    }

    public void viewdetails(View view) {
        String data = sqliteAdapter.getalldata();
        Toast.makeText(this, "" + data, Toast.LENGTH_LONG).show();
    }

    public void search(View view) {
        searchitem = search.getText().toString();
        String s2 = sqliteAdapter.search(searchitem);
        Toast.makeText(this, "" + s2, Toast.LENGTH_LONG).show();
    }

    public void update(View view) {
        firstnew = newf.getText().toString();
//        lastnew=newl.getText().toString();
        String fname = first.getText().toString();
        //String lname = last.getText().toString();
        sqliteAdapter.updatename(fname,firstnew);
    }

    public void delete(View view) {
        firstnew = newf.getText().toString();
        int count = sqliteAdapter.deleterow(firstnew);
        Toast.makeText(this, "" + count, Toast.LENGTH_LONG).show();
    }
}
