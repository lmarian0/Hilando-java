package main;

public abstract class Proceso extends EmpresaLogistica implements Runnable{
    private EmpresaLogistica eCommerce;

    public Proceso() {
        run();
    }
}
