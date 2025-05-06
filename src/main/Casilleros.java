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

    public synchronized boolean esVacio(){
        return estado == EstadoCasillero.VACIO;
    }

    public synchronized void setEstado(EstadoCasillero estado_seteado){
        this.estado = estado_seteado;
    }

    public synchronized void setPedido(Pedido pedido_arrivado){
        if(getEstado() == EstadoCasillero.VACIO){
            setEstado(EstadoCasillero.OCUPADO);
            this.pedido = pedido_arrivado;
            this.contadorOcupaciones++;
        }
    }

    public synchronized Pedido getPedido(){
        return this.pedido;
    }

    public synchronized EstadoCasillero getEstado(){
        return this.estado;
    }

    public synchronized int getContadorOcupaciones(){
        return this.contadorOcupaciones;
    }

    public synchronized void liberar() {
        if(getEstado() == EstadoCasillero.OCUPADO){
            setEstado(EstadoCasillero.VACIO);
            System.out.println("Se desocupo el casillero: " + getId());
            this.pedido = null;
        }
    }

    public synchronized int getId() {
        return idCasillero;
    }
}