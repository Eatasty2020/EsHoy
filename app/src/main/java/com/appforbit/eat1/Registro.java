package com.appforbit.eat1;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Registro extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputName, InputPassword, InputEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        CreateAccountButton = (Button) findViewById(R.id.register_Btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputEmail = (EditText) findViewById(R.id.register_email_input);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccountButton();
            }
        });
    }


    private void CreateAccountButton()
    {
        String name = InputName.getText().toString();
        String password = InputPassword.getText().toString();
        String email = InputEmail.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Por favor, escribe tu nombre", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Por favor, escribe tu contraseña", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Por favor, escribe tu email", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ValidateUsername(name, password, email);
        }
    }

    private void ValidateUsername(String name, String password, String email){
        int usuario = 0;
        usuario=BuscarCorreoUsuario();
        if (usuario>0){
            Toast.makeText(getApplicationContext(), " El correo electrónico ingresado ya está asignado a un usuario ", Toast.LENGTH_LONG).show();
        } else{
            Registrar();
        }
    }

    public Connection conexionBD() {
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

    public int BuscarCorreoUsuario(){
        String correoBCU = InputEmail.getText().toString();
        int id = 0;
        try {
            Statement st = conexionBD().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM [eatasty].[dbo].[users] WHERE correoU = '"+correoBCU+"'");
            while (rs.next()) {
                id=rs.getInt(1);
            }
        } catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return id;
    }

    public void Registrar(){
        String correoReg = InputEmail.getText().toString();
        String passwordReg = InputPassword.getText().toString();
        String nombreReg = InputName.getText().toString();
        String apellidoReg = "";
        String paisReg ="";
        boolean celiacoReg = false;
        boolean diabeticoReg = false;
        boolean hipertensoReg = false;
        boolean olvReg = false;
        boolean veganoReg = false;
        boolean noTransgenicoReg = false;
        try{
            PreparedStatement st;
            st = conexionBD().prepareStatement("INSERT INTO users (correoU, passwordU, nombreU," +
                    " apellidoU, paisU, celiacoU, diabeticoU, hipertensoU, olvU, veganoU, noTransgenicoU)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            st.setString(1, correoReg);
            st.setString(2, passwordReg);
            st.setString(3, nombreReg);
            st.setString(4,apellidoReg);
            st.setString(5,paisReg);
            st.setBoolean(6,celiacoReg);
            st.setBoolean(7,diabeticoReg);
            st.setBoolean(8,hipertensoReg);
            st.setBoolean(9,olvReg);
            st.setBoolean(10,veganoReg);
            st.setBoolean(11,noTransgenicoReg);
            st.executeUpdate();
            Toast.makeText(getApplicationContext()," Usuario creado ",Toast.LENGTH_SHORT).show();
            finish();
        }catch(SQLException ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}