//package main;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;

public class VerificarPedido extends Proceso{
    public VerificarPedido(EmpresaLogistica eCommerce) {
        super(eCommerce);
    }

private static final Object index_key = new Object();
private static final Object select_key = new Object();

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Pedido pedido;
                // Seleccionar un pedido aleatorio
                synchronized (index_key) {
                    if (eCommerce.getRegistroPedidos().getEntregados().isEmpty()) {
                        continue;
                    }
                    int index = ThreadLocalRandom.current().nextInt(eCommerce.getRegistroPedidos().getEntregados().size());
                    pedido = eCommerce.getRegistroPedidos().getEntregados().get(index);
                    eCommerce.getRegistroPedidos().delEntregados(pedido);
                }

                synchronized (select_key) {
                    if (verificarDatos()) {
                        eCommerce.getRegistroPedidos().addVerificados(pedido);
                        System.out.println("Pedido " + pedido.getId() +" verificado correctamente.");
                    } else {
                        eCommerce.getRegistroPedidos().addFallidos(pedido);
                        System.out.println("Pedido " + pedido.getId()+ " fallido en la verificación.");
                    }
                }
                TimeUnit.MILLISECONDS.sleep(10);
            }catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrumpido.");
                Thread.currentThread().interrupt(); // Restablecer el estado de interrupción
                break;
            }
        } 
    }
    
    private boolean verificarDatos() {
        return ThreadLocalRandom.current().nextDouble() <= 0.95;
    }
}