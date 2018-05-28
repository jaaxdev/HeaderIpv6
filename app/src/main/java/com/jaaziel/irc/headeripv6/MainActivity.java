package com.jaaziel.irc.headeripv6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Spinner opciones;
    String[] opc = {"Best Effort (Default)","High Priority (EF)","AF11","AF12","AF13","AF21","AF22","AF23","AF31","AF32","AF33","AF41","AF42","AF43",
                    "CS1","CS2","CS3","CS4","CS5","CS6","CS7"};
    EditText t_flujo, c_util, siguienteCabecera, l_saltos, d_fuente, d_destino, datoss;
    String ds = "";
    Button cabecera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),"App desarrollada por: \nJaaziel Isai Rebollar Calzada", Toast.LENGTH_LONG).show();

        opciones = (Spinner) findViewById(R.id.spinner);
        t_flujo = (EditText) findViewById(R.id.tag_flujo);
        c_util = (EditText) findViewById(R.id.carga_util);
        siguienteCabecera = (EditText) findViewById(R.id.sig_cabecera);
        l_saltos = (EditText) findViewById(R.id.lim_saltos);
        d_fuente = (EditText) findViewById(R.id.dir_fuente);
        d_destino = (EditText) findViewById(R.id.dir_destino);
        datoss = (EditText) findViewById(R.id.datos);
        cabecera = (Button) findViewById(R.id.boton);

        datoss.requestFocus();

        d_fuente.setText(ran());
        d_destino.setText(ran());
        t_flujo.setText(ranBin(20));
        siguienteCabecera.setText(ranBin(8));
        l_saltos.setText(ranBin(8));
        seleccionaDS();
        cabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( verificaCampos() ){
                    Intent intent = new Intent(getApplicationContext(), Encabezado.class);

                    if(datoss.getText().length()==0){
                        intent.putExtra("carga_util","0000000000000000");
                    } else {
                        intent.putExtra("carga_util",toBinario(datoss.getText().length()*8));
                        //Toast.makeText(getApplicationContext(), toBinario(datoss.getText().length()*8), Toast.LENGTH_SHORT).show();
                    }

                    intent.putExtra("DS",ds);
                    intent.putExtra("etiqueta",t_flujo.getText().toString());
                    intent.putExtra("header", siguienteCabecera.getText().toString());
                    intent.putExtra("lim_saltos",l_saltos.getText().toString());
                    intent.putExtra("dir_fuente",d_fuente.getText().toString());
                    intent.putExtra("dir_destino",d_destino.getText().toString());
                    intent.putExtra("datos",datoss.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
    public String toBinario(int numero){   // convierte de decimal a binario
        String bin = Integer.toBinaryString(numero);
        StringBuilder builder = new StringBuilder(bin);
        String reversa = builder.reverse().toString();

        while( reversa.length() < 16 ){
            reversa += "0";
        }
        StringBuilder builder1 = new StringBuilder(reversa);
        bin = builder1.reverse().toString();

        return bin;
    }

    public void seleccionaDS(){
        opciones.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opc));
        opciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i)==opc[0]){ //default
                    ds = ""; ds = "000000";
                } if(adapterView.getItemAtPosition(i)==opc[1]){
                    ds = ""; ds = "101110";
                } if(adapterView.getItemAtPosition(i)==opc[2]){
                    ds = ""; ds = "001010";
                } if(adapterView.getItemAtPosition(i)==opc[3]){
                    ds = ""; ds = "001100";
                } if(adapterView.getItemAtPosition(i)==opc[4]){
                    ds = ""; ds = "001110";
                } if(adapterView.getItemAtPosition(i)==opc[5]){
                    ds = ""; ds = "010010";
                } if(adapterView.getItemAtPosition(i)==opc[6]){
                    ds = ""; ds = "010100";
                } if(adapterView.getItemAtPosition(i)==opc[7]){
                    ds = ""; ds = "010110";
                } if(adapterView.getItemAtPosition(i)==opc[8]){
                    ds = ""; ds = "011010";
                } if(adapterView.getItemAtPosition(i)==opc[9]){
                    ds = ""; ds = "011100";
                } if(adapterView.getItemAtPosition(i)==opc[10]){
                    ds = ""; ds = "011110";
                } if(adapterView.getItemAtPosition(i)==opc[11]){
                    ds = ""; ds = "100010";
                } if(adapterView.getItemAtPosition(i)==opc[12]){
                    ds = ""; ds = "100100";
                } if(adapterView.getItemAtPosition(i)==opc[13]){
                    ds = ""; ds = "100110";
                } if(adapterView.getItemAtPosition(i)==opc[14]){
                    ds = ""; ds = "001000";
                } if(adapterView.getItemAtPosition(i)==opc[15]){
                    ds = ""; ds = "010000";
                } if(adapterView.getItemAtPosition(i)==opc[16]){
                    ds = ""; ds = "011000";
                } if(adapterView.getItemAtPosition(i)==opc[17]){
                    ds = ""; ds = "100000";
                } if(adapterView.getItemAtPosition(i)==opc[18]){
                    ds = ""; ds = "101000";
                } if(adapterView.getItemAtPosition(i)==opc[19]){
                    ds = ""; ds = "110000";
                } if(adapterView.getItemAtPosition(i)==opc[20]){
                    ds = ""; ds = "111000";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Seleccione un opción para DS", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean verificaCampos(){
        if( t_flujo.getText().length()<20 || siguienteCabecera.getText().length()<8 || l_saltos.getText().length()<8){

            if( t_flujo.getText().length()<20 ){
                t_flujo.setError("20 bits para completar\npor favor verifique");
            } if( siguienteCabecera.getText().length()<8 ){
                siguienteCabecera.setError("8 bits para completar\npor favor verifique");
            } if( l_saltos.getText().length()<8 ){
                l_saltos.setError("8 bits para completar\npor favor verifique");
            } /*if( d_fuente.getText().length()<39 ){
                d_fuente.setError("32 valores hexadecimales con : entre cada 4 valores");
            } /*if( d_destino.getText().length()<39 ){
                d_destino.setError("32 valores hexadecimales con : entre cada 4 valores");
            }*/
        } else {
            return true;
        }
        return false;
    }

    public String ranIp(int longitud){
        String cadenaAleatoria = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(System.currentTimeMillis());
        int i = 0;
        while ( i < longitud){
            char c = (char)r.nextInt(256);
            if ( (c >= '0' && c <='9') || (c >='A' && c <='F') ){
                cadenaAleatoria += c;
                i++;
            }
        }
        return cadenaAleatoria;
    }

    public String ran(){
        return ranIp(4)+":"+ranIp(4)+":"+ranIp(4)+":"+ranIp(4)+":"+ranIp(4)+":"+ranIp(4)+":"+ranIp(4)+":"+ranIp(4);
    }

    public String ranBin( int veces ){
        String numBin = "";
        int[] arrnums = new int[veces];
        Random random = new Random();
        for(int i=0; i<veces; i++){
            arrnums[i] = random.nextInt(2);
            //random.setSeed(System.currentTimeMillis());
            numBin += arrnums[i];
        }
        return numBin;
    }
}
