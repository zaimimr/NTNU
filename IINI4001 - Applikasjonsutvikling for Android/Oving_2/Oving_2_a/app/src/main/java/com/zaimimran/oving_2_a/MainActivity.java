package com.zaimimran.oving_2_a;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
    private int grense = 100;
    int request_random = 1;
    private int tilfTall;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtView = (TextView) findViewById(R.id.txtView);
        Button toastBtn = (Button) findViewById(R.id.toastBtn);
        toastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RandomActiviy.class);
                intent.putExtra("Grense",grense);
                startActivityForResult(intent, request_random);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==request_random && resultCode == RESULT_OK){
            tilfTall = data.getIntExtra("randTall", tilfTall);
            txtView.setText(Integer.toString(tilfTall));


        }
    }


}
