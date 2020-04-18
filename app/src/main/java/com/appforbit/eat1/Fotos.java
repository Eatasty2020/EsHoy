package com.appforbit.eat1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class Fotos extends AppCompatActivity {
    private static float maxAncho = 300;
    private static float maxAlto = 300;
    public String currentPhotoPath;
    private ImageView img;
    public Button camara;
    public Context contexto;


    public static void subirFotoBD(byte[] fotoByte, String tabla, String campo, int id){
        String consulta = "";
        Connection conexion = null;
        boolean esGrande = false;
        boolean alta = false;
        boolean ancha = false;
        float altoFoto = 0;
        float anchoFoto = 0;
        float aspecto = 0;
        Bitmap foto = null;
        String error ="";

        foto = BitmapFactory.decodeByteArray(fotoByte,0,fotoByte.length);
        altoFoto = foto.getHeight();
        anchoFoto = foto.getWidth();
        if (altoFoto>anchoFoto){
            aspecto = altoFoto/anchoFoto;
            alta = true;
            if (altoFoto>maxAlto){
                esGrande = true;
            }
        } else{
            if (anchoFoto>altoFoto){
                aspecto = anchoFoto/altoFoto;
                ancha = true;
                if (anchoFoto>maxAncho){
                    esGrande = true;
                }
            }
        }
        if (esGrande==true){
            if (alta==true) {
                foto = redimensionarImagenMaximo(foto, (maxAncho/aspecto), maxAlto);
            }
            if (ancha==true){
                foto = redimensionarImagenMaximo(foto, maxAncho, (maxAlto/aspecto));
            }
        }
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            foto.compress(Bitmap.CompressFormat.PNG, 0, baos);
            fotoByte = baos.toByteArray();
        }finally {
            if(baos != null){
                try {
                    baos.close();
                } catch (Exception e) {
                    error = e.getMessage().toString();
                }
            }
        }

        conexion = ConexionBD.conexionBD();
        try {
            consulta = "UPDATE "+tabla+" SET "+campo+"=? WHERE idL ="+id;
            PreparedStatement pStatement = conexion.prepareStatement(consulta);
            pStatement.setBytes(1,fotoByte);
            pStatement.execute();
            pStatement.close();
        }catch (Exception e){
            error = e.getMessage();
        }
    }

    /*
      Redimensionar un Bitmap. By TutorialAndroid.com
      @param Bitmap mBitmap
      @param float newHeight
      @param float newHeight
      @param float newHeight
      @return Bitmap
     */
    public static Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }
    public static InputStream bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }/*from   ww w  .ja v a2 s .co  m*/
}
