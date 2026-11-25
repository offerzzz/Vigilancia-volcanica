package ec.edu.udla.vigilancia;

import java.util.ArrayList;

public class RedMonitoreo {

    private String nombre;
    private ArrayList<Volcan> volcanes;
    private ArrayList<Estacion> estaciones;

    public RedMonitoreo() {
        this.nombre = "Sin nombre";
        this.volcanes = new ArrayList<>();
        this.estaciones = new ArrayList<>();
    }

    public RedMonitoreo(String nombre) {
        this();
        setNombre(nombre);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre de la red no puede estar vacío.");
            return;
        }
        this.nombre = nombre.trim();
    }

    public ArrayList<Volcan> getVolcanes() {
        return new ArrayList<>(volcanes);
    }

    public void setVolcanes(ArrayList<Volcan> nuevos) {
        if (nuevos == null) {
            System.out.println("La lista de volcanes no puede ser nula.");
            return;
        }
        this.volcanes = new ArrayList<>();
        for (int i = 0; i < nuevos.size(); i++) {
            registrarVolcan(nuevos.get(i));
        }
    }

    public ArrayList<Estacion> getEstaciones() {
        return new ArrayList<>(estaciones);
    }

    public void setEstaciones(ArrayList<Estacion> nuevas) {
        if (nuevas == null) {
            System.out.println("La lista de estaciones no puede ser nula.");
            return;
        }
        this.estaciones = new ArrayList<>();
        for (int i = 0; i < nuevas.size(); i++) {
            registrarEstacion(nuevas.get(i));
        }
    }

    public boolean registrarVolcan(Volcan volcan) {
        if (volcan == null) {
            System.out.println("No se puede registrar un volcán nulo.");
            return false;
        }
        for (int i = 0; i < volcanes.size(); i++) {
            if (volcanes.get(i).getNombre().equalsIgnoreCase(volcan.getNombre())) {
                System.out.println("El volcán " + volcan.getNombre() + " ya está registrado en la red.");
                return false;
            }
        }
        volcanes.add(volcan);
        return true;
    }

    public boolean registrarEstacion(Estacion estacion) {
        if (estacion == null || estacion.getCodigo() == null) {
            System.out.println("No se puede registrar una estación nula o sin código válido.");
            return false;
        }
        for (int i = 0; i < estaciones.size(); i++) {
            if (estaciones.get(i).getCodigo().equalsIgnoreCase(estacion.getCodigo())) {
                System.out.println("La estación " + estacion.getCodigo() + " ya está registrada en la red.");
                return false;
            }
        }
        estaciones.add(estacion);
        return true;
    }

    public Sensor buscarSensor(String codigoSensor) {
        for (int i = 0; i < estaciones.size(); i++) {
            Sensor sensor = estaciones.get(i).buscarSensor(codigoSensor);
            if (sensor != null) {
                return sensor;
            }
        }
        return null;
    }

    public Estacion buscarEstacionDeSensor(String codigoSensor) {
        for (int i = 0; i < estaciones.size(); i++) {
            if (estaciones.get(i).buscarSensor(codigoSensor) != null) {
                return estaciones.get(i);
            }
        }
        return null;
    }

    public int evaluarVolcan(Volcan volcan) {
        if (volcan == null) {
            System.out.println("No se puede evaluar un volcán nulo.");
            return 0;
        }
        int sensoresEnAlerta = 0;
        for (int i = 0; i < estaciones.size(); i++) {
            if (estaciones.get(i).getVolcan() == volcan) {
                sensoresEnAlerta += estaciones.get(i).contarSensoresEnAlerta();
            }
        }
        volcan.actualizarNivelAlerta(sensoresEnAlerta);
        return sensoresEnAlerta;
    }

    public String generarReporte() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("REPORTE DE LA RED: ").append(nombre).append("\n");
        for (int i = 0; i < volcanes.size(); i++) {
            Volcan volcan = volcanes.get(i);
            reporte.append("\nVolcán ").append(volcan.getNombre()).append(" (").append(volcan.getProvincia())
                    .append(") - Alerta ").append(volcan.getNivelAlerta()).append("\n");
            int vigilantes = 0;
            for (int j = 0; j < estaciones.size(); j++) {
                Estacion estacion = estaciones.get(j);
                if (estacion.getVolcan() == volcan) {
                    vigilantes++;
                    reporte.append("   ").append(estacion.getCodigo()).append(" ").append(estacion.getNombre())
                            .append(": ").append(estacion.contarSensores()).append(" sensores, ")
                            .append(estacion.contarSensoresEnAlerta()).append(" en alerta\n");
                }
            }
            if (vigilantes == 0) {
                reporte.append("   Sin estaciones que lo vigilen\n");
            }
        }
        return reporte.toString();
    }

    @Override
    public String toString() {
        return nombre + " | Volcanes: " + volcanes.size() + " | Estaciones: " + estaciones.size();
    }
}
