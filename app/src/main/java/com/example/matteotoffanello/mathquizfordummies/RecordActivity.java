package com.example.matteotoffanello.mathquizfordummies;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        TextView tv = findViewById(R.id.recordView);
        int i = 0;

        SharedPreferences prefs = getSharedPreferences("prova1", MODE_PRIVATE);

        while(!prefs.getString("user"+i, "null").equals("null")){
            String user = prefs.getString("user"+i, "");
            String diff = prefs.getString("diff"+i, "");
            String punt = prefs.getString("punteggio"+i, "");
            String s = "\n" + user + " - " + diff + " - " + punt;
            tv.setText(tv.getText() + s);
            i++;
        }


    }
}
