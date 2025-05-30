//package main;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;

public class VerificarPedido extends Proceso{

    private final int demoraV; // Tiempo de verificación en milisegundos
    
    public VerificarPedido(EmpresaLogistica eCommerce, int demoraV) {
        super(eCommerce);
        this.demoraV = demoraV;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                RegistroPedidos registro = eCommerce.getRegistroPedidos();
                Pedido pedido = registro.obtenerYEliminarEntregadoAleatorio();
                if (pedido == null) {
                    continue;
                }
                if (verificarDatos()) {
                    registro.addVerificados(pedido);
                    System.out.println("Pedido " + pedido.getId() +" verificado correctamente.");
                } else {
                    registro.addFallidos(pedido);
                    System.out.println("Pedido " + pedido.getId()+ " fallido en la verificación.");
                }
                TimeUnit.MILLISECONDS.sleep(demoraV);
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