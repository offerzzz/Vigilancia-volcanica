package ec.edu.udla.vigilancia;

import java.util.Locale;

public class Ubicacion {

    private double latitud;
    private double longitud;
    private double altitud;

    public Ubicacion() {
        this.latitud = 0;
        this.longitud = 0;
        this.altitud = 0;
    }

    public Ubicacion(double latitud, double longitud, double altitud) {
        setLatitud(latitud);
        setLongitud(longitud);
        setAltitud(altitud);
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        if (Double.isNaN(latitud) || latitud < -90 || latitud > 90) {
            System.out.println("Latitud inválida (" + latitud + "): debe estar entre -90 y 90 grados.");
            return;
        }
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        if (Double.isNaN(longitud) || longitud < -180 || longitud > 180) {
            System.out.println("Longitud inválida (" + longitud + "): debe estar entre -180 y 180 grados.");
            return;
        }
        this.longitud = longitud;
    }

    public double getAltitud() {
        return altitud;
    }

    public void setAltitud(double altitud) {
        if (Double.isNaN(altitud) || altitud < 0) {
            System.out.println("Altitud inválida (" + altitud + "): no puede ser negativa.");
            return;
        }
        this.altitud = altitud;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Lat %.4f, Lon %.4f, Alt %.0f m", latitud, longitud, altitud);
    }
}
