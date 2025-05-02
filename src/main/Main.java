package main;

public class Main {
    public static void main(String[] args) {

        System.out.println("hola mundo");
        EmpresaLogistica eCommerce = new EmpresaLogistica();
        Proceso proceso = new DespacharPedido(eCommerce);
        proceso.run();

    }
}
