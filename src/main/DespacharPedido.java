package main;
import java.util.List;

import java.util.concurrent.ThreadLocalRandom;

public class DespacharPedido extends Proceso{
    public DespacharPedido(EmpresaLogistica eCommerce) {
        super(eCommerce);
    }
    @Override
    public void run() {

        /*
         * Agarrar un pedido aleatorio de la lista preparacion
         * 
         * 
         */
        Pedido pedido = null;
        List<Pedido> lista= eCommerce.getRegistroPedidos().getPreparacion();

        if(lista.isEmpty()){
            try {
                System.out.println("No hay pedidos para despachar. Esperando...");
                wait(); // El hilo se pone en espera hasta que otro hilo notifique
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Hilo interrumpido mientras estaba en espera.");
            }
        }
        else{
            int index = ThreadLocalRandom.current().nextInt(lista.size());
            pedido = lista.get(index);
            if (verificarDatos() && pedido.getEstado()==EstadoPedido.EN_PREPARACION){
                liberarCasillero(pedido);
                pedido.setEstado(EstadoPedido.EN_TRANSITO);
                eCommerce.getRegistroPedidos().delPreparacion(pedido);
                eCommerce.getRegistroPedidos().addTransito(pedido);
            }
            else {
                marcarCasilleroFueraDeServicio(pedido);
                eCommerce.getRegistroPedidos().delPreparacion(pedido);
                eCommerce.getRegistroPedidos().addFallidos(pedido);
            }
        }

        
    }

    private void liberarCasillero(Pedido pedido){
        pedido.getCasilleroAsociado().setEstado(EstadoCasillero.VACIO);
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
