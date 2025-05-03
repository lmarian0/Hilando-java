package main;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class VerificarPedido extends Proceso{
    public VerificarPedido(EmpresaLogistica eCommerce) {
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
        List<Pedido> lista= eCommerce.getRegistro().getEntregados();

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
            if (verificarDatos() && pedido.getEstado()==EstadoPedido.ENTREGADO){
                eCommerce.getRegistro().delEntregados(pedido);
                eCommerce.getRegistro().addVerificados(pedido);
            }
            else {
                eCommerce.getRegistro().delEntregados(pedido);
                eCommerce.getRegistro().addFallidos(pedido);
            }
        }

        
    }

   

    private boolean verificarDatos(){
        ThreadLocalRandom probabilidad = ThreadLocalRandom.current();
        probabilidad.nextDouble(1);
        return probabilidad.nextDouble() <= 0.95;
    }
    
}
