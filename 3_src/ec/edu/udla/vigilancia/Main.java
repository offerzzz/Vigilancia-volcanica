package ec.edu.udla.vigilancia;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;

public class Main {

    private static void titulo(int numero, String texto) {
        System.out.println();
        System.out.println("=== " + numero + ". " + texto + " ===");
    }

    private static void tablaEstacion(Estacion estacion) {
        System.out.println();
        System.out.println("Estación " + estacion.getCodigo() + " - " + estacion.getNombre());
        System.out.printf(Locale.US, "%-9s %-14s %10s %10s %12s %-7s%n",
                "Código", "Tipo", "Promedio", "Máximo", "Sobre umbral", "Alerta");
        ArrayList<Sensor> sensores = estacion.getSensores();
        for (int i = 0; i < sensores.size(); i++) {
            Sensor s = sensores.get(i);
            System.out.printf(Locale.US, "%-9s %-14s %10.2f %10.2f %12d %-7s%n",
                    s.getCodigo(), s.getTipoSensor(), s.calcularPromedio(), s.obtenerMaximo(),
                    s.contarSobreUmbral(), s.estaEnAlerta() ? "SÍ" : "NO");
        }
    }

    private static void mostrarNivel(RedMonitoreo red, Volcan volcan) {
        int enAlerta = red.evaluarVolcan(volcan);
        System.out.println("Volcán " + volcan.getNombre() + ": " + enAlerta + " sensor(es) activo(s) en alerta -> nivel "
                + volcan.getNivelAlerta());
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        titulo(1, "Creación de la red, volcanes y estaciones");
        RedMonitoreo red = new RedMonitoreo("Red Nacional de Vigilancia Volcánica");
        Volcan cotopaxi = new Volcan("Cotopaxi", "Cotopaxi", -0.6838, -78.4375);
        Volcan sangay = new Volcan("Sangay", "Morona Santiago", -2.0050, -78.3410);
        Estacion est01 = new Estacion("EST-01", "Refugio Norte", -0.6500, -78.4400, 4800, cotopaxi);
        Estacion est02 = new Estacion("EST-02", "Mirador Sur", -0.7200, -78.4100, 4200, cotopaxi);
        Estacion est03 = new Estacion("EST-03", "Ladera Este", -1.9800, -78.3000, 3600, sangay);
        red.registrarVolcan(cotopaxi);
        red.registrarVolcan(sangay);
        red.registrarEstacion(est01);
        red.registrarEstacion(est02);
        red.registrarEstacion(est03);
        System.out.println(red);
        System.out.println(cotopaxi);
        System.out.println(sangay);
        System.out.println(est01);
        System.out.println(est02);
        System.out.println(est03);

        titulo(2, "Creación de sensores e instalación en estaciones");
        Sismometro sis101 = new Sismometro("SIS-101", 5000);
        SensorGas gas102 = new SensorGas("GAS-102", 2.0, "SO2", 6.5);
        Sismometro sis201 = new Sismometro("SIS-201", 5000);
        SensorGas gas202 = new SensorGas("GAS-202", 2.0, "SO2", 4.0);
        Sismometro sis301 = new Sismometro("SIS-301", 5000);
        SensorGas gas302 = new SensorGas("GAS-302", 600, "CO2", 3.2);
        est01.agregarSensor(sis101);
        est01.agregarSensor(gas102);
        est02.agregarSensor(sis201);
        est02.agregarSensor(gas202);
        est03.agregarSensor(sis301);
        est03.agregarSensor(gas302);
        System.out.println(est01);
        System.out.println(est02);
        System.out.println(est03);
        System.out.println("Intento de agregar otra vez SIS-101 a EST-01:");
        est01.agregarSensor(sis101);

        titulo(3, "Registro de lecturas (individual y por lote)");
        sis101.registrarLectura(new double[]{1200, 3400, 8200, 4100, 2600, 9100});
        gas102.registrarLectura(1.1);
        gas102.registrarLectura(1.4);
        gas102.registrarLectura(1.9);
        gas102.registrarLectura(2.3);
        gas102.registrarLectura(2.0);
        sis201.registrarLectura(new double[]{6100, 5800, 7000, 4900});
        gas202.registrarLectura(2.1);
        gas202.registrarLectura(1.8);
        gas202.registrarLectura(1.9);
        sis301.registrarLectura(new double[]{800, 1500, 1100});
        gas302.registrarLectura(420);
        gas302.registrarLectura(450);
        gas302.registrarLectura(480);
        System.out.println(sis101);
        System.out.println(gas102);
        System.out.println(sis201);
        System.out.println(gas202);
        System.out.println(sis301);
        System.out.println(gas302);

        titulo(4, "Casos inválidos");
        System.out.println("1) Agregar otra vez SIS-101 a EST-01:");
        est01.agregarSensor(sis101);
        System.out.println("2) Registrar la lectura -50 en SIS-301:");
        sis301.registrarLectura(-50);
        System.out.println("3) Crear un sismómetro con el código sis-12:");
        Sismometro invalido = new Sismometro("sis-12", 5000);
        if (invalido.getCodigo() == null) {
            System.out.println("   El sismómetro no quedó con un código válido y no puede instalarse.");
        }
        System.out.println("4) Asignar el tipo de gas H2S a GAS-102:");
        gas102.setTipoGas("H2S");
        System.out.println("5) Asignar un umbral de 0 a GAS-302:");
        gas302.setUmbral(0);

        titulo(5, "Distancia de cada estación al cráter de su volcán");
        double distancia01 = est01.calcularDistanciaAlCraterKm();
        double distancia02 = est02.calcularDistanciaAlCraterKm();
        double distancia03 = est03.calcularDistanciaAlCraterKm();
        sis101.setDistanciaCraterKm(distancia01);
        sis201.setDistanciaCraterKm(distancia02);
        sis301.setDistanciaCraterKm(distancia03);
        System.out.printf(Locale.US, "EST-01 -> %s: %.3f km (asignada a %s)%n", cotopaxi.getNombre(), distancia01, sis101.getCodigo());
        System.out.printf(Locale.US, "EST-02 -> %s: %.3f km (asignada a %s)%n", cotopaxi.getNombre(), distancia02, sis201.getCodigo());
        System.out.printf(Locale.US, "EST-03 -> %s: %.3f km (asignada a %s)%n", sangay.getNombre(), distancia03, sis301.getCodigo());

        titulo(6, "Sensores por estación");
        tablaEstacion(est01);
        tablaEstacion(est02);
        tablaEstacion(est03);

        titulo(7, "Magnitud local y flujo diario de gas");
        System.out.printf(Locale.US, "%s: magnitud local = %.2f%n", sis101.getCodigo(), sis101.calcularMagnitudLocal());
        System.out.printf(Locale.US, "%s: magnitud local = %.2f%n", sis201.getCodigo(), sis201.calcularMagnitudLocal());
        System.out.printf(Locale.US, "%s: magnitud local = %.2f%n", sis301.getCodigo(), sis301.calcularMagnitudLocal());
        System.out.printf(Locale.US, "%s: flujo diario = %.2f t/día%n", gas102.getCodigo(), gas102.calcularFlujoDiario());
        System.out.printf(Locale.US, "%s: flujo diario = %.2f t/día%n", gas202.getCodigo(), gas202.calcularFlujoDiario());
        System.out.printf(Locale.US, "%s: flujo diario = %.2f t/día%n", gas302.getCodigo(), gas302.calcularFlujoDiario());

        titulo(8, "Evaluación de los volcanes");
        mostrarNivel(red, cotopaxi);
        mostrarNivel(red, sangay);

        titulo(9, "Mantenimiento de SIS-101");
        Sensor retirado = est01.retirarSensor("SIS-101");
        retirado.setActivo(false);
        System.out.println("Sensor retirado de EST-01: " + retirado);
        System.out.println("EST-01 ahora tiene " + est01.contarSensores() + " sensor(es).");
        mostrarNivel(red, cotopaxi);

        titulo(10, "Reinstalación de SIS-101 en EST-02");
        est02.agregarSensor(sis101);
        sis101.setActivo(true);
        sis101.setDistanciaCraterKm(est02.calcularDistanciaAlCraterKm());
        System.out.println(sis101);
        System.out.println("EST-02 ahora tiene " + est02.contarSensores() + " sensor(es).");
        mostrarNivel(red, cotopaxi);

        titulo(11, "Búsqueda de sensores en la red");
        String[] codigos = {"GAS-202", "SIS-999"};
        for (int i = 0; i < codigos.length; i++) {
            Sensor encontrado = red.buscarSensor(codigos[i]);
            if (encontrado != null) {
                Estacion donde = red.buscarEstacionDeSensor(codigos[i]);
                System.out.println(codigos[i] + " encontrado en " + donde.getCodigo() + ": " + encontrado);
            } else {
                System.out.println(codigos[i] + " no existe en la red.");
            }
        }

        titulo(12, "Reporte final de la red");
        System.out.print(red.generarReporte());
    }
}
