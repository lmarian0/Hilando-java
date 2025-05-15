// package main;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PrepararPedido extends Proceso{
    private Random random = new Random();
    private GeneradorPedidos pedidosIniciales = new GeneradorPedidos(); 
    private static final Object buscador_key = new Object();

    public PrepararPedido(EmpresaLogistica eCommerce,GeneradorPedidos pedidosIniciales) {
        super(eCommerce);
        this.pedidosIniciales = pedidosIniciales; 

    }


    @Override
    public void run() {
        while (true) {
            try {
                Pedido pedido = pedidosIniciales.obtenerPedido(); // 2 hilos no pueden tomar un pedido al mismo tiempoo
                if(pedido == null){
                    System.out.println("No se puede preparar un pedido nulo");
                    return;
                }
                ubicarPedido(pedido); // 2 hilos no pueden asignar un casillero al mismo tiempo
                eCommerce.getRegistroPedidos().addPreparacion(pedido);  
                TimeUnit.MILLISECONDS.sleep(50);
                    
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrumpido.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    // The `private void ubicarPedido(Pedido pedido)` method is responsible for assigning a casillero
    // (storage compartment) to a given pedido (order) within the eCommerce system. Here's a breakdown
    // of what the method does:
    private void ubicarPedido(Pedido pedido) {
        synchronized (buscador_key) {
            while (true) {
                //System.out.println(Thread.currentThread().getName() + " - index_key hash: " + System.identityHashCode(buscador_key));
                int numRand = random.nextInt(200);                
                Casilleros casillero = eCommerce.getCasillero(numRand);

                if (casillero.getEstado() == EstadoCasillero.VACIO) {
                    casillero.setPedido(pedido);                        
                    casillero.setEstado(EstadoCasillero.OCUPADO);       
                    pedido.setCasilleroAsociado(casillero);
                    System.out.println(Thread.currentThread().getName() + " ha preparado el pedido " + pedido.getId() + " en el casillero [" + casillero.getId() + "]");
                    break; 
                }
            }   
        }
    }
}
