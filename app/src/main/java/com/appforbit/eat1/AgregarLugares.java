package com.appforbit.eat1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOError;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AgregarLugares extends AppCompatActivity {
    private EditText nombreL =null;
    private Spinner tipoL = null;
    private EditText calleL = null;
    private EditText barrioL = null;
    private EditText ciudadL = null;
    private EditText paisL = null;
    private EditText telefonoL = null;
    private EditText urlL = null;
    private CheckBox deliveryL;
    private RatingBar calificacionL = null;
    private EditText reseniaL = null;
    private Button geolocalizarL;
    private Button tomarFotoL;
    private CheckBox celiacoL;
    private CheckBox diabeticoL;
    private CheckBox hipertensoL;
    private CheckBox olvL;
    private CheckBox veganoL;
    private CheckBox noTransgenicoL;
    private Button enviarL;
    private int usuarioLugar = -1;
    private String nombreLugar;
    private int tipoLugar;
    private String calleLugar;
    private String barrioLugar = "";
    private String ciudadLugar = "";
    private String paisLugar = "";
    private String telefonoLugar = "";
    private String urlLugar = "";
    private boolean deliveryLugar = false;
    private float calificacionLugar = 0;
    private String reseniaLugar = "";
    private double latitudLugar = 0;
    private double longitudLugar = 0;
    private byte[] fotoByteLugar = null;
    private boolean celiacoLugar = false;
    private boolean diabeticoLugar = false;
    private boolean hipertensoLugar = false;
    private boolean olvLugar = false;
    private boolean veganoLugar = false;
    private boolean noTransgenicoLugar = false;
    private String consulta = null;
    private String [] opcionesL;
    private List<ObjetoTipoLugar> listaOpciones;
    private Bitmap fotoBitmap = null;
    private byte[] fotoByte = null;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private boolean fotoB = false;
    private boolean geolocalizadoB = false;
    private ObjetoTipoLugar registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_lugares);
        nombreL = (EditText)findViewById(R.id.nombreET);
        tipoL = (Spinner)findViewById(R.id.tipoLspinner);
        calleL = (EditText)findViewById(R.id.calleET);
        barrioL = (EditText)findViewById(R.id.barrioET);
        ciudadL = (EditText)findViewById(R.id.ciudadET);
        paisL = (EditText)findViewById(R.id.paisET);
        telefonoL = (EditText)findViewById(R.id.telefonoET);
        urlL = (EditText)findViewById(R.id.urlET);
        deliveryL = (CheckBox)findViewById(R.id.checkDelivery);
        calificacionL = (RatingBar)findViewById(R.id.calificacionRB);
        reseniaL = (EditText)findViewById(R.id.reseniaTV);
        geolocalizarL = (Button)findViewById(R.id.btnGeo);
        tomarFotoL = (Button)findViewById(R.id.btnFotoLugar);
        celiacoL = (CheckBox)findViewById(R.id.checkCeliaco);
        diabeticoL = (CheckBox)findViewById(R.id.checkDiabetico);
        hipertensoL = (CheckBox)findViewById(R.id.checkHipertenso);
        olvL = (CheckBox)findViewById(R.id.checkOLV);
        veganoL = (CheckBox)findViewById(R.id.checkVegano);
        noTransgenicoL = (CheckBox)findViewById(R.id.checkNT);
        enviarL = (Button)findViewById(R.id.btnEnviarLugar);

        listaOpciones=obtenerTiposLugaresBD();
        opcionesL=llenarOpcionesL(listaOpciones);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opcionesL);
        tipoL.setAdapter(adapter);
        try {
            enviarL.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Bundle extras = getIntent().getExtras();
                    if (extras!=null) usuarioLugar=extras.getInt("idUsuario");
                    if (nombreL!=null) nombreLugar = nombreL.getText().toString();
                    if (tipoL!=null) tipoLugar =obtenerTipoLugarInt(tipoL.getSelectedItem().toString(),listaOpciones);
                    if (calleL!=null) calleLugar = calleL.getText().toString();
                    if (barrioL!=null) barrioLugar = barrioL.getText().toString();
                    if (ciudadL!=null) ciudadLugar = ciudadL.getText().toString();
                    if (paisL!=null) paisLugar = paisL.getText().toString();
                    telefonoLugar = telefonoL.getText().toString();
                    urlLugar = urlL.getText().toString();
                    deliveryLugar = deliveryL.isChecked();
                    calificacionLugar = calificacionL.getRating();
                    reseniaLugar = reseniaL.getText().toString();
                    /*if (geolocalizadoB=true){
                        extras.getDouble("latitudL", latitudLugar);
                        extras.getDouble("longitudL", longitudLugar);
                    }*/
                    fotoByteLugar = fotoByte;
                    celiacoLugar = celiacoL.isChecked();
                    diabeticoLugar = diabeticoL.isChecked();
                    hipertensoLugar = hipertensoL.isChecked();
                    olvLugar = olvL.isChecked();
                    veganoLugar = veganoL.isChecked();
                    noTransgenicoLugar = noTransgenicoL.isChecked();
                    String mensaje = "Resultado desconocido...";
                    long ancho = 0;
                    Connection conexion;
                    conexion = ConexionBD.conexionBD();
                    consulta = "INSERT INTO lugares (idLU, nombreL, tipoL, calleL, barrioL, ciudadL, paisL, telefonoL, "
                            + "urlL, deliveryL, calificacionL, reseniaL, latitudL, longitudL, fotoL, celiacoL, diabeticoL, "
                            + "hipertensoL, olvL, veganoL, noTransgenicoL) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try {
                        PreparedStatement statement = null;
                        statement = conexion.prepareStatement(consulta);
                        statement.setInt(1, usuarioLugar);
                        statement.setString(2, nombreLugar);
                        statement.setInt(3 , tipoLugar);
                        statement.setString(4, calleLugar);
                        statement.setString(5, barrioLugar);
                        statement.setString(6, ciudadLugar);
                        statement.setString(7, paisLugar);
                        statement.setString(8, telefonoLugar);
                        statement.setString(9, urlLugar);
                        if (deliveryLugar== true){
                            statement.setInt(10,  1);
                        }else {
                            statement.setInt(10,  0);
                        }
                        statement.setFloat(11, calificacionLugar);
                        statement.setString(12, reseniaLugar);
                        statement.setDouble(13, latitudLugar);
                        statement.setDouble( 14, longitudLugar);
                        statement.setBytes(15, fotoByteLugar);
                        if (celiacoLugar==true) {
                            statement.setInt(16,  1);
                        }else {
                            statement.setInt(16,  0);
                        }
                        if (diabeticoLugar==true) {
                            statement.setInt(17,  1);
                        }else {
                            statement.setInt(17,  0);
                        }
                        if (hipertensoLugar==true) {
                            statement.setInt(18,  1);
                        }else {
                            statement.setInt(18,  0);
                        }
                        if (olvLugar==true) {
                            statement.setInt(19,  1);
                        }else {
                            statement.setInt(19,  0);
                        }
                        if (veganoLugar==true) {
                            statement.setInt(20,  1);
                        }else {
                            statement.setInt(20,  0);
                        }
                        if (noTransgenicoLugar==true) {
                            statement.setInt(21,  1);
                        }else {
                            statement.setInt(21,  0);
                        }
                        statement.executeUpdate();
                        mensaje = "Registro agregado correctamente ...";
                    } catch (SQLException e){
                        mensaje = e.getMessage().toString();
                        Log.d("Error Nº 1:", mensaje);
                    }catch (IOError e){
                        mensaje = e.getMessage().toString();
                        Log.d("Error Nº 2:", mensaje);
                    }catch (AndroidRuntimeException e){
                        mensaje = e.getMessage().toString();
                        Log.d("Error Nº 3:", mensaje);
                    }catch (NullPointerException e){
                        mensaje = e.getMessage().toString();
                        Log.d("Error Nº 4:", mensaje);
                    }catch (Exception e){
                        mensaje = e.getMessage().toString();
                        Log.d("Error Nº 5:", mensaje);
                    }
                    Toast.makeText(AgregarLugares.this, mensaje, Toast.LENGTH_LONG).show();
                    finish();
                }
            });
            tomarFotoL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent foto = new Intent(getApplicationContext(), TomarFoto.class);
                    startActivityForResult(foto, 1234);
                }
            });
            geolocalizarL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent localizar = new Intent(getApplicationContext(), Localizar.class);
                    startActivityForResult(localizar, 5678);
                    //Toast.makeText(getApplicationContext(), "Sin implementar", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (((requestCode == 1234) || (requestCode ==5678)) && resultCode == RESULT_OK){
            try {
                if (requestCode == 1234){
                    Bundle extras = data.getExtras();
                    fotoByte = (byte[]) extras.getByteArray("mapa");
                    data.putExtra("mapa",fotoByte);
                    setResult(RESULT_OK, data);
                }
                if (requestCode == 5678){
                    Bundle bundle = data.getExtras();
                    if (bundle!=null) {
                        geolocalizadoB = true;
                        latitudLugar = bundle.getDouble("latitud");
                        longitudLugar = bundle.getDouble("longitud");
                    }
                }
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private int obtenerTipoLugarInt(String opcionLugarString, List<ObjetoTipoLugar> tLugar){
        int opcionLugarInt = -1;
        int i = 0;
        while ((i <=tLugar.size())&&(opcionLugarInt==-1)){
            if (tLugar.get(i).getNombreTl()==opcionLugarString){
                opcionLugarInt = tLugar.get(i).getIdTl();
            }
            i = i+1;
        }
        return  opcionLugarInt;
    }

    private String[] llenarOpcionesL(List<ObjetoTipoLugar> tipoLugar){
        String[] opciones = new String[tipoLugar.size()];
        int i = 0;
        try {
            while (i < tipoLugar.size()) {
                opciones[i] = tipoLugar.get(i).getNombreTl();
                i++;
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return opciones;
    }

    private List<ObjetoTipoLugar> obtenerTiposLugaresBD(){
        List<ObjetoTipoLugar> tipoLugar = new ArrayList<>();
        String tipoL = "";
        Connection conexion = ConexionBD.conexionBD(this);
        String consulta ="SELECT idTl, nombreTl FROM tipoLugar";
        try{
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()){
                tipoLugar.add(new ObjetoTipoLugar(rs.getInt("idTl"), rs.getString("nombreTl")));
            }
            conexion.close();
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return tipoLugar;
    }

}
