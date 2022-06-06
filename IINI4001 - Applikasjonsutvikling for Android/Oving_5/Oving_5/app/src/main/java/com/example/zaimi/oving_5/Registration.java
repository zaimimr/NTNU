package com.example.zaimi.oving_5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private static Http http = new Http("http://tomcat.stud.iie.ntnu.no/studtomas/tallspill.jsp");
    private static EditText inputNavn;
    private static EditText inputKreditt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        inputNavn = findViewById(R.id.navn_input);
        inputKreditt = findViewById(R.id.kreditt_input);

        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(inputNavn.getText().toString(), inputKreditt.getText().toString());
            }
        });
    }

    public void start(String navn, String kreditt){
        registerPlayer(navn, kreditt, (response) -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(Requests.REGISTER_DATA, response);
            intent.putExtra(Requests.USER_NAME, navn);
            startActivity(intent);
            finish();
        });

    }

    public static void registerPlayer(String name, String kreditt, Http.Callback callback) {
        Map<String, String> inn = new HashMap<>();
        inn.put("navn",name);
        inn.put("kortnummer", kreditt);
        Log.d("API","Starting registration of {" + name + "} with {" + kreditt+ "}");
        http.runHttpRequestInThread(Http.HttpRequestType.HTTP_GET, inn, callback);
    }
}
