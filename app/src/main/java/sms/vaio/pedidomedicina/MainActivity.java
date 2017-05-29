package sms.vaio.pedidomedicina;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Camara camara;
    Button btnCapturar;
    Button btnIniciar;
    Button btnEnviarCorreo;
    TextView TVUbicacion;
    EditText ETCorreo;
    EditText ETContraseña;
    GPS gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            //componentes de la interfaz
            camara = new Camara((SurfaceView) findViewById(R.id.camara));
            btnCapturar = (Button) findViewById(R.id.btnCapturar);
            btnIniciar = (Button) findViewById(R.id.btnIniciar);
            btnEnviarCorreo = (Button) findViewById(R.id.btnEnviarCorreo);
            TVUbicacion = (TextView) findViewById(R.id.TVUbicacion);
            ETCorreo = (EditText) findViewById(R.id.ETCorreo);
            ETContraseña = (EditText) findViewById(R.id.ETContraseña);

            LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            gps = new GPS();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),"No tiene permiso",Toast.LENGTH_LONG).show();
                return;
            }else{
                mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) gps);
            }
            gps.setMain(this);
            btnCapturar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        camara.tomarFoto();
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }

            });

            btnIniciar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        camara.iniciarCamaraTrasera();
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }

            });
            btnEnviarCorreo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        EmailSender emailSender = new EmailSender();
                        emailSender.execute(ETCorreo.getText().toString(), ETContraseña.getText().toString(), "jorgeitch@hotmail.com", "Pedido de Medicina", gps.getUrlUbicacion(), "/storage/sdcard0/pedido/pedido.jpg");
                        Toast.makeText(getApplicationContext(),"Correo Enviado Correctamente",Toast.LENGTH_LONG).show();
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }

            });
        } catch (Exception e) {
            TVUbicacion.setText(e.toString());
            Toast.makeText(getApplicationContext(),"Error en MainActivity \n"+e.toString(),Toast.LENGTH_LONG).show();
            Log.e("JMA",e.toString());
        }
    }


}
