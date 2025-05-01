
/**
 * Ejecuta procesos para la ejecucion de la EMpresa Logistica.
 */

public abstract class Proceso extends EmpresaLogistica implements Runnable{
    protected EmpresaLogistica eCommerce; // Ejecuta el eCommerce de la empresa

    /**Constructor del proceso
     * runnea un hilo*/
    public Proceso(EmpresaLogistica eCommerce) {
        this.eCommerce = eCommerce;
    }
}
