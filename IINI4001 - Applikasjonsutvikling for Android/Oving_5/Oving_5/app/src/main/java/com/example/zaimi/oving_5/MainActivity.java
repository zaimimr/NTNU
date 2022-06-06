package com.example.zaimi.oving_5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static Http http = new Http("http://tomcat.stud.iie.ntnu.no/studtomas/tallspill.jsp");
    private static final int max_gjett = 3;
    private int guesses = 0;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeQuiz();
    }

    private void initializeQuiz() {
        Intent intent = getIntent();
        String question = intent.getStringExtra(Requests.REGISTER_DATA);
        name = intent.getStringExtra(Requests.USER_NAME);
        setQuestion(question);
        guesses = 0;

        // Guess button
        Button gjettBtn = findViewById(R.id.gjettBtn);
        gjettBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGuess(v);
            }
        }));

        // Reset button
        Button rstBtn = findViewById(R.id.rstBtn);
        rstBtn.setOnClickListener((v) -> {
            Intent in = new Intent(this, Registration.class);
            startActivity(in);
            finish();
        });
    }

    private void sendGuess(View v) {
        TextView input = findViewById(R.id.input);
        String gjett = input.getText().toString();

        guessNumber(gjett, (response) -> {
            handleResponse(v, response);
        });
    }


    private void handleResponse(View v, String response) {
        guesses++;

        boolean status = validateResponse(response);
        if(status) {
            setVictoryText();
        }
        else if(guesses == max_gjett) {
        }
        else {
        }
        setQuestion(response);
    }

    private void guessNumber(String number, Http.Callback callback) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("tall", number);
        http.runHttpRequestInThread(Http.HttpRequestType.HTTP_GET, parameters, callback);
    }

    private void setQuestion(String q) {
        TextView hjelpText = findViewById(R.id.hjelpText);
        hjelpText.setText(q);
    }

    // Was the guess correct?...
    private boolean validateResponse(String response) {
        return response.startsWith(name);
    }


    private void setVictoryText () {
        TextView text = findViewById(R.id.textView);
        text.setTextColor(0xff4cc462);
        text.setText("Du vant!!");
    }
}

