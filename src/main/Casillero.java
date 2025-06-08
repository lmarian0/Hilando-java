//package main;

public class Casillero{
    private EstadoCasillero estado;
    private int contadorOcupaciones;
    private Pedido pedido; 
    private int idCasillero;


    public Casillero(int idCasillero){
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
            if(this.estado == EstadoCasillero.FUERA_SERVICIO){
                this.estado = EstadoCasillero.VACIO;
                this.pedido = null;
            }
        }
    }

    public Pedido getPedido(){
        synchronized(pedido_key){
            return pedido;
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

    /**
     * Intenta ocupar el casillero con el pedido dado de forma atómica.
     * @param pedido El pedido a asignar
     * @return true si se ocupó exitosamente, false si no estaba vacío
     */
    public boolean intentarOcupar(Pedido pedido) {
        boolean ocupado = false;
        synchronized(estado_key) {
            if (estado == EstadoCasillero.VACIO) {
                estado = EstadoCasillero.OCUPADO;
                ocupado = true;
            }
        }
        if (ocupado) {
            synchronized(pedido_key) {
                this.pedido = pedido;
            }
            synchronized(contador_key) {
                this.contadorOcupaciones++;
            }
            pedido.setCasilleroAsociado(this);
            return true;
        }
        return false;
    }
}