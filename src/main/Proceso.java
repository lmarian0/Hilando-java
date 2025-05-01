package main;

/**
 * Ejecuta procesos para la ejecucion de la EMpresa Logistica.
 */

public abstract class Proceso extends EmpresaLogistica implements Runnable{
    private EmpresaLogistica eCommerce; // Ejecuta el eCommerce de la empresa

    /**Constructor del proceso
     * runnea un hilo*/
    public Proceso() {
        run();
    }
}
