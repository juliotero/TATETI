package com.example.juli.tateti;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

public class SegundaActividad extends AppCompatActivity implements OnClickListener {

    Button a1, a2, a3, b1, b2, b3, c1, c2, c3;//creo un boton por cada celda
    Button nuevaPart,salir;
    TextView titulo;
    String jug1, jug2;
    Button[] bArray;//creo un array para luego almacenar los botones
    boolean turno=true;//Defino variable que me diga de quien es el turno. X = true, O = false
    int cont_turno=0,punt1=0,punt2=0;
    final static String ACT_INFO="com.example.juli.tateti.PantallaSalida";
    Button music;
    MediaPlayer mPlayer;


    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //defino content_main como layout de SegundaActividad
        setContentView(R.layout.content_main);
        mPlayer = MediaPlayer.create(this.getApplicationContext(), R.raw.sound);
        music = (Button) findViewById(R.id.music2);
        music.setText("music: on");
        mPlayer.start();
        Intent ant= getIntent();
        String [] datos = ant.getStringArrayExtra(MainActivity.DATOS_JUG);
        titulo = (TextView) findViewById(R.id.titulo);
        jug1=datos[0];
        jug2=datos[1];
        titulo.setText(""+jug1+" vs. "+jug2);
        a1 = (Button) findViewById(R.id.A1);
        a2 = (Button) findViewById(R.id.A2);
        a3 = (Button) findViewById(R.id.A3);
        b1 = (Button) findViewById(R.id.B1);
        b2 = (Button) findViewById(R.id.B2);
        b3 = (Button) findViewById(R.id.B3);
        c1 = (Button) findViewById(R.id.C1);
        c2 = (Button) findViewById(R.id.C2);
        c3 = (Button) findViewById(R.id.C3);
        nuevaPart = (Button) findViewById(R.id.button);
        salir = (Button) findViewById(R.id.salir);
        //guardo los botones en al arreglo
        bArray = new Button[]{a1, a2, a3, b1, b2, b3, c1, c2, c3};

        //defino que la clase SegundaActividad sea el Handler para el evento de cada boton
        for (Button b : bArray) {
            b.setOnClickListener(this);
        }
        salir.setOnClickListener(this);
        nuevaPart.setOnClickListener(this);
        music.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void reiniciarPartida(){
        turno=true;
        cont_turno=0;
        for (Button b : bArray) {
            b.setClickable(true);
            b.setText("");
            b.setBackgroundColor(Color.parseColor("#87fef5"));
        }
    }
    @Override
    public void onClick(View v) {
        //defino lo que ocurre cuando se presiona una celda
        Button b = (Button) v;
        if (b.getId() == R.id.button) {
            reiniciarPartida();
        } else {
            if (b.getId() == R.id.salir) {
                pasarActividad();
            } else {
                if(b.getId() == R.id.music2){
                    if(music.getText()=="music: on") {
                        mPlayer.pause();
                        music.setText("music: off");
                    }else{
                        mPlayer.start();
                        music.setText("music: on");
                    }
                } else {
                    buttonClicked(b);
                }
            }
        }
    }


    public void buttonClicked(Button b) {
        //Como empieza la actividad con turno en true, siempre empieza la X
        if(turno){
            b.setText("X");
            b.setBackgroundColor(Color.GRAY);
        }else{
            b.setText("O");
            b.setBackgroundColor(Color.LTGRAY);
        }
        b.setClickable(false);
        cont_turno++;
        turno=!turno;

        comprobarGanador();
    }

    private void comprobarGanador(){
        boolean gano=false;
        if(cont_turno>=5) {
            //primero miro las lineas horizontales
            if (a1.getText().equals(a2.getText()) && (a2.getText().equals(a3.getText())) && a1.getText()!="") {
                gano = true;
            } else {
                if (b1.getText().equals(b2.getText()) && (b2.getText().equals(b3.getText())) && b1.getText()!="") {
                    gano = true;
                } else {
                    if (c1.getText().equals(c2.getText()) && (c2.getText().equals(c3.getText()))&&c1.getText()!="") {
                        gano = true;
                    }
                }
            }

            //ahora pruebo las verticales
            if (!gano) {
                if (a1.getText() == (b1.getText()) && (b1.getText() == (c1.getText())) && a1.getText()!="") {
                    gano = true;
                } else {
                    if (a2.getText() == (b2.getText()) && (b2.getText() == (c2.getText())) && a2.getText()!="") {
                        gano = true;
                    } else {
                        if (a3.getText() == (b3.getText()) && (b3.getText() == (c3.getText())) && a3.getText()!="") {
                            gano = true;
                        }
                    }
                }
            }

            //ahora pruebo diagonales
            if (!gano) {
                if (a1.getText() == (b2.getText()) && b2.getText() == (c3.getText()) && a1.getText()!="") {
                    gano = true;
                } else {
                    if (a3.getText() == (b2.getText()) && b2.getText() == (c1.getText()) && a3.getText()!="") {
                        gano = true;
                    }
                }
            }

            if(gano){
                if(turno){
                    toast(""+jug1+" gana");
                    punt2=punt2+100;
                }else{
                    toast(""+jug2+" gana");
                    punt1=punt1+100;
                }
                for (Button b : bArray) {
                    b.setClickable(false);
                }
            }
        }





    }

    private void toast(String msj) {
        Toast.makeText(SegundaActividad.this, msj, Toast.LENGTH_SHORT).show();
    }

    public void pasarActividad(){
        Intent act = new Intent(this,PantallaSalida.class);
        int [] info = new int [2];
        info[0]=punt1;
        info[1]=punt2;
        act.putExtra(ACT_INFO,info);
        mPlayer.stop();
        startActivity(act);
        this.finish();
    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.juli.tateti/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.juli.tateti/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
