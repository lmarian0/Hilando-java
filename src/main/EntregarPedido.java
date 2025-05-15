//package main;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class EntregarPedido extends Proceso{

    private final int demoraE;
    private final Random random = new Random();
    private static final Object index_key = new Object();
    private static final Object select_key = new Object();

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
        Pedido pedido;
        synchronized(index_key){
            if (eCommerce.getRegistroPedidos().getTransito().isEmpty()) { 
                return;
            }
            int indiceAleatorio = random.nextInt(eCommerce.getRegistroPedidos().getTransito().size());
            pedido = eCommerce.getRegistroPedidos().getTransito().get(indiceAleatorio);
            eCommerce.getRegistroPedidos().delTransito(pedido);
        }

        synchronized (select_key) {
                if (confirmarPedido()) { // 90% de éxito
                    eCommerce.getRegistroPedidos().addEntregados(pedido);
                    //System.out.println(Thread.currentThread().getName() + " entrego el pedido " + pedido.getId() + " del casillero " + pedido.getCasilleroAsociado().getId());
                    pedido.setEstado(EstadoPedido.ENTREGADO);

                } else { // 10% de fallo
                    eCommerce.getRegistroPedidos().addFallidos(pedido);
                    pedido.setEstado(EstadoPedido.FALLIDO);
                }
            }
        
    }
    

    private boolean confirmarPedido(){
        return ThreadLocalRandom.current().nextDouble(0.0, 1.0) <= 0.9;
    }
}
