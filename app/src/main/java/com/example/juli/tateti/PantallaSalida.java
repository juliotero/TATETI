package com.example.juli.tateti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PantallaSalida extends AppCompatActivity implements View.OnClickListener {
    private TextView score1,score2;
    Button salir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_salida);
        score1=(TextView) findViewById(R.id.s1);
        score2=(TextView) findViewById(R.id.s2);
        salir = (Button) findViewById(R.id.button2);
        salir.setOnClickListener(this);
        Intent ant= getIntent();
        int [] info = ant.getIntArrayExtra(SegundaActividad.ACT_INFO);
        score1.setText(""+info[0]+"");
        score2.setText(""+info[1]+"");

    }

    @Override
    public void onClick(View v) {
        this.restart();
    }

    public void restart(){
        Intent act = new Intent(this,MainActivity.class);
        startActivity(act);
        this.finish();
    }
}
