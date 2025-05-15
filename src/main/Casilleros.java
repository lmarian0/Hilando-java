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

    private final Object estado_key = new Object();
    private final Object contador_key = new Object();
    private final Object pedido_key = new Object();

    //Modificadores y getters de pedido

    public void liberar() {
        synchronized(pedido_key){
            if(getEstado() == EstadoCasillero.FUERA_SERVICIO){
                setEstado(EstadoCasillero.VACIO);
                //System.out.println("Se desocupo el casillero: " + getId());
                this.pedido = null;
            }
        }
    }

    public Pedido getPedido(){
        synchronized(pedido_key){
            return pedido;
        }
    }

    public synchronized void setPedido(Pedido pedido_arrivado){
        synchronized(pedido_key){
            if(getEstado() == EstadoCasillero.VACIO){
                setEstado(EstadoCasillero.OCUPADO);
                this.pedido = pedido_arrivado;
                // Se incrementa el contador de ocupaciones y se bloquea la lectura del contador para que hilos de clases externas no puedan leer el contador mientras se incrementa
                synchronized(contador_key){
                    this.contadorOcupaciones++;
                }
            }
        }
    }

    public int getContadorOcupaciones(){
        synchronized(contador_key){
            return contadorOcupaciones;
        }
    }

    //Modificadores y getters de estado

    public boolean esVacio(){
        synchronized(estado_key){
            return estado == EstadoCasillero.VACIO;
        }
    }

    public void setEstado(EstadoCasillero estado_seteado){
        synchronized(estado_key){
            this.estado = estado_seteado;
        }
    }

    public EstadoCasillero getEstado(){
        synchronized(estado_key){
            return estado;
        }
    }

    public int getId() {
        return idCasillero;
    }
}