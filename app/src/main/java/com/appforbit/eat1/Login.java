package com.appforbit.eat1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class Login extends AppCompatActivity {
    private EditText emailET;
    private EditText passwordET;
    public int id =-1;
    private Button BotonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailET = (EditText) findViewById(R.id.login_email_input);
        passwordET = (EditText) findViewById(R.id.login_password_input);
        BotonLogin = (Button) findViewById(R.id.loginBtn);

        BotonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loguearse();
            }
        });
    }

     public int BuscarUsuario(){
         String correo = emailET.getText().toString();
         String password = passwordET.getText().toString();
         id=0;
         Connection con =ConexionBD.conexionBD(this);
         try {
             Statement st = null;
             st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM [eatasty].[dbo].[users] WHERE correoU = '"+correo+"' AND passwordU = '"+password+"'");
             while (rs.next()) {
                id=rs.getInt(1);
             }
         } catch(Exception e){
             Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
         }
         return id;
     }

     private void Loguearse(){
         id=BuscarUsuario();
         if (id>0){
             Intent menuPrincipal = new Intent(this, MenuPrincipal.class);
             menuPrincipal.putExtra("idUsuario", id);
             startActivity(menuPrincipal);
         }else{
             Toast.makeText(getApplicationContext(), " Usuario inexistente ", Toast.LENGTH_LONG).show();
         }
     }
}
