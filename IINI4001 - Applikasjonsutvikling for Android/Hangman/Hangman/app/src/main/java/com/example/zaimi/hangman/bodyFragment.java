package com.example.zaimi.hangman;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class bodyFragment extends Fragment {
    private long id;
    private ImageView imageView;
    public void setId(long id) {
        this.id = id;
    }


    public bodyFragment(){
     }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null){
            id = savedInstanceState.getLong("id");
        }

        return inflater.inflate(R.layout.fragment_body, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View v = getView();
        if (v != null ) {
            imageView = v.findViewById(R.id.bodyIW);
            imageView.setImageResource(R.drawable.hangman0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        View v = getView();
        if (v != null){
            switch ((int)id){
                case 0:
                    imageView.setImageResource(R.drawable.hangman0);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.hangman1);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.hangman2);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.hangman3);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.hangman4);
                    break;
                case 5:
                    imageView.setImageResource(R.drawable.hangman5);
                    break;
                case 6:
                    imageView.setImageResource(R.drawable.hangman6);
                    break;
                default:
                    imageView.setImageResource(R.drawable.hangman6);
            }
        }
    }
}
