package com.appforbit.eat1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MenuPrincipal extends AppCompatActivity {
    private Button btnLugares;
    private Button btnProductos;
    private Button btnRecetas;
    private Button btnCursos;
    private Button btnEventos;
    private Bundle estadoInstancia;
    private boolean parametrosOK = false;
    private int usuario = 0;
    private ImageView imgInfoPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        //imgInfoPopup = findViewById(R.id.imginfo);

        //imgInfoPopup.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        startActivity(new Intent(MenuPrincipal.this, PopUpInfo.class));
        //    }
        //});


        btnLugares      = (Button) findViewById(R.id.botonLugares);
        btnProductos    = (Button) findViewById(R.id.botonProductos);
        btnRecetas      = (Button) findViewById(R.id.botonRecetas);
        btnCursos       = (Button) findViewById(R.id.botonCursos);
        btnEventos      = (Button) findViewById(R.id.botonEventos);

        estadoInstancia=savedInstanceState;
        usuario=RecuperarUsuario();
        if (usuario>0){
            parametrosOK = true;
        }

        try {
            btnLugares.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent lugares = new Intent(getApplicationContext(), Lugares.class);
                    if (parametrosOK == true) lugares.putExtra("idUsuario", usuario);
                    startActivity(lugares);
                }
            });

            btnProductos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productos = new Intent(MenuPrincipal.this, Productos.class);
                    if (parametrosOK == true) productos.putExtra("idUsuario", usuario);
                    startActivity(productos);
                }
            });
            btnRecetas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentRecetas = new Intent(MenuPrincipal.this, Recetas.class);
                    if (parametrosOK == true) intentRecetas.putExtra("idUsuario", usuario);
                    startActivity(intentRecetas);
                }
            });
            btnCursos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentCursos = new Intent(MenuPrincipal.this, Cursos.class);
                    if (parametrosOK == true) intentCursos.putExtra("idUsuario", usuario);
                    startActivity(intentCursos);
                }
            });
            btnEventos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentEventos = new Intent(MenuPrincipal.this, Eventos.class);
                    if (parametrosOK == true) intentEventos.putExtra("idUsuario", usuario);
                    startActivity(intentEventos);
                }
            });
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public int RecuperarUsuario(){
        int user =0;
        if (estadoInstancia==null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null){
                user = extras.getInt("idUsuario");
            }
        }
        return user;
    }
}
