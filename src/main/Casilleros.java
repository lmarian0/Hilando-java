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

    public boolean esVacio(){
        return estado == EstadoCasillero.VACIO;
    }

    public synchronized void setEstado(EstadoCasillero estado_seteado){
        this.estado = estado_seteado;
    }
/**
 * setea el pediddo que llego al casillero y cambia el estado a OCUPADO. Aumentado el contador de ocupaciones
 * @param pedido_arrivado
 */
    public synchronized void setPedido(Pedido pedido_arrivado){
        if(estado == EstadoCasillero.OCUPADO){
            System.out.println("El casillero ya esta ocupado.");
            return;
        }
        this.pedido = pedido_arrivado;
        setEstado(EstadoCasillero.OCUPADO);
        contadorOcupaciones++;
    }

    public synchronized Pedido getPedido(){
        return pedido;
    }

    public synchronized EstadoCasillero getEstado(){
        return estado;
    }

    public synchronized int getContadorOcupaciones(){
        return contadorOcupaciones;
    }

    public synchronized void liberar() {
        this.estado = EstadoCasillero.VACIO;
        this.pedido = null;
    }

    public int getId() {
        return idCasillero;
    }
}