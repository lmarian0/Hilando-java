//package main;

import java.util.List;
//import java.util.concurrent.ThreadLocalRandom;
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
        List <Pedido> pedidosEnTransito;
        Pedido pedido;
        synchronized(eCommerce.getRegistroPedidos()){
            pedidosEnTransito = eCommerce.getRegistroPedidos().getTransito();
            if (pedidosEnTransito.isEmpty()) { 
                return;
            }
            int indiceAleatorio = random.nextInt(pedidosEnTransito.size());
            pedido = pedidosEnTransito.get(indiceAleatorio);

            synchronized (pedido) {
                if (verificarDatos()) { // 90% de éxito
                    eCommerce.getRegistroPedidos().delTransito(pedido);
                    eCommerce.getRegistroPedidos().addEntregados(pedido);
                    System.out.println(Thread.currentThread().getName() + " entrego el pedido " + pedido.getId() + " del casillero " + pedido.getCasilleroAsociado().getId());
                    pedido.setEstado(EstadoPedido.ENTREGADO);
        
                    // Liberar casillero asociado
                    pedido.getCasilleroAsociado().liberar();
                    System.out.println("Casillero liberado:");
                } else { // 10% de fallo
                    eCommerce.getRegistroPedidos().delTransito(pedido);
                    eCommerce.getRegistroPedidos().addFallidos(pedido);
                    
                    pedido.setEstado(EstadoPedido.FALLIDO);
                }
            }
        }
        
    }
    

    public boolean verificarDatos(){
        return random.nextDouble() <= 0.9;
    }
}
