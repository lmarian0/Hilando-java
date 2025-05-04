// package main;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class PrepararPedido extends Proceso{
    private Random random = new Random();
    private List<Pedido> pedidosIniciales = new ArrayList<>(); // Lista de pedidos iniciales

    public PrepararPedido(EmpresaLogistica eCommerce,List<Pedido> pedidosIniciales) {
        super(eCommerce);
        this.pedidosIniciales = pedidosIniciales; //inicializa la lista de pedidos que llegaron a la empresa

    }

    private boolean verifVacio(Casilleros casillero){
        if(casillero.getEstado() == EstadoCasillero.VACIO){
            return true;
        }else{
            return false;
        }
    }

    private Casilleros buscarCasilleroLibre() {
    
        while (true) {
            int numRand = random.nextInt(200); // Generar un índice aleatorio
            Casilleros casillero = eCommerce.getCasillero(numRand);
            synchronized (casillero) { //Sincronizacion para que 2 hilos distintos no devuelvan el mismo casillero
                if (verifVacio(casillero)) {
                    return casillero;
                }
            }
        }
    }

    @Override
    public void run() {
        // Buscar un casillero libre
        while(true){
            try{
                Pedido pedido_cargar = null;
                synchronized(pedidosIniciales){ //Sincronizacion para que 2 hilos distintos intenten agarrar el mismo pedido
                    if(!pedidosIniciales.isEmpty()){
                        pedido_cargar = pedidosIniciales.remove(0);
                    }
                }
                if(pedido_cargar != null && !pedidosIniciales.isEmpty()){
                    Casilleros casillero = buscarCasilleroLibre();
                     
                    synchronized(casillero){ //Sincronizacion para que 2 hilos distintos no modifiquen el mismo casillero
                        //Asignacionaciones
                        casillero.setPedido(pedido_cargar);
                        pedido_cargar.setCasilleroAsociado(casillero);
                        System.out.println(Thread.currentThread().getName() + " asignó el pedido: " + pedido_cargar);

                        //Cambio de estados de casillero y pedido
                        casillero.setEstado(EstadoCasillero.OCUPADO);
                        pedido_cargar.setEstado(EstadoPedido.EN_PREPARACION);

                        //Cargado al registro de preparacion
                        eCommerce.getRegistroPedidos().addPreparacion(pedido_cargar);
                    }

                    Thread.sleep(10);//Simula el tiempo de preparación del pedido    
                }
                else{
                    break;
                }
            } catch (InterruptedException e){
                System.out.println(Thread.currentThread().getName() + " interrumpido.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
