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

     public Connection conexionBD(){
         Connection cn = null;
         String urlDB = "jdbc:jtds:sqlserver://eatasty.mssql.somee.com:1433/eatasty";
         String userDB = "glorenzo68_SQLLogin_1";
         String passwordDB = "qlwnekx8xb";
         try{
             StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
             StrictMode.setThreadPolicy(politica);
             Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
             cn = DriverManager.getConnection(urlDB,userDB,passwordDB);
             Toast.makeText(getApplicationContext(),"Enlace establecido", Toast.LENGTH_SHORT).show();
         } catch(Exception e){
             Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
         }
         return cn;
     }
     public int BuscarUsuario(){
         String correo = emailET.getText().toString();
         String password = passwordET.getText().toString();
         id=0;
         try {
             Statement st = conexionBD().createStatement();
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
