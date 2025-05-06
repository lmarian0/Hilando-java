//package main;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;

public class VerificarPedido extends Proceso{
    public VerificarPedido(EmpresaLogistica eCommerce) {
        super(eCommerce);
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                List<Pedido> lista;
    
                // Sincronizar el acceso a la lista de pedidos entregados
                synchronized (eCommerce.getRegistroPedidos()) {
                    lista = eCommerce.getRegistroPedidos().getEntregados();
                    if (lista.isEmpty()) {
                        continue;
                    }
                }
    
                // Seleccionar un pedido aleatorio
                Pedido pedido;
                synchronized (eCommerce.getRegistroPedidos()) {
                    int index = ThreadLocalRandom.current().nextInt(lista.size());
                    pedido = lista.get(index);

                    // Sincronizar el acceso al pedido
                    synchronized (pedido) {
                        eCommerce.getRegistroPedidos().delEntregados(pedido);
                        if (verificarDatos()) {
                            eCommerce.getRegistroPedidos().addVerificados(pedido);
                            System.out.println("Pedido verificado correctamente.");
                        } else {
                            eCommerce.getRegistroPedidos().addFallidos(pedido);
                            System.out.println("Pedido fallido en la verificación.");
                        }
                    }
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restablecer el estado de interrupción
            break;
            }
        } 
    }
    
    private boolean verificarDatos() {
        return ThreadLocalRandom.current().nextDouble() <= 0.95;
    }
}