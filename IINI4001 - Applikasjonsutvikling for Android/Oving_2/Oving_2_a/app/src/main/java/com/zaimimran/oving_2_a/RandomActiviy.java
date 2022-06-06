package com.zaimimran.oving_2_a;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Random;

public class RandomActiviy extends Activity {

    private int grense;
    final Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        grense = getIntent().getIntExtra("Grense",grense);
        int tilfTall = rand.nextInt(grense+1);

        Intent intent = new Intent();
        intent.putExtra("randTall", tilfTall);
        setResult(RESULT_OK, intent);

        //String textTall = Integer.toString(tilfTall);
        //Toast.makeText(getApplicationContext(), textTall , Toast.LENGTH_SHORT).show();

        finish();

    }

}
