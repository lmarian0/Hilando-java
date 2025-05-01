
public class PrepararPedido extends Proceso{

    public PrepararPedido(EmpresaLogistica eCommerce) {
        super(eCommerce);
    }
    @Override
    public void run() {
        eCommerce.buscarCasilleroLibre(); // método dentro de Empresa que hace la lógica real
    }
}
