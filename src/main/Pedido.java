//package main;

/**
 * Clase que representa un pedido en el sistema de logística.
 * Cada pedido tiene un estado y esta asociado a un casillero.
 */
public class Pedido {

    // Atributos
    private EstadoPedido estado;           // Estado actual del pedido
    private Casilleros casilleroAsociado;   // Casillero asignado al pedido

    /**
     * Constructor por defecto. Inicializa el pedido en estado EN_PREPARACION.
     */
    public Pedido() {
        this.estado = EstadoPedido.EN_PREPARACION;
    }

    // Métodos de acceso (Getters y Setters)

    /**
     * Establece el estado del pedido.
     * @param estado Nuevo estado del pedido (debe ser uno de los valores de EstadoPedido).
     */
    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el estado actual del pedido.
     * @return Estado del pedido.
     */
    public EstadoPedido getEstado() {
        return estado;
    }

    /**
     * Asocia un casillero al pedido.
     * @param casillero Casillero a asociar.
     */
    public void setCasilleroAsociado(Casilleros casillero) {
        this.casilleroAsociado = casillero;
    }

    /**
     * Obtiene el casillero asociado al pedido.
     * @return Casillero asociado o null si no tiene.
     */
    public Casilleros getCasilleroAsociado() {
        return casilleroAsociado;
    }
}