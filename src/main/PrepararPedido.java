// package main;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

public class PrepararPedido extends Proceso{
    private Random random = new Random();
    private List<Pedido> pedidosIniciales = new ArrayList<>(); 

    public PrepararPedido(EmpresaLogistica eCommerce,List<Pedido> pedidosIniciales) {
        super(eCommerce);
        this.pedidosIniciales = pedidosIniciales; 

    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Pedido pedido = null;

                if (!pedidosIniciales.isEmpty()) {
                    pedido = buscarPedido(); // Tomar y eliminar el pedido de la lista
                }

                if (pedido != null ) {
                    Casilleros casillero = buscarCasilleroLibre(); // Buscar un casillero libre
                    
                    if (casillero != null) {
                        casillero.setPedido(pedido); // Asignar el pedido al casillero
                        pedido.setCasilleroAsociado(casillero);
                        eCommerce.getRegistroPedidos().addPreparacion(pedido);
                        System.out.println(Thread.currentThread().getName() + " preparo el pedido " + pedido.getId() + " en el casillero " + casillero.getId());
                        TimeUnit.MILLISECONDS.sleep(50); // Simular tiempo de preparación del pedido
                    } else {
                        System.out.println("No hay casilleros libres para el pedido " + pedido.getId());
                        break; // Salir del bucle si no hay casilleros libres
                    }
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

    private Casilleros buscarCasilleroLibre() {
    
        while (pedidosIniciales.size() > 0) {
            int numRand = random.nextInt(200); // Generar un índice aleatorio
            Casilleros casillero = eCommerce.getCasillero(numRand);
            synchronized(casillero){
                if (casillero.getEstado() == EstadoCasillero.VACIO) {
                    return casillero;
                }
            }
        }
        return null; // Si no hay casilleros libres, retornar null
    }

    private Pedido buscarPedido() {
        synchronized(pedidosIniciales) {
            if (pedidosIniciales.isEmpty()) {
                throw new IllegalStateException("No hay más pedidos en la lista.");
            }
            Pedido pedido = pedidosIniciales.remove(0);
            return pedido;
        }
        
    }
}
