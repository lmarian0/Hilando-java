//package main;

import java.util.List;
//import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class EntregarPedido extends Proceso{

    private final RegistroPedidos registro;
    private final int demoraE;
    private final Random random = new Random();
    

    public EntregarPedido(EmpresaLogistica eCommerce, RegistroPedidos registro, int demoraE) {
        super(eCommerce);
        this.registro = registro;
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
        List <Pedido> pedidosEnTransito = registro.getTransito();

        if (pedidosEnTransito.isEmpty()) {
            return; // No hay pedidos en tránsito
        }

        synchronized (pedidosEnTransito) {
            if (pedidosEnTransito.isEmpty()) return;
        
            int indiceAleatorio = random.nextInt(pedidosEnTransito.size());
            Pedido pedido = pedidosEnTransito.get(indiceAleatorio);
        
            if (verificarDatos()) { // 90% de éxito
                registro.delTransito(pedido);
                registro.addEntregados(pedido);
                pedido.setEstado(EstadoPedido.ENTREGADO);
                
                // Liberar casillero aquí (dentro del bloque sincronizado)
                pedido.getCasilleroAsociado().liberar();
            } else { // 10% de fallo
                registro.delTransito(pedido);
                registro.addFallidos(pedido);
                pedido.setEstado(EstadoPedido.FALLIDO);
            }
        }
    }

    public boolean verificarDatos(){
        return random.nextDouble() <= 0.9;
    }
}
