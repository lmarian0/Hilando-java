//package main;

public class Casilleros{
    private EstadoCasillero estado;
    private int contadorOcupaciones;
    private Pedido pedido; 
    private int idCasillero;
    private final Object OcupationKey;

    public Casilleros(int idCasillero){
        this.estado = EstadoCasillero.VACIO;
        this.contadorOcupaciones = 0;
        this.pedido = null;
        this.idCasillero = idCasillero;
        OcupationKey = new Object();
    }

    public boolean esVacio(){
        return estado == EstadoCasillero.VACIO;
    }

    public void setEstado(EstadoCasillero estado_seteado){
        this.estado = estado_seteado;
    }

    public void setPedido(Pedido pedido_arrivado){
        synchronized(OcupationKey){
            if(getEstado() == EstadoCasillero.VACIO){
                setEstado(EstadoCasillero.OCUPADO);
                this.pedido = pedido_arrivado;
                this.contadorOcupaciones++;
            }
        }
    }

    public Pedido getPedido(){
        return this.pedido;
    }

    public EstadoCasillero getEstado(){
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

    public int getId() {
        return idCasillero;
    }
}