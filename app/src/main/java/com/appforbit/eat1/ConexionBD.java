package com.appforbit.eat1;

import android.content.ContentProvider;
import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD extends AppCompatActivity {

    public static Connection conexionBD(){
        Connection cn = null;
        String urlDB = "jdbc:jtds:sqlserver://eatasty.mssql.somee.com:1433/eatasty";
        String userDB = "glorenzo68_SQLLogin_1";
        String passwordDB = "qlwnekx8xb";
        try{
            StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            cn = DriverManager.getConnection(urlDB,userDB,passwordDB);
        } catch(Exception e){
            e.getMessage().toString();
        }
        return cn;
    }

    public static Connection conexionBD(Context contexto) {
        Connection con =null;
        String urlDB = "jdbc:jtds:sqlserver://eatasty.mssql.somee.com:1433/eatasty";
        String userDB = "glorenzo68_SQLLogin_1";
        String passwordDB = "qlwnekx8xb";
        try{
            StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(urlDB,userDB,passwordDB);
            Toast.makeText(contexto,"Enlace establecido", Toast.LENGTH_SHORT).show();
        } catch(Exception e){
            Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return con;
    }

}
