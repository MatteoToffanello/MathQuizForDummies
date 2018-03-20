package com.example.matteotoffanello.mathquizfordummies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.AlphabeticIndex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.*;

public class GameActivity extends AppCompatActivity {

    private Random r;
    private Bundle extra;

    private int numCal = 15;
    private int numEsatte;
    private int index;
    private int difficulty;
    private int[] prob;
    private ArrayList<String> calc;
    private ArrayList<Integer> res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        calc = new ArrayList<String>();
        res = new ArrayList<Integer>();
        prob = new int[10];
        numEsatte = 0;

        r = new Random();
        index = 0;

        // chiave "username" e "diff"
        extra = getIntent().getExtras();

        final TextView op1 = (TextView)findViewById(R.id.op1);
        final TextView op2 = (TextView)findViewById(R.id.op2);
        final TextView op3 = (TextView)findViewById(R.id.op3);
        final EditText num = findViewById(R.id.res);
        final Button invia = findViewById(R.id.invia);

        invia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num.getText().toString().equals(res.get(index).toString())){
                    numEsatte++;
                }else{
                }
                if(index == 14){
                    SharedPreferences prefs = getSharedPreferences("prova1", MODE_PRIVATE);
                    SharedPreferences.Editor editor = getSharedPreferences("prova1", MODE_PRIVATE).edit();
                    int j = 0;
                    while(!prefs.getString("user"+j, "null").equals("null")){
                        j++;
                    }
                    editor.putString("user"+j, extra.getString("username"));
                    editor.putString("diff"+j, extra.getString("diff"));
                    editor.putString("punteggio"+j, numEsatte+"/"+numCal);
                    editor.apply();
                    editor.commit();
                    finish();
                }else {
                    index++;
                    op1.setText(calc.get(index - 1) + " = " + res.get(index - 1));
                    op2.setText(calc.get(index));
                    if (index != numCal - 1) {
                        op3.setText(calc.get(index + 1));
                    } else {
                        op3.setText("");
                    }
                    num.setText("");
                }
            }
        });


        /*

            1.  - trovare tutte le 15(?) operazioni da far fare con risultati
            2.  - visualizzare operazioni con aiuto di eventuali indici per tenere il segno
                - se il risultato inserito dall'utente è corretto, quindi uguale a quello salvato nel vettore
                    spostare di una textview in alto le operazioni, la textview sopra ha scritto anche il risultato
                  altrimenti
                    restare fermi
                (1textview -> operazione già presentata e completata; 2textview -> operazione che sta eseguendo l'utente; 3textview -> prossima operazione in lista)
            3.  - mettere un timer per contare i secondi/minuti per fare i record

         */

        difficulty = Integer.parseInt(extra.get("diff").toString());
        Log.w("DIFF", difficulty + "");

        // riempio vettore delle probabilità
        if(difficulty==0){
            fillVetProb(7);
        }else if(difficulty==1){
            fillVetProb(5);
        }else if(difficulty==2) {
            fillVetProb(3);
        }

        // per numCalc volte prendo una delle operazioni dal vettore prob
        // e aggiungo al vettore dei calcoli da fare le operazioni
        for(int i = 0; i < numCal; i++){
            int p = r.nextInt(10);
            //Log.i("EIWE", ""+prob[p]);
            if(prob[p] == 0){
                // addizione/sottrazione
                calc.add(getOpAddSub());
            }else if(prob[p] == 1){
                // moltiplicazione/divisione
                calc.add(getOpMultDiv());
            }
            // imposto le operazioni complete e i risultato nei vettori: calc e res
            getCalcolo(i);
        }

        op1.setText(calc.get(index));
        op2.setText(calc.get(index));
        op3.setText(calc.get(index+1));

        num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // verificare se il risultato è giusto
                //op1.setText(charSequence);
                /*if(charSequence.toString().equals(res.get(index).toString())){
                    if(index == 14){
                        //fine timer. Apri altra activity. Partita finita

                    }else {
                        index++;
                        op1.setText(calc.get(index - 1) + " = " + res.get(index - 1));
                        op2.setText(calc.get(index));
                        if (index != numCal - 1) {
                            op3.setText(calc.get(index + 1));
                        } else {
                            op3.setText("");
                        }
                        num.setText("");
                    }
                }
                */

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent in = new Intent(this, RecordActivity.class);
        startActivity(in);
    }

    private void fillVetProb(int n1){
        /*
            0 = addizione/sottrazione
            1 = moltiplicazione/divisione
        */

        for(int i = 0; i < 10; i++){
            if(i < n1){
                prob[i] = 0;
            }else{
                prob[i] = 1;
            }
            Log.w("PROBABILITA", "" + prob[i]);
        }

    }

    private void getCalcolo(int i){

        String o = calc.get(i);

        int n1 = 0, n2 = 0;

        String ca = "";
        int ris = 0;

        if(o.equals("+")){
            n1 = r.nextInt(15) + (r.nextInt(10) * difficulty+1);
            n2 = r.nextInt(15) + (r.nextInt(10) * difficulty+1);

            ris = n1+n2;
        }else if(o.equals("-")){
            n1 = 1;
            n2 = 0;
            while(n1<n2 && difficulty==0){
                n1 = r.nextInt(17) + (difficulty * (r.nextInt(r.nextInt(6)) - r.nextInt(r.nextInt(11))));
                n2 = r.nextInt(13) + (difficulty * (r.nextInt(r.nextInt(6)) - r.nextInt(r.nextInt(11))));
            }
            ris = n1-n2;
        }else if(o.equals("x")){
            n1 = r.nextInt(15) + (difficulty * -1);
            n2 = r.nextInt(15) + (difficulty * -1);
            ris = n1 * n2;
        }else if(o.equals(":")){
            ris = r.nextInt(10);
            n2 = r.nextInt(15);
            n1 = n2 * ris;
            if(n1 == 0 && n2 == 0){
                ris = 0;
            }
        }
        ca = n1 + " " + o + " " + n2;
        calc.add(i, ca);
        res.add(ris);
    }

    private String getOpAddSub(){
        float prob2 = r.nextFloat();
        if(prob2 < 0.5){
            // addizione
            return "+";
        }else{
            //sottrazione
            return "-";
        }
    }

    private String getOpMultDiv(){
        float prob2 = r.nextFloat();
        if(prob2 < 0.5){
            // moltiplicazione
            return "x";
        }else{
            // divisione
            return ":";
        }
    }

}
