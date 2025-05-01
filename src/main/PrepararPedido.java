package main;

public class PrepararPedido extends Proceso{

    public PrepararPedido(EmpresaLogistica eCommerce) {
        super(eCommerce);
    }
    @Override
    public void run() {
        eCommerce.buscarCasillero(); // método dentro de Empresa que hace la lógica real
    }
}
