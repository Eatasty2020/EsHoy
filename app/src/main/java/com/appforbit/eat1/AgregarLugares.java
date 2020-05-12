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
    private String nombreLugar = null;
    private int tipoLugar = 0;
    private String calleLugar = null;
    private String barrioLugar = null;
    private String ciudadLugar = null;
    private String paisLugar = null;
    private String telefonoLugar = null;
    private String urlLugar = null;
    private boolean deliveryLugar = false;
    private float calificacionLugar = 0;
    private String reseniaLugar = null;
    private long latitudLugar = 0;
    private long longitudLugar = 0;
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
                    if (extras!=null) usuarioLugar=extras.getInt("usuarioId");
                    if (nombreL!=null) nombreLugar = nombreL.toString();
                    if (tipoL!=null) tipoLugar =obtenerTipoLugarInt(tipoL.getSelectedItem().toString(),listaOpciones);
                    if (calleL!=null) calleLugar = calleL.toString();
                    if (barrioL!=null) barrioLugar = barrioL.toString();
                    if (ciudadL!=null) ciudadLugar = ciudadL.toString();
                    if (paisL!=null) paisLugar = paisL.toString();
                    telefonoLugar = telefonoL.toString();
                    urlLugar = urlL.toString();
                    deliveryLugar = deliveryL.isChecked();
                    calificacionLugar = calificacionL.getRating();
                    reseniaLugar = reseniaL.toString();
                    if (geolocalizadoB=true){}
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
                        statement.setBoolean(10, deliveryLugar);
                        statement.setFloat(11, calificacionLugar);
                        statement.setString(12, reseniaLugar);
                        statement.setLong(13, latitudLugar);
                        statement.setLong( 14, longitudLugar);
                        statement.setBytes(15, fotoByteLugar);
                        statement.setBoolean(16, celiacoLugar);
                        statement.setBoolean(17, diabeticoLugar);
                        statement.setBoolean(18, hipertensoLugar);
                        statement.setBoolean(19, olvLugar);
                        statement.setBoolean(20, veganoLugar);
                        statement.setBoolean(21, noTransgenicoLugar);
                        ancho = statement.getMaxFieldSize();
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
                    Toast.makeText(getApplicationContext(), "Sin implementar", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 && resultCode == RESULT_OK){
            try {
                Bundle extras = data.getExtras();
                fotoByte = (byte[]) extras.getByteArray("mapa");
                data.putExtra("mapa",fotoByte);
                setResult(RESULT_OK, data);
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void agregarLugar(int u, String nL, int tl, String ca, String ba, String ci, String pa, String te, String uL, boolean dL,
                              float cL, String rL, long laL, long loL, byte[] fL, boolean ce, boolean di, boolean hi, boolean olv, boolean ve, boolean nt){
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
