package com.example.juli.tateti;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText e1,e2;
    Button music;
    Button enviar;
    final static String DATOS_JUG="com.example.juli.tateti.SegundaActividad";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        e1 = (EditText) findViewById(R.id.editText);
        e2 = (EditText) findViewById(R.id.editText2);
        enviar = (Button) findViewById(R.id.enviar);
        enviar.setOnClickListener(this);
    }


    public void onClick(View v) {
        Button b = (Button) v;
            pasarActividad();
    }

    public void pasarActividad(){
        Intent act = new Intent(this,SegundaActividad.class);
        String [] datos = new String [2];
        datos[0]= String.valueOf(e1.getText());
        datos[1]= String.valueOf(e2.getText());
        act.putExtra(DATOS_JUG,datos);
        startActivity(act);
        this.finish();
    }

}
