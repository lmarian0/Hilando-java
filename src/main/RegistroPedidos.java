//package main;

import java.util.ArrayList;
import java.util.List;

public class RegistroPedidos {

    private List<Pedido> enPreparacion;
    private List<Pedido> enTransito;
    private List<Pedido> entregados;
    private List<Pedido> fallidos;
    private List<Pedido> verificados;

    public RegistroPedidos(){
        this.enPreparacion= new ArrayList<>();
        this.entregados = new ArrayList<>();
        this.enTransito = new ArrayList<>();
        this.fallidos = new ArrayList<>();
        this.verificados = new ArrayList<>();
    }

    // -----------------------------------------------------------------------------------------------
    public List<Pedido> getPreparacion(){
        synchronized(this){
            return new ArrayList<>(enPreparacion);
        }
    }
    public List<Pedido> getEntregados(){
        synchronized(this){
            return new ArrayList<>(entregados);
        }
    }
    public List<Pedido> getTransito(){
        synchronized(this){
            return new ArrayList<>(enTransito);
        }
    }
    public List<Pedido> getFallidos(){
        synchronized(this){
            return new ArrayList<>(fallidos);
        }
    }
    public List<Pedido> getVerificados(){
        synchronized(this){
            return new ArrayList<>(verificados);
        }
    }
    //
     //             ⊂(◉‿◉)つ
    //-----------------------------------------------------------------------------------------------
    public void addPreparacion(Pedido pedido){
        if(pedido == null){
            System.out.println("No se puede agregar un pedido nulo a la lista de pedidos en preparacion");
            return;
        }
        synchronized (this) {
            if (enPreparacion.contains(pedido)) {
                System.out.println("El pedido ya existe en la lista de pedidos en preparacion");
                return;
            }else{
                enPreparacion.add(pedido);
            }
        }
    }


    public void addEntregados (Pedido pedido){
        if(pedido == null){
            System.out.println("No se puede agregar un pedido nulo a la lista de pedidos entregados");
            return;
        }
        synchronized (this) {
            if (entregados.contains(pedido)) {
                System.out.println("El pedido ya existe en la lista de pedidos entregados");
                return;
            }else{
                entregados.add(pedido);
            }
        }

    }
    public  void addTransito (Pedido pedido){
        if(pedido == null){
            System.out.println("No se puede agregar un pedido nulo a la lista de pedidos en transito");
            return;
        }
        synchronized (this) {
            if (enTransito.contains(pedido)) {
                System.out.println("El pedido ya existe en la lista de pedidos en transito");
                return;
            }else{
                enTransito.add(pedido);
            }
        }
    }
    public void addFallidos (Pedido pedido){
        if(pedido == null){
            System.out.println("No se puede agregar un pedido nulo a la lista de pedidos fallidos");
            return;
        }
        synchronized (this) {
            if (fallidos.contains(pedido)) {
                System.out.println("El pedido ya existe en la lista de pedidos fallidos");
                return;
            }else{
                fallidos.add(pedido);
            }
        }
    }
    public void addVerificados(Pedido pedido){
        if(pedido == null){
            System.out.println("No se puede agregar un pedido nulo a la lista de pedidos verificados");
            return;
        }
        synchronized (this) {
            if (verificados.contains(pedido)) {
                System.out.println("El pedido ya existe en la lista de pedidos verificados");
                return;
            }else{
                verificados.add(pedido);
            }
        }
    }

    //------------------------------------------------------------------------------------------------

    public void delPreparacion(Pedido pedido){
        synchronized (this){
            enPreparacion.remove(pedido);
        }
    }

    public void delEntregados (Pedido pedido){
        synchronized (this){
            entregados.remove(pedido);
        }
    }

    public void delTransito (Pedido pedido){
        synchronized (this){
            enTransito.remove(pedido);
        }
    }
    /**
     * Obtiene y elimina un pedido aleatorio de enPreparacion de forma atómica.
     * @return Pedido o null si vacío
     */
    public Pedido obtenerYEliminarPreparacionAleatorio() {
        synchronized (this) {
            if (enPreparacion.isEmpty()) return null;
            int index = (int) (Math.random() * enPreparacion.size());
            return enPreparacion.remove(index);
        }
    }
    /**
     * Obtiene y elimina un pedido aleatorio de enTransito de forma atómica.
     */
    public Pedido obtenerYEliminarTransitoAleatorio() {
        synchronized (this) {
            if (enTransito.isEmpty()) return null;
            int index = (int) (Math.random() * enTransito.size());
            return enTransito.remove(index);
        }
    }
    /**
     * Obtiene y elimina un pedido aleatorio de entregados de forma atómica.
     */
    public Pedido obtenerYEliminarEntregadoAleatorio() {
        synchronized (this) {
            if (entregados.isEmpty()) return null;
            int index = (int) (Math.random() * entregados.size());
            return entregados.remove(index);
        }
    }

}
