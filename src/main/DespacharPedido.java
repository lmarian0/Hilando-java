//package main;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class DespacharPedido extends Proceso{

    private final int demoraD;

    public DespacharPedido(EmpresaLogistica eCommerce, int demoraD) {
        super(eCommerce);
        this.demoraD = demoraD;
    }

    @Override
    public void run() {
        while (true) {
            try {
                RegistroPedidos registro = eCommerce.getRegistroPedidos();
                Pedido pedido = registro.obtenerYEliminarPreparacionAleatorio();
                if (pedido == null) {
                    TimeUnit.MILLISECONDS.sleep(10);
                    continue;
                }
                if(verificarPedido()){
                    System.out.println(Thread.currentThread().getName() + " despacho el pedido " + pedido.getId() + " del casillero " + pedido.getCasilleroAsociado().getId());
                    pedido.getCasilleroAsociado().setEstado(EstadoCasillero.VACIO);
                    registro.addTransito(pedido);
                }else{
                    System.out.println("Pedido " + pedido.getId()+ " fallido en el despacho");
                    pedido.getCasilleroAsociado().setEstado(EstadoCasillero.FUERA_SERVICIO);
                    registro.addFallidos(pedido);
                }
                TimeUnit.MILLISECONDS.sleep(demoraD);
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

