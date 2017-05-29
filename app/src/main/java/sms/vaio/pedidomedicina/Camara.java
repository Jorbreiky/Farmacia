package sms.vaio.pedidomedicina;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.widget.Toast;

public class Camara {

    Camera camara;
    boolean encendida;
    SurfaceHolder surfaceHolder;
    SurfaceView surfaceView;
    String rutaFoto;

    public Camara(SurfaceView cam){
        surfaceView = cam;
    }
    public void tomarFoto(){
        camara.takePicture(shutterCallback,rawCallback,jpegCallback);
    }
    ShutterCallback shutterCallback = new ShutterCallback() {public void onShutter() {}};

    PictureCallback rawCallback = new PictureCallback() {public void onPictureTaken(byte[] data, Camera camera) {}};

    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            new SaveImageTask().execute(data);
            camara.stopPreview();
        }
    };

    public void iniciarCamaraTrasera(){
        try{
            surfaceHolder = surfaceView.getHolder();
            if (!encendida) {
                camara = Camera.open();
                camara.setDisplayOrientation(90);
                camara.setPreviewDisplay(surfaceHolder);
                camara.startPreview();
                encendida = true;
            }
            else{
                camara.stopPreview();
                camara.release();
                encendida = false;
            }
        }catch(Exception e){
            Log.e("JMA",e.toString());
        }
    }


    public class SaveImageTask extends AsyncTask<byte[], Void, Void> {
        @Override
        protected Void doInBackground(byte[]... data) {
            FileOutputStream outStream = null;
            try {
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File (sdCard.getAbsolutePath() + "/pedido");
                dir.mkdirs();
                String fileName = String.format("pedido.jpg", System.currentTimeMillis());
                File outFile = new File(dir, fileName);
                Log.e("JMA",outFile.getAbsolutePath());
                outStream = new FileOutputStream(outFile);
                outStream.write(data[0]);
                rutaFoto = outFile.getAbsolutePath();
                Log.e("JMAA","Ruta de la Foto: "+rutaFoto);
                outStream.flush();
                outStream.close();
            } catch (Exception e) {
                Log.e("JMAA",e.toString());
            }
            return null;
        }
    }
}