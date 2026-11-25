package ec.edu.udla.vigilancia;

import java.util.Locale;

public class SensorGas extends Sensor {

    public static final double K_SO2 = 2.62;
    public static final double K_CO2 = 1.80;

    private String tipoGas;
    private double velocidadViento;

    public SensorGas() {
        super("GAS-", "Sensor de gas", "ppm");
        this.tipoGas = "SO2";
        this.velocidadViento = 0;
    }

    public SensorGas(String codigo, double umbral, String tipoGas, double velocidadViento) {
        super("GAS-", "Sensor de gas", "ppm", codigo, umbral);
        this.tipoGas = "SO2";
        this.velocidadViento = 0;
        setTipoGas(tipoGas);
        setVelocidadViento(velocidadViento);
    }

    public String getTipoGas() {
        return tipoGas;
    }

    public void setTipoGas(String tipoGas) {
        if (tipoGas == null) {
            System.out.println("El tipo de gas no puede ser nulo. Solo se acepta SO2 o CO2.");
            return;
        }
        String gas = tipoGas.trim().toUpperCase();
        if (gas.equals("SO2") || gas.equals("CO2")) {
            this.tipoGas = gas;
        } else {
            System.out.println("Tipo de gas inválido: \"" + tipoGas + "\". Solo se acepta SO2 o CO2.");
        }
    }

    public double getVelocidadViento() {
        return velocidadViento;
    }

    public void setVelocidadViento(double velocidadViento) {
        if (Double.isNaN(velocidadViento) || velocidadViento < 0) {
            System.out.println("Velocidad de viento inválida (" + velocidadViento + "): no puede ser negativa.");
            return;
        }
        this.velocidadViento = velocidadViento;
    }

    public double calcularFlujoDiario() {
        double k = tipoGas.equals("SO2") ? K_SO2 : K_CO2;
        return calcularPromedio() * velocidadViento * k;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(Locale.US, " | Gas: %s | Viento: %.2f m/s", tipoGas, velocidadViento);
    }
}
