package com.example.zaimi.ov_07;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity {

    DatabaseManager db;
    SharedPreferences perf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set background Color
        perf = getDefaultSharedPreferences(this);
        setColor();

        try {
            setupFile();
            dataBase();
            ArrayList<String> res = db.getAllBooks();
            System.out.println("dette er her:");

            showResults(res);
        }catch (Exception e) {
            e.printStackTrace();
        }

        Button bookBtn = findViewById(R.id.bookBtn);
        Button autBtn = findViewById(R.id.autBtn);
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> res = db.getAllBooks();
                showResults(res);
            }
        });
        autBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> res = db.getAllAuthors();
                showResults(res);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setColor();
    }

    public void setupFile() {
        String s1 = "Zaim Imran, The Well";
        String s2 = "Simen Watne, The Welling";
        String s3 = "Zaim Imran, The Hell";
        String s4 = "Zaim Imran, The Selling";
        String s5 = "Audun Karlsen, If and only If";
        String[] strings = {s1, s2, s3, s4, s5};

        String hel = "";
        for (String s : strings) {
            hel += s + ", ";
        }
        fileManager.writeToFile(getFilesDir(), "text.txt", hel);
    }

    public String[]fromFile(){
        String fil = fileManager.readFile(getFilesDir(), "text.txt");
        try {
            String[] forkort = fil.split("[,\\:]");
            return forkort;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void dataBase(){
        String[] fil = fromFile();
        try {
            db = new DatabaseManager(this);
            db.clean();
            for (int i = 1; i < fil.length; i += 2) {
                db.insert(fil[i-1].trim(),fil[i].trim());
            }
        } catch (Exception e) {
        }
    }

    public void showResults(ArrayList<String> list) {
        StringBuffer res = new StringBuffer("");
        for (String s : list)  {
            res.append(s+"\n");
        }
        TextView t = (TextView)findViewById(R.id.tw1);
        t.setText(res);
    }

    private void setColor() {
        RelativeLayout t = findViewById(R.id.background);
        String value = perf.getString("color", "#FFFFFF");
        Log.d("COLOR", value);
        t.setBackgroundColor(Color.parseColor(value));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            // launch settings activity
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
