package main;

/**
 * Ejecuta procesos para la ejecucion de la EMpresa Logistica.
 */

public abstract class Proceso implements Runnable {
    protected EmpresaLogistica eCommerce; // Ejecuta el eCommerce de la empresa

    /**Constructor del proceso
     * runnea un hilo*/
    public Proceso(EmpresaLogistica eCommerce) {
        this.eCommerce = eCommerce;
    }

    @Override
    public void run() {
        System.out.println(eCommerce.getClass().getName());
    }

}
