package com.appforbit.eat1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TomarFoto extends AppCompatActivity {
    private ImageButton botonCamara;
    private String rutaArchivo = null;
    private Bitmap fotoBitmap = null;
    private byte[] fotoByte = null;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bitmap bitmap = null;
        int rqCode = 0;
        int rsCode = 0;
        byte[] fotoByte = null;
        Intent datos = null;
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager())!=null) {
            startActivityForResult(takePic, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            try {
                Bundle extras = data.getExtras();
                fotoBitmap = (Bitmap) extras.get("data");
                fotoByte = BitmapUtils.convertBitmapToByteArray(fotoBitmap);
                data.putExtra("mapa", fotoByte);
                setResult(RESULT_OK, data);
                finish();
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
