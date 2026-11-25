package ec.edu.udla.vigilancia;

import java.util.Locale;

public class Sismometro extends Sensor {

    private double distanciaCraterKm;

    public Sismometro() {
        super("SIS-", "Sismómetro", "nm");
        this.distanciaCraterKm = 0;
    }

    public Sismometro(String codigo, double umbral) {
        super("SIS-", "Sismómetro", "nm", codigo, umbral);
        this.distanciaCraterKm = 0;
    }

    public Sismometro(String codigo, double umbral, double distanciaCraterKm) {
        this(codigo, umbral);
        setDistanciaCraterKm(distanciaCraterKm);
    }

    public double getDistanciaCraterKm() {
        return distanciaCraterKm;
    }

    public void setDistanciaCraterKm(double distanciaCraterKm) {
        if (Double.isNaN(distanciaCraterKm) || distanciaCraterKm < 0) {
            System.out.println("Distancia inválida (" + distanciaCraterKm + "): no puede ser negativa.");
            return;
        }
        this.distanciaCraterKm = distanciaCraterKm;
    }

    public double calcularMagnitudLocal() {
        if (distanciaCraterKm <= 0) {
            System.out.println("Aviso: el sismómetro " + getCodigo() + " no tiene distancia al cráter asignada; magnitud = 0.");
            return 0;
        }
        double amplitud = obtenerMaximo();
        if (amplitud <= 0) {
            System.out.println("Aviso: el sismómetro " + getCodigo() + " no tiene lecturas positivas; magnitud = 0.");
            return 0;
        }
        return Math.log10(amplitud) + 1.11 * Math.log10(distanciaCraterKm) + 0.00189 * distanciaCraterKm - 2.09;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(Locale.US, " | Distancia al cráter: %.3f km", distanciaCraterKm);
    }
}
