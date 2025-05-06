//package main;

/**
 * Clase que representa un pedido en el sistema de logística.
 * Cada pedido tiene un estado y esta asociado a un casillero.
 */
public class Pedido {

    // Atributos
    private EstadoPedido estado;           // Estado actual del pedido
    private Casilleros casilleroAsociado;   // Casillero asignado al pedido
    private int idPedido;                  // ID del pedido (no se utiliza en este código, pero puede ser útil para futuras implementaciones)

    public Pedido(int idPedido) {
        this.estado = EstadoPedido.EN_PREPARACION;
        this.idPedido = idPedido;
        this.casilleroAsociado = null; 
    }

    // Métodos de acceso (Getters y Setters)

    public void setEstado(EstadoPedido estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado del pedido no puede ser nulo.");
        }
        this.estado = estado;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setCasilleroAsociado(Casilleros casillero) {
        if (casillero == null) {
            throw new IllegalArgumentException("El casillero asociado no puede ser nulo.");
        }
        this.casilleroAsociado = casillero;
    }

    public Casilleros getCasilleroAsociado() {
        return casilleroAsociado;
    }

    public int getId() {
        return idPedido;
    }
}