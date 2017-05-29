package sms.vaio.pedidomedicina;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class GPS implements LocationListener {

    MainActivity main;
    String urlUbicacion;

    public void setMain(MainActivity mainactivity) {
        this.main = mainactivity;
        main.TVUbicacion.setText("Detectando Localizacion GPS");
    }

    @Override
    public void onLocationChanged(Location loc) {
        loc.getLatitude();
        loc.getLongitude();
        String text = "Mi ubicacion actual es: " + "\n Lat = " + loc.getLatitude() + "\n Long = " + loc.getLongitude();
        urlUbicacion = "<a href='https://maps.google.com/maps?q=" + loc.getLatitude() + "," + loc.getLongitude() + "&z=16'>Abrir Ubicacion</a>";
        main.TVUbicacion.setText(text);
    }

    public String getUrlUbicacion(){
        return urlUbicacion;
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Este metodo se ejecuta cuando el GPS es desactivado
        Log.e("JMA","Entro al Provider Disable");
        main.TVUbicacion.setText("GPS Desactivado");
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Este metodo se ejecuta cuando el GPS es activado
        Log.e("JMA","Entro al Provider Enable");
        main.TVUbicacion.setText("GPS Activado");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

}