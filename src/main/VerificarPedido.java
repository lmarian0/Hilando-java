//package main;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class VerificarPedido extends Proceso{
    private List<Pedido> lista= eCommerce.getRegistroPedidos().getEntregados();
    private Object controlverificacion = new Object(); // Objeto de control para la sincronización
    public VerificarPedido(EmpresaLogistica eCommerce) {
        super(eCommerce);
    }
    
    @Override
    public void run() {
        
        try {
            System.out.println("Verificando pedidos...");
            System.out.println(lista.size() + " pedidos entregados para verificar.");
            while (!Thread.currentThread().isInterrupted()) {
                TimeUnit.MILLISECONDS.sleep(100);
                if (!lista.isEmpty()) {
                    Pedido pedido; // Declarar la variable fuera del bloque synchronized

                    synchronized(controlverificacion){
                        int indiceAleatorio = ThreadLocalRandom.current().nextInt(lista.size());
                        pedido = lista.get(indiceAleatorio); // Asignar el valor dentro del bloque synchronized
                        if (verificarDatos()) {
                            // Se verifica si el pedido fue entregado
                            eCommerce.getRegistroPedidos().getVerificados().add(pedido);
                            eCommerce.getRegistroPedidos().getEntregados().remove(pedido);
                        } else {
                            eCommerce.getRegistroPedidos().getFallidos().add(pedido);
                            eCommerce.getRegistroPedidos().getEntregados().remove(pedido);
                        }


                    }
                    
                }else{
                    // Si no hay pedidos en la lista, esperar un momento antes de volver a verificar
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restablecer el estado de interrupción
        }
    }

    private boolean verificarDatos(){
        ThreadLocalRandom probabilidad = ThreadLocalRandom.current();
        return probabilidad.nextDouble() <= 0.95;
    }
    
}
