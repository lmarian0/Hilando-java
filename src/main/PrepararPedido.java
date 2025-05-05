// package main;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

public class PrepararPedido extends Proceso{
    private Random random = new Random();
    private List<Pedido> pedidosIniciales = new ArrayList<>(); // Lista de pedidos iniciales

    public PrepararPedido(EmpresaLogistica eCommerce,List<Pedido> pedidosIniciales) {
        super(eCommerce);
        this.pedidosIniciales = pedidosIniciales; //inicializa la lista de pedidos que llegaron a la empresa

    }

    Object control = new Object(); // Objeto de control para la sincronización

    @Override
    public void run() {
        while (true) {
            try {
                Pedido pedido = null;

                // Sincronizar el acceso a la lista de pedidos
                synchronized (control) {
                    if (!pedidosIniciales.isEmpty()) {
                        pedido = buscarPedido(); // Tomar y eliminar el pedido de la lista
                    }
                }

                if (pedido != null) {
                    Casilleros casillero = buscarCasilleroLibre(); // Buscar un casillero libre
                    
                    casillero.setPedido(pedido); // Asignar el pedido al casillero
                    System.out.println(Thread.currentThread().getName() + " guardó el pedido " + casillero.getPedido().getId() + " en el casillero " + casillero.getId());
                    TimeUnit.MILLISECONDS.sleep(100); // Simular tiempo de preparación del pedido
                    
                } else {
                    break; // Si no hay más pedidos, salir del bucle
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrumpido.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private synchronized boolean verifVacio(Casilleros casillero){
        if(casillero.getEstado() == EstadoCasillero.VACIO){
            casillero.setEstado(EstadoCasillero.OCUPADO); // Cambiar el estado del casillero a OCUPADO
            return true;
        }else{
            return false;
        }
    }

    private Casilleros buscarCasilleroLibre() {
    
        while (true) {
            int numRand = random.nextInt(200); // Generar un índice aleatorio
            Casilleros casillero = eCommerce.getCasillero(numRand);
            if (verifVacio(casillero)) {
                return casillero;
            }
        }
    }

    private Pedido buscarPedido(){
        Pedido pedido = pedidosIniciales.remove(0); // Tomar el pedido y eliminarlo de la lista de pedidos iniciales
        System.out.println(Thread.currentThread().getName() + " agarro el pedido " + pedido.getId());
        return pedido;
    }
}
