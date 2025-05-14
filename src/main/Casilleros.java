//package main;

public class Casilleros{
    private EstadoCasillero estado;
    private int contadorOcupaciones;
    private Pedido pedido; 
    private int idCasillero;


    public Casilleros(int idCasillero){
        this.estado = EstadoCasillero.VACIO;
        this.contadorOcupaciones = 0;
        this.pedido = null;
        this.idCasillero = idCasillero;
    }

    private Object estado_key = new Object();
    private Object contador_key = new Object();

    //Para cada instancia de casillero la asignacion de pedido esta en la misma seccion critica que la liberacion del casillero, el seteo del estado y el control de casillero vacio para que un hilo no pueda asignar un pedido mientras otro hilo lo libera, tampoco se pueda ver un casillero que se esta ocupando como vacio y viceversa


    public synchronized void setPedido(Pedido pedido_arrivado){
        if(getEstado() == EstadoCasillero.VACIO){
            setEstado(EstadoCasillero.OCUPADO);
            this.pedido = pedido_arrivado;
            // Se incrementa el contador de ocupaciones y se bloquea la lectura del contador para que hilos de clases externas no puedan leer el contador mientras se incrementa
            synchronized(contador_key){
                this.contadorOcupaciones++;
            }
        }
    }

    public int getContadorOcupaciones(){
        synchronized(contador_key){
            return contadorOcupaciones;
        }
    }

    public synchronized void liberar() {
        if(getEstado() == EstadoCasillero.OCUPADO){
            setEstado(EstadoCasillero.VACIO);
            //System.out.println("Se desocupo el casillero: " + getId());
            this.pedido = null;
        }
    }

    public synchronized boolean esVacio(){
        return estado == EstadoCasillero.VACIO;
    }


    // Se utiliza un objeto de bloqueo para evitar que 1 hilo no pueda modificar el estado mientras otro hilo lo lee

    public synchronized void setEstado(EstadoCasillero estado_seteado){
        synchronized(estado_key){
            this.estado = estado_seteado;
        }
    }

    public EstadoCasillero getEstado(){
        synchronized(estado_key){
            return estado;
        }
    }

    public Pedido getPedido(){
        return pedido;
    }

    public int getId() {
        return idCasillero;
    }
}