package ec.edu.udla.vigilancia;

import java.util.Locale;

public class Volcan {

    private String nombre;
    private String provincia;
    private double latitud;
    private double longitud;
    private String nivelAlerta;

    public Volcan() {
        this.nombre = "Sin nombre";
        this.provincia = "Sin provincia";
        this.latitud = 0;
        this.longitud = 0;
        this.nivelAlerta = "BLANCA";
    }

    public Volcan(String nombre, String provincia, double latitud, double longitud) {
        this();
        setNombre(nombre);
        setProvincia(provincia);
        setLatitud(latitud);
        setLongitud(longitud);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre del volcán no puede estar vacío.");
            return;
        }
        this.nombre = nombre.trim();
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        if (provincia == null || provincia.trim().isEmpty()) {
            System.out.println("La provincia del volcán no puede estar vacía.");
            return;
        }
        this.provincia = provincia.trim();
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

    public String getNivelAlerta() {
        return nivelAlerta;
    }

    public void setNivelAlerta(String nivelAlerta) {
        if (nivelAlerta == null) {
            System.out.println("El nivel de alerta no puede ser nulo.");
            return;
        }
        String nivel = nivelAlerta.trim().toUpperCase();
        if (nivel.equals("BLANCA") || nivel.equals("AMARILLA") || nivel.equals("NARANJA") || nivel.equals("ROJA")) {
            this.nivelAlerta = nivel;
        } else {
            System.out.println("Nivel de alerta inválido: " + nivelAlerta + ".");
        }
    }

    public void actualizarNivelAlerta(int sensoresEnAlerta) {
        if (sensoresEnAlerta < 0) {
            System.out.println("La cantidad de sensores en alerta no puede ser negativa.");
            return;
        }
        if (sensoresEnAlerta == 0) {
            this.nivelAlerta = "BLANCA";
        } else if (sensoresEnAlerta == 1) {
            this.nivelAlerta = "AMARILLA";
        } else if (sensoresEnAlerta == 2) {
            this.nivelAlerta = "NARANJA";
        } else {
            this.nivelAlerta = "ROJA";
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Volcán %s | Provincia: %s | Cráter: (%.4f, %.4f) | Alerta: %s",
                nombre, provincia, latitud, longitud, nivelAlerta);
    }
}
