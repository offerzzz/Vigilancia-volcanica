package ec.edu.udla.vigilancia;

import java.util.Arrays;
import java.util.Locale;

public class Sensor {

    public static final int CAPACIDAD = 24;

    private String prefijo;
    private String tipoSensor;
    private String codigo;
    private String unidad;
    private double umbral;
    private boolean activo;
    private double[] lecturas;
    private int numLecturas;

    public Sensor() {
        this.prefijo = "SEN-";
        this.tipoSensor = "Sensor";
        this.codigo = null;
        this.unidad = "u";
        this.umbral = 1.0;
        this.activo = true;
        this.lecturas = new double[CAPACIDAD];
        this.numLecturas = 0;
    }

    public Sensor(String prefijo, String tipoSensor, String unidad) {
        this();
        setPrefijo(prefijo);
        setTipoSensor(tipoSensor);
        setUnidad(unidad);
    }

    public Sensor(String prefijo, String tipoSensor, String unidad, String codigo, double umbral) {
        this(prefijo, tipoSensor, unidad);
        setCodigo(codigo);
        setUmbral(umbral);
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        if (prefijo == null || prefijo.trim().isEmpty()) {
            System.out.println("El prefijo de código no puede estar vacío.");
            return;
        }
        this.prefijo = prefijo.trim().toUpperCase();
    }

    public String getTipoSensor() {
        return tipoSensor;
    }

    public void setTipoSensor(String tipoSensor) {
        if (tipoSensor == null || tipoSensor.trim().isEmpty()) {
            System.out.println("El tipo de sensor no puede estar vacío.");
            return;
        }
        this.tipoSensor = tipoSensor.trim();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == null) {
            System.out.println("Código de sensor inválido: no puede ser nulo (formato " + prefijo + "###).");
            return;
        }
        String c = codigo.trim().toUpperCase();
        if (c.length() != prefijo.length() + 3 || !c.startsWith(prefijo)) {
            System.out.println("Código de sensor inválido: \"" + codigo + "\". El formato correcto es " + prefijo + "###.");
            return;
        }
        String digitos = c.substring(prefijo.length());
        for (int i = 0; i < digitos.length(); i++) {
            if (!Character.isDigit(digitos.charAt(i))) {
                System.out.println("Código de sensor inválido: \"" + codigo + "\". Debe terminar en tres dígitos.");
                return;
            }
        }
        this.codigo = c;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        if (unidad == null || unidad.trim().isEmpty()) {
            System.out.println("La unidad de medida no puede estar vacía.");
            return;
        }
        this.unidad = unidad.trim();
    }

    public double getUmbral() {
        return umbral;
    }

    public void setUmbral(double umbral) {
        if (Double.isNaN(umbral) || umbral <= 0) {
            System.out.println("Umbral inválido (" + umbral + "): el umbral de alerta debe ser mayor que 0.");
            return;
        }
        this.umbral = umbral;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public double[] getLecturas() {
        return Arrays.copyOf(lecturas, numLecturas);
    }

    public void setLecturas(double[] lote) {
        this.lecturas = new double[CAPACIDAD];
        this.numLecturas = 0;
        registrarLectura(lote);
    }

    public int getNumLecturas() {
        return numLecturas;
    }

    public boolean registrarLectura(double lectura) {
        if (Double.isNaN(lectura) || Double.isInfinite(lectura) || lectura < 0) {
            System.out.println("Lectura " + lectura + " rechazada en " + codigo + ": no se aceptan lecturas negativas.");
            return false;
        }
        if (numLecturas >= lecturas.length) {
            System.out.println("El sensor " + codigo + " tiene el arreglo de lecturas lleno (" + CAPACIDAD
                    + "): la lectura " + lectura + " no se registró.");
            return false;
        }
        lecturas[numLecturas] = lectura;
        numLecturas++;
        return true;
    }

    public int registrarLectura(double[] lote) {
        if (lote == null) {
            System.out.println("El lote de lecturas no puede ser nulo.");
            return 0;
        }
        int registradas = 0;
        for (int i = 0; i < lote.length; i++) {
            if (registrarLectura(lote[i])) {
                registradas++;
            }
        }
        return registradas;
    }

    public double calcularPromedio() {
        if (numLecturas == 0) {
            return 0;
        }
        double suma = 0;
        for (int i = 0; i < numLecturas; i++) {
            suma += lecturas[i];
        }
        return suma / numLecturas;
    }

    public double obtenerMaximo() {
        if (numLecturas == 0) {
            return 0;
        }
        double maximo = lecturas[0];
        for (int i = 1; i < numLecturas; i++) {
            if (lecturas[i] > maximo) {
                maximo = lecturas[i];
            }
        }
        return maximo;
    }

    public int contarSobreUmbral() {
        int cantidad = 0;
        for (int i = 0; i < numLecturas; i++) {
            if (lecturas[i] > umbral) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public boolean estaEnAlerta() {
        if (!activo || numLecturas == 0) {
            return false;
        }
        return calcularPromedio() > umbral || obtenerMaximo() > 1.5 * umbral;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s | %s | Umbral: %.2f %s | Estado: %s | Lecturas: %d/%d",
                codigo, tipoSensor, umbral, unidad, activo ? "ACTIVO" : "EN MANTENIMIENTO", numLecturas, CAPACIDAD);
    }
}
