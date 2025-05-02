package main;

/**
 * Ejecuta procesos para la ejecucion de la EMpresa Logistica.
 */

public abstract class Proceso extends EmpresaLogistica implements Runnable {
    protected EmpresaLogistica eCommerce; // Ejecuta el eCommerce de la empresa
    protected Casilleros casillero = new Casilleros();
    protected RegistroPedidos registro = new RegistroPedidos(); //registro de pedidos

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
