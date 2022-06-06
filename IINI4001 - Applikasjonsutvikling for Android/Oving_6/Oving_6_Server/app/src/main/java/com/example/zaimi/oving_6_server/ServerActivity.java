package com.example.zaimi.oving_6_server;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ServerActivity extends AppCompatActivity {

    Server s;

    private static final int PORT = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_acticity);
        s = new Server("SERVER", PORT);
        s.setOnRecieve((request) -> {
            runOnUiThread(() -> {
                TextView textView = findViewById(R.id.text_log);
                textView.append("\nRecieved: " + request);
            });
        });
        s.start();
    }
}
