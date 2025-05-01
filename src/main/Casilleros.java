package main;


public class Casilleros{
    private EstadoCasillero estado;
    private int contadorOcupaciones;
    private PedidoTemp pedido; 

    public Casilleros(){
        this.estado = EstadoCasillero.VACIO;
        this.contadorOcupaciones = 0;
        this.pedido = null;
    }

/**
 * The function `esVacio()` in Java checks if a certain state is empty.
 * 
 * @return The method `esVacio()` is returning a boolean value based on the comparison of the variable
 * `estado` with the enum constant `EstadoCasillero.VACIO`. If the `estado` is equal to
 * `EstadoCasillero.VACIO`, then the method will return `true`, indicating that the casillero is empty.
 * Otherwise, it will return `false`.
 */
    public boolean esVacio(){
        return estado == EstadoCasillero.VACIO;
    }

    public void setEstado(EstadoCasillero estado_seteado){
        this.estado = estado_seteado;
    }
/**
 * setea el pediddo que llego al casillero y cambia el estado a OCUPADO. Aumentado el contador de ocupaciones
 * @param pedido_arrivado
 */
    public void setPedido(PedidoTemp pedido_arrivado){
        if(estado == EstadoCasillero.OCUPADO){
            System.out.println("El casillero ya esta ocupado.");
            return;
        }
        this.pedido = pedido_arrivado;
        setEstado(EstadoCasillero.OCUPADO);
        contadorOcupaciones++;
    }

    public PedidoTemp getPedido(){
        return pedido;
    }

    public int getContadorOcupaciones(){
        return contadorOcupaciones;
    }
}