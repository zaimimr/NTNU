package com.example.zaimi.oving_6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ClientActivity extends AppCompatActivity {

    Client c;

    private static final String IP = "10.0.2.2";
    private static final int PORT = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        c = new Client(IP, PORT);
        c.setResponse((response) -> {
            runOnUiThread(() -> {
                TextView textView = findViewById(R.id.text_answer);
                textView.setText(response);
            });
        });
        c.start();
    }

    public void sendData(View v) {
        EditText numA = findViewById(R.id.input_numA);
        EditText numB = findViewById(R.id.input_numB);

        String a = numA.getText().toString();
        String b = numB.getText().toString();

        c.send(a + " + " + b);
    }
}
