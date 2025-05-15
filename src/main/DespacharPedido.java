//package main;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class DespacharPedido extends Proceso{
    public DespacharPedido(EmpresaLogistica eCommerce) {
        super(eCommerce);
    }

private static final Object index_key = new Object();
private static final Object select_key = new Object();

    @Override
    public void run() {
        while (true) {
            try {
                Pedido pedido;
                synchronized (index_key) {
                    if(eCommerce.getRegistroPedidos().getPreparacion().isEmpty()){
                        TimeUnit.MILLISECONDS.sleep(10);
                        continue; // Salta a la siguiente iteracion del while
                    }
                    int index = ThreadLocalRandom.current().nextInt(eCommerce.getRegistroPedidos().getPreparacion().size());
                    pedido = eCommerce.getRegistroPedidos().getPreparacion().get(index);
                    eCommerce.getRegistroPedidos().delPreparacion(pedido);
                }
                synchronized(select_key){
                    if(verificarPedido()){
                        pedido.getCasilleroAsociado().setEstado(EstadoCasillero.VACIO);
                        eCommerce.getRegistroPedidos().addTransito(pedido);
                    }else{
                        pedido.getCasilleroAsociado().setEstado(EstadoCasillero.FUERA_SERVICIO);
                        eCommerce.getRegistroPedidos().addFallidos(pedido);
                    }
                }
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrumpido.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    

    private boolean verificarPedido(){
        return ThreadLocalRandom.current().nextDouble(0.0, 1.0) <= 0.85;
    }
}
   
