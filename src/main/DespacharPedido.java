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
        
            while(!Thread.currentThread().isInterrupted()) {
            try {    
                List<Pedido> lista= eCommerce.getRegistroPedidos().getPreparacion();

                if (lista.isEmpty()){
                    TimeUnit.MILLISECONDS.sleep(100);
                    //System.out.println("No hay pedidos, a dormir...");
                    continue;
                }
                synchronized(eCommerce.getRegistroPedidos()){
                    int index = ThreadLocalRandom.current().nextInt(lista.size());
                    pedido = lista.get(index);
                    
                    synchronized(pedido){
                        if (verificarDatos() && pedido.getEstado()==EstadoPedido.EN_PREPARACION){
                            liberarCasillero(pedido);
                            System.out.println(Thread.currentThread().getName() + " asignó el pedido: " );
                            pedido.setEstado(EstadoPedido.EN_TRANSITO);
                            eCommerce.getRegistroPedidos().delPreparacion(pedido);
                            eCommerce.getRegistroPedidos().addTransito(pedido);
                            System.out.println("entre despachar1------------------------------------------------");
                        }
                        else {
                            marcarCasilleroFueraDeServicio(pedido);
                            eCommerce.getRegistroPedidos().delPreparacion(pedido);
                            eCommerce.getRegistroPedidos().addFallidos(pedido);
                            System.out.println("entre despachar2------------------------------------------------");
                        } 

                    }
                }
                TimeUnit.MILLISECONDS.sleep(200);
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restablecer el estado de interrupción
                break;
            }
        }
    }

    private void liberarCasillero(Pedido pedido) {
        if (pedido.getCasilleroAsociado() == null) {
            throw new IllegalStateException("El pedido no tiene un casillero asociado.");
        }
        pedido.getCasilleroAsociado().liberar();
    }

    private void marcarCasilleroFueraDeServicio(Pedido pedido) {
        if (pedido.getCasilleroAsociado() == null) {
            throw new IllegalStateException("El pedido no tiene un casillero asociado.");
        }
        pedido.getCasilleroAsociado().setEstado(EstadoCasillero.FUERA_SERVICIO);
    }


    public boolean verificarDatos(){
        ThreadLocalRandom probabilidad = ThreadLocalRandom.current();
        return probabilidad.nextDouble() <= 0.85;
    }
}