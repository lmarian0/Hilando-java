//package main;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class EntregarPedido extends Proceso{

    private final int demoraE;
    private final Random random = new Random();

    public EntregarPedido(EmpresaLogistica eCommerce, int demoraE) {
        super(eCommerce);
        this.demoraE = demoraE;
    }


    @Override
    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted()) {
                TimeUnit.MILLISECONDS.sleep(demoraE);
                procesarEntrega();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restablecer el estado de interrupción
        }
    }

    public void procesarEntrega() {
        RegistroPedidos registro = eCommerce.getRegistroPedidos();
        Pedido pedido = registro.obtenerYEliminarTransitoAleatorio();
        if (pedido == null) {
            return;
        }
        if (confirmarPedido()) { // 90% de éxito
            registro.addEntregados(pedido);
            System.out.println(Thread.currentThread().getName() + " entrego el pedido " + pedido.getId());
            pedido.setEstado(EstadoPedido.ENTREGADO);

        } else { // 10% de fallo
            System.out.println("Pedido " + pedido.getId()+ " fallido en la entrega");
            registro.addFallidos(pedido);
            pedido.setEstado(EstadoPedido.FALLIDO);
        }
    }
    

    private boolean confirmarPedido(){
        return ThreadLocalRandom.current().nextDouble(0.0, 1.0) <= 0.9;
    }
}
