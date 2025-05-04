//package main;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class VerificarPedido extends Proceso{
    private List<Pedido> lista= eCommerce.getRegistroPedidos().getEntregados();

    public VerificarPedido(EmpresaLogistica eCommerce) {
        super(eCommerce);
    }
    @Override
    public void run() {
        Pedido pedido = null;

        synchronized (this) {
            while (lista.isEmpty()) {
                try {
                    System.out.println("No hay pedidos para verificar. Esperando...");
                    wait(5000); // Espera 5 segundos antes de volver a intentar
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrumpido.");
                    Thread.currentThread().interrupt(); // Mantiene la interrupción para manejo posterior
                    return; // Sale del método si la interrupción es crítica
                }
            }

            // Sale de la espera y notifica a otros hilos
            notifyAll();
        }

        synchronized (lista) { // Asegura la sincronización adecuada
            notifyAll();
        }

        int index = ThreadLocalRandom.current().nextInt(lista.size());
        pedido = lista.get(index);

        if (verificarDatos() && pedido.getEstado() == EstadoPedido.ENTREGADO) {
            eCommerce.getRegistroPedidos().delEntregados(pedido);
            eCommerce.getRegistroPedidos().addVerificados(pedido);
        } else {
            eCommerce.getRegistroPedidos().delEntregados(pedido);
            eCommerce.getRegistroPedidos().addFallidos(pedido);
        }
    }

    private boolean verificarDatos(){
        ThreadLocalRandom probabilidad = ThreadLocalRandom.current();
        probabilidad.nextDouble(1);
        return probabilidad.nextDouble() <= 0.95;
    }
    
}
