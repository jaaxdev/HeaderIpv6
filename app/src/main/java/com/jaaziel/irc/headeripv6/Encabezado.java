package com.jaaziel.irc.headeripv6;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Medalorian on 05/06/2017.
 */

public class Encabezado extends AppCompatActivity {
    TextView encabezado, tag_flujo, c_util, s_cabecera, l_saltos,d_fuente, d_destino;
    String ecn = "00";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encabezado);

        encabezado = (TextView) findViewById(R.id.ds_head);
        tag_flujo = (TextView) findViewById(R.id.etiqueta_flujo);
        c_util = (TextView) findViewById(R.id.carga_util);
        s_cabecera = (TextView) findViewById(R.id.sig_head);
        l_saltos = (TextView) findViewById(R.id.lim_saltos);
        d_fuente = (TextView) findViewById(R.id.dir_fuente);
        d_destino = (TextView) findViewById(R.id.dir_destino);

        addTextoToCabecera();
    }

    public void addTextoToCabecera(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String ds;
        String[] controlTrafico, cu;
        if( bundle!=null ){

            ds = bundle.getString("DS")+ecn;
            controlTrafico = splitByNumber(ds,2);
            cu = splitByNumber(bundle.getString("carga_util"),4);

            encabezado.setText(binToHex(controlTrafico[0]).toUpperCase());
            encabezado.append(binToHex(controlTrafico[1]).toUpperCase());
            tag_flujo.setText(binToHex(bundle.getString("etiqueta")).toUpperCase());
            c_util.setText(binToHex(cu[0]).toUpperCase());
            c_util.append(binToHex(cu[1]).toUpperCase());
            c_util.append(binToHex(cu[2]).toUpperCase());
            c_util.append(binToHex(cu[3]).toUpperCase());
            s_cabecera.setText(binToHex(bundle.getString("header")).toUpperCase());
            l_saltos.setText(binToHex(bundle.getString("lim_saltos")).toUpperCase());
            d_fuente.setText(bundle.getString("dir_fuente"));
            d_destino.setText(bundle.getString("dir_destino"));

            Toast.makeText(getApplicationContext(),"App desarrollada por: \nJaaziel Isai Rebollar Calzada", Toast.LENGTH_LONG).show();
        }
    }

    public String binToHex(String valorBin ){
        int dec = Integer.parseInt(valorBin, 2);
        valorBin = Integer.toHexString(dec);
        return valorBin;
    }
    /*private String decToHex( int valor ){
        return Integer.toHexString(valor);
    }*/

    private String[] splitByNumber(String s, int size) {  //divide string en partes iguales de 4 bits
        if(s == null || size <= 0)
            return null;
        int chunks = s.length() / size + ((s.length() % size > 0) ? 1 : 0);
        String[] arr = new String[chunks];
        for(int i = 0, j = 0, l = s.length(); i < l; i += size, j++)
            arr[j] = s.substring(i, Math.min(l, i + size));
        return arr;
    }
}
