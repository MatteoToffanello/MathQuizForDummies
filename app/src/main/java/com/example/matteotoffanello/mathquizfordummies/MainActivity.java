package com.example.matteotoffanello.mathquizfordummies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent in = new Intent(this, GameActivity.class);
        final Intent inRec = new Intent(this, RecordActivity.class);

        final Button play = findViewById(R.id.play);
        final Button record = findViewById(R.id.record);
        final EditText user = findViewById(R.id.input_username);
        final SeekBar diff = findViewById(R.id.bar_diff);
        final TextView tv_dif = findViewById(R.id.tv_diff);

        // Al click del bottone salvo username per eventuale activity dove si visualizzano i record dei giocatori e la difficoltà scelta dal giocatore
        play.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                in.putExtra("username", "" + user.getText());
                in.putExtra("diff", "" + diff.getProgress());
                startActivity(in);
            }
        });

        record.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(inRec);
            }
        });

        diff.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch(i){
                    case 0:
                        tv_dif.setText(R.string.diff_facile);
                        break;
                    case 1:
                        tv_dif.setText(R.string.diff_media);
                        break;
                    case 2:
                        tv_dif.setText(R.string.diff_difficile);
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

}
