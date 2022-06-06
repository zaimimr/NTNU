package com.zaimimran.oving_1_meny;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView menuTextView = (TextView) findViewById(R.id.menuTextView);
        switch (item.getItemId()) {
            case R.id.forNavn:
                menuTextView.setText("Zaim");
                Log.i("fornavn", "Zaim er mitt fornavn");
                return true;
            case R.id.etterNavn:
                menuTextView.setText("Imran");
                Log.i("etternavn","Imran er mitt etternavn");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
