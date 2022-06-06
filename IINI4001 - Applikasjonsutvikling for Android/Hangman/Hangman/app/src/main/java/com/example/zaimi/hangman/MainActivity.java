package com.example.zaimi.hangman;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startBtn = findViewById(R.id.startBtn);
        //Ved trykk start Hangman spillet
        startBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HangmanActivity.class);
            startActivity(intent);
            finish();
        });

        Button infoBtn = findViewById(R.id.infoBtn);
        //Ved trykk vil hvordan man spiller spillet og avslutter activity;
        infoBtn.setOnClickListener(v -> {
            popupShow(v);
        });
    }

    public void popupShow(View v){
        //bruker inflater for å vise popup vindu
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popup = inflater.inflate(R.layout.popup_window, null);

        //lager et nytt popup med mulighet for å trykke utenfor for å lukke
        int wrap = LinearLayout.LayoutParams.WRAP_CONTENT;
        PopupWindow popWindow = new PopupWindow(popup, wrap, wrap, true);

        //Setter posisjon midt i skjermen
        popWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        //Avslutt popup ved trykk på vindu
        popup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popWindow.dismiss();
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.info) {
            //Vise popUp
            popupShow(findViewById(R.id.layout));
            return true;
        }else if (id == R.id.exit){
            //Avslutter alle oppgavene og avslutter systemet
            finishAndRemoveTask();
        }
        return super.onOptionsItemSelected(item);
    }
}
