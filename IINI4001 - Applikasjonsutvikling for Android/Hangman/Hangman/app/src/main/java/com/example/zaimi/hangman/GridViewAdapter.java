package com.example.zaimi.hangman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    String[] letters;

    public GridViewAdapter(Context context){
        this.context = context;
        setupLetterEng();
    }



    private String[] setupLetterEng(){
        //Looper gjennom alfabetet og ved "no" så har jeg hardkodet inn æøå pga implementering av ascii
        letters = new String[Integer.parseInt(context.getResources().getString(R.string.number_letter))];
        for (int i = 0; i < letters.length; i++) {
            if(i == 26) letters[i] = "Æ";
            else if(i == 27) letters[i] = "Ø";
            else if(i == 28) letters[i] = "Å";
            else letters[i] = "" + (char)('A' + i);
        }
        return letters;
    }

    @Override
    public int getCount() {
        return letters.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //lager en Button som får hver sin bokstav
        Button b;
        if(convertView == null){
            b = (Button) LayoutInflater.from(context).inflate(R.layout.buttons_clicker, parent,false);
        }else {
            b = (Button) convertView;
        }
        b.setText(letters[position]);
        if(b.isPressed()){
            b.setBackgroundColor(51);
        }
        return b;
    }

}
