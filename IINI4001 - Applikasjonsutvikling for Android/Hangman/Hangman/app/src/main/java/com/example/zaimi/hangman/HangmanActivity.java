package com.example.zaimi.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class HangmanActivity extends AppCompatActivity{

    private TextView guess;
    private TextView title;
    private TextView feil;
    private TextView ordTot;
    private TextView ordSpt;
    private TextView rkt;
    private TextView fel;

    private String word;
    private List<String> words;
    private StringBuilder display;

    private GridView gw;

    private Button nextBtn;
    private Button finishBtn;

    private bodyFragment body;

    private int counterMiss;
    private int counterHit;
    private int counterRkt;
    private int counterFel;

    private Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);

        //Initialisering

        body = new bodyFragment();
        words = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.ord)));

        nextBtn = findViewById(R.id.nextBtn);
        finishBtn = findViewById(R.id.ferdigBtn);
        title = findViewById(R.id.header);
        guess = findViewById(R.id.ordTW);
        feil = findViewById(R.id.feilTW);
        ordTot = findViewById(R.id.ordTot);
        ordSpt = findViewById(R.id.ordSpl);
        rkt = findViewById(R.id.rkt);
        fel = findViewById(R.id.fel);
        gw = findViewById(R.id.gw);

        counterFel = 0;
        counterRkt = 0;

        //Start en runde hangman
        startGame();

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Hører om knappene blir trykket
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        finishBtn.setOnClickListener(v -> {
            exit();
        });
    }

    private void startGame(){

        //Nullstiller for et nytt ord
        scoreBoard();

        //lager bokstav knappene
        if(!(words.size() == 0)) gw.setAdapter(new GridViewAdapter(this));

        //Knapper som vises for neste ord / tomt for ord
        nextBtn.setVisibility(View.GONE);
        finishBtn.setVisibility(View.GONE);

        feil.setText("");
        title.setText(getString(R.string.app_name));

        counterMiss = 0;
        counterHit = 0;

        //Oppdatterer bilde til 0 og velger ut ord
        updateImage();
        wordPicker();
    }



    public void buttonPressed(View v){

        //Sjekker om knapp er trykket, og gjør at den ikke kan tryppes igjen
        String button = ((TextView)v).getText().toString();
        v.setBackgroundColor(0);
        v.setEnabled(false);

        //Henter bokstaven fra knappen og sjekker gyldighet
        char letter = button.charAt(0);
        checkValidity(letter);
        checker();
    }

    private void checkValidity(char x){

        //Henter bokstaven og ser om den er en del av ordet. hvis ja, endre display så bokstaven blir vist på sin plass

        display = new StringBuilder(guess.getText().toString());
        Boolean miss = true;
        for(int i = 0; i < word.length(); i++){
            if(x == word.charAt(i)) {
                display.replace(2 * i, 2 * i + 1, x + "");
                guess.setText(display);
                miss = false;
                counterHit++;
            }
        }
        //hvis ikke, legg til ordet til feil-TextView og tegn på hangman
        if(miss){
            feil.setText(feil.getText().toString() + x + " ");
            counterMiss++;
            updateImage();
        }
    }

    private void updateImage(){
        //Bruker fragment til å oppdatere bildet etter hver feil
        body = new bodyFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        body.setId((long)counterMiss);
        ft.replace(R.id.fragment,body);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    private void checker(){
        //Ikke gjettet ordet, disabler knappene og viser nesteknappen.
        if(counterMiss == 6){
            nextBtn.setVisibility(View.VISIBLE);
            title.setText(getString(R.string.lose));
            disableButton();
            for (int i = 0; i < word.length(); i++) {
                display.replace(2 * i, 2 * i + 1, word.charAt(i) + "");
                guess.setText(display);
            }
            counterFel++;
        //Gjettet ordet, disabler knappene og viser nesteknappen.
        }else if(counterHit == word.length()){
            nextBtn.setVisibility(View.VISIBLE);
            title.setText(getString(R.string.win));
            disableButton();
            counterRkt++;
        }

    }

    private void wordPicker(){
        //Sjekker om det er flere ord å gjette
        if(words.size() == 0){
            //Avslutter
            finishBtn.setVisibility(View.VISIBLE);
            title.setText(getString(R.string.ferdig));
            word = " ";
            guess.setText(word);
        }else {
            //velger en sudo random ord og fjerner ordet fra listen slik at den ikke kommer flere ganger
            int x = rand.nextInt(words.size());
            word = words.get(x);
            words.remove(x);
            //displayer ordet med "_ " slik at den viser antall ord
            words.toString();

            String display = "";
            for (int i = 0; i < word.length(); i++) {
                display += "_ ";
            }
            guess.setText(display);
        }
    }

    public void scoreBoard(){
        //bruker størresle på List og counters for å holde styr på antall riktig/feil osv
        ordTot.setText(Integer.toString(words.size()));
        ordSpt.setText(Integer.toString(counterRkt + counterFel));
        rkt.setText(Integer.toString(counterRkt));
        fel.setText(Integer.toString(counterFel));

    }

    private void disableButton(){
        //looper gjennom alle tallene og diabler de
        for (int i = 0; i < gw.getChildCount(); i++){
            gw.getChildAt(i).setEnabled(false);
        }
    }
    //start et nytt game
    private void reset(){
        startGame();
    }

    //avslutt og gå til MainActivity
    private void exit(){
        Intent intent = new Intent(HangmanActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
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

    //Lager en meny

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.info) {
            //Viser popupVindu
            popupShow(findViewById(R.id.layoutHang));
            return true;
        }else if (id == R.id.exit){
            //Avslutter til Main
            exit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
