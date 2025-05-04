//package main;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.List;

public class DespacharPedido extends Proceso{
    public DespacharPedido(EmpresaLogistica eCommerce) {
        super(eCommerce);
    }

    @Override
    public void run() {

        /*Agarrar un pedido aleatorio de la lista preparacion */
        Pedido pedido = null;
        List<Pedido> lista= eCommerce.getRegistroPedidos().getPreparacion();
        try {
            while(!Thread.currentThread().isInterrupted()) {
                    
                if (lista.isEmpty()){
                    TimeUnit.MILLISECONDS.sleep(10000);
                    System.out.println("No hay pedidos, a dormir...");
                    continue;
                }
                synchronized(eCommerce.getRegistroPedidos()){
                    int index = ThreadLocalRandom.current().nextInt(lista.size());
                    pedido = lista.get(index);
                }
                if (verificarDatos() && pedido.getEstado()==EstadoPedido.EN_PREPARACION){
                    liberarCasillero(pedido);
                    System.out.println(Thread.currentThread().getName() + " asignó el pedido: " );
                    pedido.setEstado(EstadoPedido.EN_TRANSITO);
                    eCommerce.getRegistroPedidos().delPreparacion(pedido);
                    eCommerce.getRegistroPedidos().addTransito(pedido);
                }
                else {
                    marcarCasilleroFueraDeServicio(pedido);
                    eCommerce.getRegistroPedidos().delPreparacion(pedido);
                    eCommerce.getRegistroPedidos().addFallidos(pedido);
                }
                TimeUnit.MILLISECONDS.sleep(200);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restablecer el estado de interrupción
        }
    }

    private void liberarCasillero(Pedido pedido){
        pedido.getCasilleroAsociado().liberar();
    }

    private void marcarCasilleroFueraDeServicio(Pedido pedido){
        pedido.getCasilleroAsociado().setEstado(EstadoCasillero.FUERA_SERVICIO);
    }


    public boolean verificarDatos(){
        ThreadLocalRandom probabilidad = ThreadLocalRandom.current();
        probabilidad.nextDouble(1);
        return probabilidad.nextDouble() <= 0.85;
    }
}