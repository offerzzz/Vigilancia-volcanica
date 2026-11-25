package ec.edu.udla.vigilancia;

import java.util.ArrayList;

public class Estacion {

    public static final double KM_POR_GRADO = 111.32;

    private String codigo;
    private String nombre;
    private Ubicacion ubicacion;
    private Volcan volcan;
    private ArrayList<Sensor> sensores;

    public Estacion() {
        this.codigo = null;
        this.nombre = "Sin nombre";
        this.ubicacion = new Ubicacion();
        this.volcan = null;
        this.sensores = new ArrayList<>();
    }

    public Estacion(String codigo, String nombre, double latitud, double longitud, double altitud, Volcan volcan) {
        this();
        setCodigo(codigo);
        setNombre(nombre);
        this.ubicacion = new Ubicacion(latitud, longitud, altitud);
        setVolcan(volcan);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == null) {
            System.out.println("Código de estación inválido: no puede ser nulo (formato EST-##).");
            return;
        }
        String c = codigo.trim().toUpperCase();
        if (c.length() != 6 || !c.startsWith("EST-")) {
            System.out.println("Código de estación inválido: \"" + codigo + "\". El formato correcto es EST-##.");
            return;
        }
        if (!Character.isDigit(c.charAt(4)) || !Character.isDigit(c.charAt(5))) {
            System.out.println("Código de estación inválido: \"" + codigo + "\". Debe terminar en dos dígitos.");
            return;
        }
        this.codigo = c;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre de la estación no puede estar vacío.");
            return;
        }
        this.nombre = nombre.trim();
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        if (ubicacion == null) {
            System.out.println("La estación necesita una ubicación válida.");
            return;
        }
        this.ubicacion = new Ubicacion(ubicacion.getLatitud(), ubicacion.getLongitud(), ubicacion.getAltitud());
    }

    public Volcan getVolcan() {
        return volcan;
    }

    public void setVolcan(Volcan volcan) {
        if (volcan == null) {
            System.out.println("La estación " + codigo + " debe vigilar exactamente un volcán.");
            return;
        }
        this.volcan = volcan;
    }

    public ArrayList<Sensor> getSensores() {
        return new ArrayList<>(sensores);
    }

    public void setSensores(ArrayList<Sensor> nuevos) {
        if (nuevos == null) {
            System.out.println("La lista de sensores no puede ser nula.");
            return;
        }
        this.sensores = new ArrayList<>();
        for (int i = 0; i < nuevos.size(); i++) {
            agregarSensor(nuevos.get(i));
        }
    }

    public boolean agregarSensor(Sensor sensor) {
        if (sensor == null) {
            System.out.println("No se puede instalar un sensor nulo en la estación " + codigo + ".");
            return false;
        }
        if (sensor.getCodigo() == null) {
            System.out.println("No se puede instalar en la estación " + codigo + " un sensor sin código válido.");
            return false;
        }
        if (buscarSensor(sensor.getCodigo()) != null) {
            System.out.println("Sensor rechazado: la estación " + codigo + " ya tiene instalado el sensor "
                    + sensor.getCodigo() + ".");
            return false;
        }
        sensores.add(sensor);
        return true;
    }

    public Sensor retirarSensor(String codigoSensor) {
        if (codigoSensor == null) {
            System.out.println("El código del sensor a retirar no puede ser nulo.");
            return null;
        }
        for (int i = 0; i < sensores.size(); i++) {
            if (sensores.get(i).getCodigo().equalsIgnoreCase(codigoSensor.trim())) {
                return sensores.remove(i);
            }
        }
        System.out.println("La estación " + codigo + " no tiene instalado el sensor " + codigoSensor + ".");
        return null;
    }

    public Sensor buscarSensor(String codigoSensor) {
        if (codigoSensor == null) {
            return null;
        }
        for (int i = 0; i < sensores.size(); i++) {
            if (sensores.get(i).getCodigo().equalsIgnoreCase(codigoSensor.trim())) {
                return sensores.get(i);
            }
        }
        return null;
    }

    public int contarSensores() {
        return sensores.size();
    }

    public int contarSensoresEnAlerta() {
        int cantidad = 0;
        for (int i = 0; i < sensores.size(); i++) {
            if (sensores.get(i).estaEnAlerta()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public double calcularDistanciaAlCraterKm() {
        if (volcan == null) {
            System.out.println("La estación " + codigo + " no tiene volcán asignado; distancia = 0.");
            return 0;
        }
        double deltaLat = volcan.getLatitud() - ubicacion.getLatitud();
        double deltaLon = volcan.getLongitud() - ubicacion.getLongitud();
        double factor = Math.cos(Math.toRadians(ubicacion.getLatitud()));
        return KM_POR_GRADO * Math.sqrt(Math.pow(deltaLat, 2) + Math.pow(deltaLon * factor, 2));
    }

    @Override
    public String toString() {
        String nombreVolcan = volcan == null ? "sin asignar" : volcan.getNombre();
        return codigo + " | " + nombre + " | " + ubicacion + " | Vigila: " + nombreVolcan
                + " | Sensores: " + sensores.size();
    }
}
