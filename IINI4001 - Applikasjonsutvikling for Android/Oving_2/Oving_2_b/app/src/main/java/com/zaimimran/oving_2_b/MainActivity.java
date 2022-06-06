package com.zaimimran.oving_2_b;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    int request_random = 1;
    int request_random2 = 2;

    private int svar;
    private int grense;
    private int tilfTall;
    private int tall1;
    private int tall2;

    TextView tall1TxtView;
    TextView tall2TxtView;
    EditText svarEdtTxt;
    EditText ogEdtTxt;
    Button gangeBtn;
    Button plussBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gangeBtn = (Button) findViewById(R.id.gangeBtn);
        plussBtn = (Button) findViewById(R.id.plussBtn);
        svarEdtTxt = (EditText) findViewById(R.id.svarEdtTxt);
        ogEdtTxt = (EditText) findViewById(R.id.ogEdtTxt);
        tall1TxtView = (TextView) findViewById(R.id.tall1TxtView);
        tall2TxtView = (TextView) findViewById(R.id.tall2TxtView);

        gangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    reCheck(v);
                    knappTrykk(tall1*tall2, v);
                }catch (Exception e){
                    System.out.print("Feil " + e);
                }
            }
        });

        plussBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    reCheck(v);
                    knappTrykk(tall1+tall2, v);
                }catch (Exception e){
                    System.out.print("Feil " + e);
                }
            }
        });

    }

    public void knappTrykk(int i, View v){

            if( svar == i){
                Toast.makeText(getApplicationContext(), getString(R.string.riktig), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), getString(R.string.feil) + " " + Integer.toString(i), Toast.LENGTH_SHORT).show();
            }
            nyeTall(v);

    }

    public void reCheck(View v){
        tall1 = Integer.parseInt(tall1TxtView.getText().toString());
        tall2 = Integer.parseInt(tall2TxtView.getText().toString());
        svar = Integer.parseInt(svarEdtTxt.getText().toString());
        grense = Integer.parseInt(ogEdtTxt.getText().toString());
    }

    public void nyeTall(View v){
        Intent intent = new Intent(MainActivity.this,RandomActiviy.class);
        intent.putExtra("Grense",grense);
        startActivityForResult(intent, request_random);
        startActivityForResult(intent, request_random2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==request_random && resultCode == RESULT_OK){
            tilfTall = data.getIntExtra("randTall", tilfTall);
            tall1TxtView.setText(Integer.toString(tilfTall));
        }
        if(requestCode==request_random2 && resultCode == RESULT_OK){
            tilfTall = data.getIntExtra("randTall", tilfTall);
            tall2TxtView.setText(Integer.toString(tilfTall));
        }
    }
}
